package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.domain.payment.PaymentRepository;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.HomeDto;
import com.waywalkers.kbook.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class HomeService {

    private final AccountRepository accountRepository;
    private final RecordRepository recordRepository;
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public ResultDto getHome() {
        HomeDto.HomeCard homeCard = new HomeDto.HomeCard();
        homeCard.setTotalUser(accountRepository.findAll().size());
        homeCard.setTotalRecord(recordRepository.findAll().size());
        homeCard.setTotalPayment(paymentRepository.findAllByPaySuccess(true).stream().mapToLong(payment -> payment.getAmount()).sum());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(homeCard)
                .build();
    }
}
