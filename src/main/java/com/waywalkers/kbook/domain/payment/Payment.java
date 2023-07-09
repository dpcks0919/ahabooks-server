package com.waywalkers.kbook.domain.payment;

import com.waywalkers.kbook.constant.PayType;
import com.waywalkers.kbook.constant.SubscriptionStatus;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.PaymentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, unique = true)
    private Long seq;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayType payType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus subscriptionStatus;

    private boolean paySuccess;

    private String payFailReason;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Account customer;

    private String paymentKey;

    public void setCustomer(Account account){
        this.customer = account;
    }

    public void setPaymentKey(String paymentKey){
        this.paymentKey = paymentKey;
    }

    public void paymentSuccess(){
        this.paySuccess = true;
    }

    public void paymentFail(String errorMsg){
        this.paySuccess = false;
        this.payFailReason = errorMsg;
    }
}