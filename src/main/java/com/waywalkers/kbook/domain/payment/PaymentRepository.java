package com.waywalkers.kbook.domain.payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
     List<Payment> findAllByPaySuccess(boolean paySuccess);
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentKeyAndAmount(String orderId, Long amount);
    Page<Payment> findAllByCustomer_Id(long accountId, Pageable pageable);
}
