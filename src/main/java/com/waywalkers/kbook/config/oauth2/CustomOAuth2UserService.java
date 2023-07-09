package com.waywalkers.kbook.config.oauth2;

import com.waywalkers.kbook.config.oauth2.CustomUserDetails;
import com.waywalkers.kbook.config.oauth2.OAuth2UserInfo;
import com.waywalkers.kbook.config.oauth2.OAuth2UserInfoFactory;
import com.waywalkers.kbook.constant.AccountType;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.exception.OAuthProcessingException;
import com.waywalkers.kbook.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    // OAuth2UserRequest에 있는 Access Token으로 유저정보 get
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        return process(oAuth2UserRequest, oAuth2User);
    }

    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        AccountType authProvider = AccountType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        if (userInfo.getId().isEmpty()) {
            throw new OAuthProcessingException("id not found from OAuth2 provider");
        }
        Optional<Account> accountOptional = accountRepository.findById( Long.parseLong(userInfo.getId()));
        Account account;
        if (accountOptional.isPresent()) {		// 이미 가입된 경우
            account = accountOptional.get();
            if (authProvider != account.getAccountType()) {
                throw new OAuthProcessingException("Wrong Match Auth Provider");
            }
        } else {			// 가입되지 않은 경우
            account = accountService.createAccount(userInfo.getId(), authProvider);
        }
        return CustomUserDetails.create(account, oAuth2User.getAttributes());
    }
}