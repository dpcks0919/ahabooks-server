package com.waywalkers.kbook.dto;

import com.waywalkers.kbook.constant.AccountType;
import com.waywalkers.kbook.constant.PayType;
import com.waywalkers.kbook.constant.Role;
import com.waywalkers.kbook.constant.SubscriptionStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class AccountDto {

    @Data
    public static class ListOfAccounts{
        private long id;
        private String name;
        private String email;
        private SubscriptionStatus subscriptionStatus;
        private LocalDateTime createdAt;
        private Integer profileCount;
        private long totalPayment;
    }

    @Data
    public static class AccountDetail{
        private long id;
        private String name;
        private String email;
        private String phone;
        private Role role;
        private SubscriptionStatus subscriptionStatus;
        private LocalDateTime subscriptionDeadline;
        private List<ProfileDto.AccountProfile> profiles;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class PostAccount {
        private long id;
        private AccountType accountType;
        private String name;
        private String email;
        private String phone;
    }

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public static class PutAccount {
        private String name;
        private String email;
        private String phone;
    }

    @Builder
    @Data
    public static class LoginProcessAccount{
        private boolean isNew;
        private long id;
        private AccountType accountType;
        private String name;
        private String email;
    }

    @Builder
    @Data
    public static class AccountPayment{
        private PayType payType;
        private Long amount;
        private SubscriptionStatus subscriptionStatus;
        private LocalDateTime createdAt;
    }

    @Builder
    @Data
    public static class EvaluateAccount{
        private Long id;
        private String name;
    }

}
