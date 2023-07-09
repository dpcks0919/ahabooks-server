package com.waywalkers.kbook.domain.paymentCancel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentCancelRepository extends JpaRepository<PaymentCancel, Long> {
    Optional<PaymentCancel> findByPaymentKey(String orderId);
}
