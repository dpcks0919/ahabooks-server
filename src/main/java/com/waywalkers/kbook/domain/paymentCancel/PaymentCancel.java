package com.waywalkers.kbook.domain.paymentCancel;

import com.waywalkers.kbook.domain.account.Account;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, unique = true)
    private Long seq;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String paymentKey;

    private String orderName;
    private String requestedAt;
    private String approvedAt;
    private String cardCompany;
    private String cardNumber;
    private String receiptUrl;
    private Long cancelAmount;
    private String cancelDate;
    private String cancelReason;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Account customer;
}
