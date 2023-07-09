package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.AccountDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.AccountService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = Path.API_ACCOUNTS)
    @ApiOperation(
            value = "계정 목록 조회"
    )
    public ResultDto<List<AccountDto.ListOfAccounts>> getListOfAccounts(Pageable pageable){
        return accountService.getListOfAccounts(pageable);
    }

    @GetMapping(path = Path.API_ACCOUNT)
    @ApiOperation(
            value = "계정 상세 조회"
    )
    public ResultDto<AccountDto.AccountDetail> getAccountDetail(@PathVariable("account-id") long id){
        return accountService.getAccountDetail(id);
    }

    @PostMapping(path = Path.API_ACCOUNTS)
    @ApiOperation(
            value = "계정 생성"
    )
    public ResultDto<Long> createAccount(@RequestBody AccountDto.PostAccount PostAccount){
        return accountService.createAccount(PostAccount);
    }

    @PutMapping(path = Path.API_ACCOUNT)
    @ApiOperation(
            value = "계정 수정"
    )
    public ResultDto<Long> updateAccount(@PathVariable("account-id") long id, @RequestBody AccountDto.PutAccount putAccount){
        return accountService.updateAccount(id, putAccount);
    }

    @PutMapping(path = Path.API_ACCOUNT_VULNERABLE)
    @ApiOperation(
            value = "계정 취약계층 여부 수정"
    )
    public ResultDto<Long> updateAccount(@PathVariable("account-id") long id, boolean isVulnerable){
        return accountService.updateAccountVulnerable(id, isVulnerable);
    }


    @DeleteMapping(path = Path.API_ACCOUNT)
    @ApiOperation(
            value = "계정 삭제"
    )
    public ResultDto deleteAccount(@PathVariable("account-id") long id){
        return accountService.deleteAccount(id);
    }

    @GetMapping(path = Path.API_ACCOUNT_PAYMENT)
    @ApiOperation(
            value = "계정 결제내역 조회"
    )
    public ResultDto<List<AccountDto.AccountPayment>> getAccountPaymentList(@PathVariable("account-id") long id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable){
        return accountService.getListOfAccountPayment(id, pageable);
    }

}
