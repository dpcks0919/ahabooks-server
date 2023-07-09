package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.PayType;
import com.waywalkers.kbook.constant.SubscriptionStatus;
import com.waywalkers.kbook.domain.payment.Payment;
import com.waywalkers.kbook.domain.paymentCancel.PaymentCancel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentDto {

    @Data
    public static class ListOfPayments{
        private PayType payType;
        private Long amount;
        private String orderId;
        private String orderName;
        private String customerEmail;
        private String customerName;
        private LocalDateTime createdAt;
        private SubscriptionStatus subscriptionStatus;
        private boolean paySuccess;
        private String payFailReason;
        private long accountId;
    }

    @Builder
    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class PaymentRequest{
        @ApiModelProperty("지불방법")
        private PayType payType;
        @ApiModelProperty("지불금액")
        private Long amount;
        @ApiModelProperty("주문 상품 이름")
        private String orderName;
        @ApiModelProperty("구매자 이메일")
        private String customerEmail;
        @ApiModelProperty("구매자 이름")
        private String customerName;

        @ApiModelProperty("구매자 id")
        private Long accountId;

        @ApiModelProperty("구독 타입")
        private SubscriptionStatus subscriptionStatus;

        public Payment toEntity() {
            return Payment.builder()
                    .orderId(UUID.randomUUID().toString())
                    .payType(payType)
                    .amount(amount)
                    .orderName(orderName)
                    .customerEmail(customerEmail)
                    .customerName(customerName)
                    .subscriptionStatus(subscriptionStatus)
                    .paySuccess(false)
                    .build();
        }
    }

    @Builder
    @Data
    public static class PaymentResponse{
        private String payType;			// 지불방법
        private Long amount;			// 지불금액
        private String orderId;			// 주문 고유 ID
        private String orderName;		// 주문 상품 이름
        private String customerEmail;	// 구매자 이메일
        private String customerName;	// 구매자 이름
        private String successUrl;		// 성공시 콜백 주소
        private String failUrl;			// 실패시 콜백 주소
        private LocalDateTime createdAt;		// 결제 날짜
        private boolean paySuccess;	// 결제 성공 여부
    }

    @Data
    public static class PaymentResHandleDto {
        String mId;                     // : "tosspayments", 가맹점 ID
        String version;                 // : "1.3", Payment 객체 응답 버전
        String paymentKey;              // : "5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6",
        String orderId;                 // : "IBboL1BJjaYHW6FA4nRjm",
        String orderName;               // : "토스 티셔츠 외 2건",
        String currency;                // : "KRW",
        String method;                  // : "카드", 결제수단
        String totalAmount;             // : 15000,
        String balanceAmount;           // : 15000,
        String suppliedAmount;          // : 13636,
        String vat;                     // : 1364,
        String status;                  // : "DONE", 결제 처리 상태
        String requestedAt;             // : "2021-01-01T10:01:30+09:00",
        String approvedAt;              // : "2021-01-01T10:05:40+09:00",
        String useEscrow;               // : false,
        String cultureExpense;          // : false,
        PaymentDto.PaymentResHandleCardDto card;	// : 카드 결제,
        PaymentResHandleCancelDto[] cancels;	// : 결제 취소 이력 관련 객체
        String type;                    // : "NORMAL",	결제 타입 정보 (NOMAL, BILLING, CONNECTPAY)

        public PaymentCancel toCancelPayment() {
            return PaymentCancel.builder()
                    .orderId(orderId)
                    .orderName(orderName)
                    .paymentKey(paymentKey)
                    .requestedAt(requestedAt)
                    .approvedAt(approvedAt)
                    .cardCompany(card.getCompany())
                    .cardNumber(card.getNumber())
                    .receiptUrl(card.getReceiptUrl())
                    .cancelAmount(cancels[0].getCancelAmount())
                    .cancelDate(cancels[0].getCanceledAt())
                    .cancelReason(cancels[0].getCancelReason())
                    .build();
        }
    }

    @Data
    public static class PaymentResHandleCardDto {
        String company;                     // "현대",
        String number;                      // "433012******1234",
        String installmentPlanMonths;       // 0,
        String isInterestFree;              // false,
        String approveNo;                   // "00000000",
        String useCardPoint;                // false,
        String cardType;                    // "신용",
        String ownerType;                   // "개인",
        String acquireStatus;               // "READY",
        String receiptUrl;                  // "https://merchants.tosspayments.com/web/serve/merchant/test_ck_jkYG57Eba3G06EgN4PwVpWDOxmA1/receipt/5zJ4xY7m0kODnyRpQWGrN2xqGlNvLrKwv1M9ENjbeoPaZdL6"
    }

    @Data
    public static class PaymentResHandleCancelDto {
        Long cancelAmount;
        String canceledAt;
        String cancelReason;
    }

    @Data
    @Builder
    public static class PaymentResHandleFailDto {
        String errorCode;
        String errorMsg;
        String orderId;
    }

}
