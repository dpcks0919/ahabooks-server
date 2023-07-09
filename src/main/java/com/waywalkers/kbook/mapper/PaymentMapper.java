package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.payment.Payment;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PaymentMapper {
    PaymentDto.PaymentResponse PaymentToPaymentResponse(Payment payment);

    AccountDto.AccountPayment PaymentToAccountPayment(Payment payment);

    @Mapping(target = "accountId", expression = "java(payment.getCustomer().getId())")
    PaymentDto.ListOfPayments PaymentToListOfPayments(Payment payment);
}
