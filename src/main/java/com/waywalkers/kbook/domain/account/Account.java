package com.waywalkers.kbook.domain.account;

import com.waywalkers.kbook.constant.AccountType;
import com.waywalkers.kbook.constant.Role;
import com.waywalkers.kbook.constant.SubscriptionStatus;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelation;
import com.waywalkers.kbook.domain.payment.Payment;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.ProfileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Table(name="account")
@Entity
public class Account extends BaseTimeEntity {
    @Id
    @Column(name = "account_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    private String name;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status")
    private SubscriptionStatus subscriptionStatus;

    @Column(name = "subscription_deadline")
    private LocalDateTime subscriptionDeadline;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isVulnerable;

    private String refreshToken;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "account",
            fetch = FetchType.LAZY
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AccountProfileRelation> accountProfileRelations = new ArrayList<>();

    @Builder
    public Account(long id, AccountType accountType, String name, String email, String phone) {
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subscriptionStatus = SubscriptionStatus.UNSUBSCRIBE;
        this.subscriptionDeadline = null;
        this.role = Role.USER;
        this.isVulnerable = false;
    }

    public void subscribe(SubscriptionStatus subscriptionStatus){
        if(subscriptionStatus.equals(SubscriptionStatus.MONTHLY)){
            if( this.subscriptionDeadline == null){
                this.subscriptionDeadline = LocalDateTime.now().plusMonths(1);
            }else{
                this.subscriptionDeadline = this.subscriptionDeadline.plusMonths(1);
            }
            this.subscriptionStatus = SubscriptionStatus.MONTHLY;
        }else if(subscriptionStatus.equals(SubscriptionStatus.YEARLY)){
            if( this.subscriptionDeadline == null){
                this.subscriptionDeadline = LocalDateTime.now().plusYears(1);
            }else{
                this.subscriptionDeadline = this.subscriptionDeadline.plusYears(1);
            }
            this.subscriptionStatus = SubscriptionStatus.YEARLY;
        }
    }

    public String getAuthRole(){
        return "ROLE_" + getRole();
    }

    public int getProfileCount(){
        return this.accountProfileRelations.size();
    }

    public long getTotalPayment(){
        return this.payments.stream().mapToLong(Payment::getAmount).sum();
    }

    public void update(AccountDto.PutAccount putAccount) {
        if( putAccount.getName() != null){
            this.name = putAccount.getName();
        }
        if(putAccount.getEmail() != null){
            this.email = putAccount.getEmail();
        }
        if(putAccount.getPhone() != null ){
            this.phone = putAccount.getPhone();
        }
    }

    public void updateVulnerable(boolean isVulnerable){
        this.isVulnerable = isVulnerable;
    }
}
