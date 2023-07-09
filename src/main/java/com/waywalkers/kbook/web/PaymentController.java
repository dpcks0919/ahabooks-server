package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Constants;
import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.PaymentDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Properties;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(path = Path.API_PAYMENT)
    @ApiOperation(
            value = "결제 요청"
    )
    public ResultDto<PaymentDto.PaymentResponse> requestPayment(@RequestBody PaymentDto.PaymentRequest paymentRequest){
        return paymentService.requestPayment(paymentRequest);
    }

    @GetMapping(path = Path.API_REDIRECT_PAYMENT_SUCCESS)
    @ApiOperation(
            value = "결제 성공 리다이렉트"
    )
    public RedirectView requestPaymentSuccess(
            @ApiParam(value = "토스 측 결제 고유 번호", required = true) @RequestParam String paymentKey,
            @ApiParam(value = "우리 측 주문 고유 번호", required = true) @RequestParam String orderId,
            @ApiParam(value = "실제 결제 금액", required = true) @RequestParam Long amount
    ){
        paymentService.verifyRequest(paymentKey, orderId, amount);
        return paymentService.requestFinalPayment(paymentKey, orderId, amount);
    }

    @GetMapping(path = Path.API_REDIRECT_PAYMENT_FAIL)
    @ApiOperation(
            value = "결제 실패 리다이렉트"
    )
    public RedirectView requestPaymentFail(
            @ApiParam(value = "에러 코드", required = true) @RequestParam(name = "code") String errorCode,
            @ApiParam(value = "에러 메시지", required = true) @RequestParam(name = "message") String errorMsg,
            @ApiParam(value = "우리측 주문 고유 번호", required = true) @RequestParam(name = "orderId") String orderId
    ){
        return paymentService.requestFail(errorCode, errorMsg, orderId);
    }

    @GetMapping(path = Path.API_PAYMENT_CANCEL)
    @ApiOperation(
            value = "결제 취소"
    )
    public ResultDto<Boolean> requestPaymentCancel(
            @ApiParam(value = "토스 측 주문 고유 번호", required = true) @RequestParam String paymentKey,
            @ApiParam(value = "결제 취소 사유", required = true) @RequestParam String cancelReason
    ){
        return paymentService.requestCancel(paymentKey, cancelReason);
    }

    @GetMapping(path = Path.API_PAYMENTS)
    @ApiOperation(
            value = "결제내역 조회"
    )
    public ResultDto<List<PaymentDto.ListOfPayments>> getListOfPayments(Pageable pageable){
        return paymentService.getListOfPayments(pageable);
    }
}
