package com.waywalkers.kbook.service;

import com.waywalkers.kbook.constant.SubscriptionStatus;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.domain.payment.Payment;
import com.waywalkers.kbook.domain.payment.PaymentRepository;
import com.waywalkers.kbook.domain.paymentCancel.PaymentCancel;
import com.waywalkers.kbook.dto.PaymentDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.exception.PaymentException;
import com.waywalkers.kbook.mapper.PageMapper;
import com.waywalkers.kbook.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentMapper paymentMapper;
    private final PageMapper pageMapper;

    @Value("${domain.front}")
    private String domainFront;
    @Value("${payments.toss.origin_url}")
    private String tossOriginUrl;

    @Value("${payments.toss.test_client_api_key}")
    private String testClientApiKey;

    @Value("${payments.toss.test_secret_api_key}")
    private String testSecretApiKey;

    @Value("${payments.toss.live_client_api_key}")
    private String liveClientApiKey;

    @Value("${payments.toss.live_secret_api_key}")
    private String liveSecretApiKey;

    @Value("${payments.toss.success_url}")
    private String successCallBackUrl;

    @Value("${payments.toss.fail_url}")
    private String failCallBackUrl;

    public ResultDto requestPayment(PaymentDto.PaymentRequest paymentRequest) {
        Payment payment = paymentRequest.toEntity();
        long accountId = paymentRequest.getAccountId();

        Account account = accountRepository.findById(accountId).orElseThrow(()->new EntityNotFoundException("account"));
        payment.setCustomer(account);
        paymentRepository.save(payment);

        PaymentDto.PaymentResponse paymentResponse;
        paymentResponse = paymentMapper.PaymentToPaymentResponse(payment);
        paymentResponse.setSuccessUrl(successCallBackUrl);
        paymentResponse.setFailUrl(failCallBackUrl);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(paymentResponse)
                .build();
    }

    public void verifyRequest(String paymentKey, String orderId, Long realAmount) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(()-> new EntityNotFoundException("payment"));
        Long amount = payment.getAmount();
        SubscriptionStatus subscriptionStatus = payment.getSubscriptionStatus();
        boolean isVulnerable = payment.getCustomer().isVulnerable();

        if(amount.equals(realAmount)){
            if(isVulnerable){
                if(! subscriptionStatus.getVulnerablePrice().equals(realAmount)){
                    throw new PaymentException("amount error");
                }
            }else{
                if(! subscriptionStatus.getGeneralPrice().equals(realAmount)){
                    throw new PaymentException("amount error");
                }
            }
            payment.setPaymentKey(paymentKey);
        }else{
            throw new PaymentException("amount error");
        }
    }

    public RedirectView requestFinalPayment(String paymentKey, String orderId, Long amount) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(()-> new EntityNotFoundException("payment"));
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        testSecretApiKey = testSecretApiKey + ":";
        String encodedAuth = new String(Base64.getEncoder().encode(testSecretApiKey.getBytes(StandardCharsets.UTF_8)));
        headers.setBasicAuth(encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("paymentKey", paymentKey);
        param.put("orderId", orderId);
        param.put("amount", amount);

        PaymentDto.PaymentResHandleDto paymentResHandleDto;
        try{
            paymentResHandleDto = rest.postForEntity(
                    URI.create(tossOriginUrl + "/confirm"),
                    new HttpEntity<>(param, headers),
                    PaymentDto.PaymentResHandleDto.class
            ).getBody();
            payment.paymentSuccess();
            payment.getCustomer().subscribe(payment.getSubscriptionStatus());
        }catch (HttpClientErrorException e){
            paymentResHandleDto = null;
        }

        RedirectView redirectView = new RedirectView();
        Properties properties = new Properties();
        if(paymentResHandleDto == null){
            redirectView.setUrl(domainFront + "/app/payment/fail");
            properties.setProperty("errorCode", "404");
            properties.setProperty("errorMsg", "NOT_FOUND_PAYMENT");
        }else{
            redirectView.setUrl(domainFront + "/app/payment/success");
            properties.setProperty("orderName", paymentResHandleDto.getOrderName());
            properties.setProperty("currency", paymentResHandleDto.getCurrency());
            properties.setProperty("method", paymentResHandleDto.getMethod());
            properties.setProperty("totalAmount", paymentResHandleDto.getTotalAmount());
        }
        redirectView.setAttributes(properties);

        return redirectView;
    }

    public RedirectView requestFail(String errorCode, String errorMsg, String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(()-> new EntityNotFoundException("payment"));
        payment.paymentFail(errorMsg);
        PaymentDto.PaymentResHandleFailDto paymentResHandleFailDto = PaymentDto.PaymentResHandleFailDto.builder()
                .orderId(orderId)
                .errorCode(errorCode)
                .errorMsg(errorMsg)
                .build();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(domainFront + "/app/payment/fail");
        Properties properties = new Properties();
        properties.setProperty("errorCode", paymentResHandleFailDto.getErrorCode());
        properties.setProperty("errorMsg", paymentResHandleFailDto.getErrorMsg());
        redirectView.setAttributes(properties);

        return redirectView;
    }

    public ResultDto requestCancel(String paymentKey, String cancelReason) {
        boolean requestCancel;
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        byte[] secretKeyByte = (testSecretApiKey + ":").getBytes(StandardCharsets.UTF_8);
        headers.setBasicAuth(new String(Base64.getEncoder().encode(secretKeyByte)));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        JSONObject param = new JSONObject();
        param.put("cancelReason", cancelReason);

        PaymentDto.PaymentResHandleDto paymentCancelResDto = rest.postForObject(
                URI.create(tossOriginUrl + paymentKey + "/cancel"),
                new HttpEntity<>(param, headers),
                PaymentDto.PaymentResHandleDto.class
        );

        if (paymentCancelResDto == null){
            requestCancel = false;
        }else{
            Long cancelAmount = paymentCancelResDto.getCancels()[0].getCancelAmount();
            Payment payment = paymentRepository.findByPaymentKeyAndAmount(paymentKey, cancelAmount).orElseThrow(() -> new EntityNotFoundException("payment"));
            PaymentCancel paymentCancel = paymentCancelResDto.toCancelPayment();
            paymentCancel.setCustomer(payment.getCustomer());
            requestCancel = true;
        }

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(requestCancel)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getListOfPayments(Pageable pageable) {
        Page<Payment> paymentPage = paymentRepository.findAll(pageable);
        List<PaymentDto.ListOfPayments> listOfPayments = paymentPage.getContent().stream().map(paymentMapper::PaymentToListOfPayments).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfPayments)
                .page(pageMapper.PageToPageableDto(paymentPage))
                .build();
    }
}
