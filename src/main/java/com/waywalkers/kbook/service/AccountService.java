package com.waywalkers.kbook.service;

import com.waywalkers.kbook.constant.AccountType;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelation;
import com.waywalkers.kbook.domain.accountProfileRelation.AccountProfileRelationRepository;
import com.waywalkers.kbook.domain.evaluation.EvaluationRepository;
import com.waywalkers.kbook.domain.payment.Payment;
import com.waywalkers.kbook.domain.payment.PaymentRepository;
import com.waywalkers.kbook.domain.profile.ProfileRepository;
import com.waywalkers.kbook.domain.profileBookRelation.ProfileBookRelationRepository;
import com.waywalkers.kbook.domain.record.RecordRepository;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.ProfileDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.mapper.AccountMapper;
import com.waywalkers.kbook.mapper.PageMapper;
import com.waywalkers.kbook.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final EvaluationRepository evaluationRepository;
    private final RecordRepository recordRepository;
    private final ProfileRepository profileRepository;
    private final ProfileBookRelationRepository profileBookRelationRepository;
    private final AccountProfileRelationRepository accountProfileRelationRepository;
    private final PaymentRepository paymentRepository;
    private final AccountMapper accountMapper;
    private final PaymentMapper paymentMapper;
    private final PageMapper pageMapper;

    @Transactional(readOnly = true)
    public ResultDto getListOfAccounts(Pageable pageable) {
        Page<Account> accountsPage = accountRepository.findAll(pageable);
        List<AccountDto.ListOfAccounts> listOfAccounts = accountsPage.getContent().stream().map(accountMapper::AccountToListOfAccounts).collect(Collectors.toList());
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(listOfAccounts)
                .page(pageMapper.PageToPageableDto(accountsPage))
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getAccountDetail(long id) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        AccountDto.AccountDetail accountDetail = accountMapper.AccountToAccountDetail(account);
        accountDetail.setProfiles(account.getAccountProfileRelations().stream()
                .map(accountProfileRelation -> new ProfileDto.AccountProfile(accountProfileRelation.getProfile()))
                .collect(Collectors.toList()));
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(accountDetail)
                .build();
    }

    public ResultDto createAccount(AccountDto.PostAccount PostAccount) {
       Account account = accountRepository.save(
               Account.builder()
                .id(PostAccount.getId())
                .accountType(PostAccount.getAccountType())
                .name(PostAccount.getName())
                .email(PostAccount.getEmail())
                .phone(PostAccount.getPhone())
                .build()
       );
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(account.getId())
                .build();
    }

    public ResultDto updateAccount(long id, AccountDto.PutAccount putAccount) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        account.update(putAccount);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(account.getId())
                .build();
    }

    public ResultDto updateAccountVulnerable(long id, boolean isVulnerable) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        account.updateVulnerable(isVulnerable);
        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(account.getId())
                .build();
    }

    public ResultDto deleteAccount(long id) {
        Account account = accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        List<Long> profileIds = account.getAccountProfileRelations().stream().map(accountProfileRelation -> accountProfileRelation.getProfile().getId()).collect(Collectors.toList());

        // evaluation 삭제
        evaluationRepository.deleteAllByRecord_ProfileBookRelation_Profile_IdIn(profileIds);
        // record 삭제
        recordRepository.deleteAllByProfileBookRelation_Profile_IdIn(profileIds);
        // profile_book_relation 삭제
        profileBookRelationRepository.deleteAllByProfile_IdIn(profileIds);
        // account_profile_relation 삭제
        accountProfileRelationRepository.deleteAllByProfile_IdIn(profileIds);
        // profile 삭제
        profileRepository.deleteAllByIdIn(profileIds);
        // account 삭제
        accountRepository.delete(account);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Transactional(readOnly = true)
    public ResultDto getListOfAccountPayment(long id, Pageable pageable) {
        accountRepository.findById(id).orElseThrow( () ->new EntityNotFoundException("account"));
        Page<Payment> paymentsPage = paymentRepository.findAllByCustomer_Id(id, pageable);
        List<AccountDto.AccountPayment> accountPayments = paymentsPage.getContent().stream().map(paymentMapper::PaymentToAccountPayment).collect(Collectors.toList());
        return ResultDto.builder()
                .data(accountPayments)
                .page(pageMapper.PageToPageableDto(paymentsPage))
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public Account createAccount(String id, AccountType accountType) {
        Account account = Account.builder()
                .id(Long.parseLong(id))
                .accountType(accountType)
                .build();
        return accountRepository.save(account);
    }

}
