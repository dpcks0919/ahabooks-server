package com.waywalkers.kbook.service;

import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelation;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.profile.Profile;
import com.waywalkers.kbook.dto.ProfileDto;
import com.waywalkers.kbook.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountProfileRelationService {
    private final AccountRepository accountRepository;
    private final AccountProfileRelationRepository accountProfileRelationRepository;
    private final ProfileService profileService;

    public ResultDto createAccountProfileRelation(long id, ProfileDto.PostProfile postProfile) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        Profile profile = profileService.postProfile(postProfile);
        AccountProfileRelation accountProfileRelation = this.postAccountProfileRelation(account, profile);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(accountProfileRelation.getId())
                .build();
    }

    private AccountProfileRelation postAccountProfileRelation(Account account, Profile profile){
        AccountProfileRelation accountProfileRelation = accountProfileRelationRepository.save(
                AccountProfileRelation.builder()
                        .account(account)
                        .profile(profile)
                        .build()
        );
        return accountProfileRelation;
    }
}
