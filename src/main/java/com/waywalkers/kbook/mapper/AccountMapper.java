package com.waywalkers.kbook.mapper;

import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {
    @Mapping(target = "profileCount", expression = "java(account.getProfileCount())")
    @Mapping(target = "totalPayment", expression = "java(account.getTotalPayment())")
    AccountDto.ListOfAccounts AccountToListOfAccounts(Account account);

    AccountDto.AccountDetail AccountToAccountDetail(Account account);
}
