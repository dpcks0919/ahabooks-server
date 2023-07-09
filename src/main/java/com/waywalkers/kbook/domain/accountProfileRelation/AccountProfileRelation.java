package com.waywalkers.kbook.domain.accountProfileRelation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.waywalkers.kbook.domain.BaseTimeEntity;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.profile.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name="account_profile_relation")
@Entity
public class AccountProfileRelation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_profile_relation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;


    @Builder
    public AccountProfileRelation(Account account, Profile profile) {
        this.account = account;
        this.profile = profile;
    }
}
