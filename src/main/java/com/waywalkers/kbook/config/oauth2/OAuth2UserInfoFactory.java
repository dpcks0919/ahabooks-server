package com.waywalkers.kbook.config.oauth2;

import com.waywalkers.kbook.constant.AccountType;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(AccountType accountType, Map<String, Object> attributes) {
        switch (accountType) {
            case KAKAO: return new KakaoOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}