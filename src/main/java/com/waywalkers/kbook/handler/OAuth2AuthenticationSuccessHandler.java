package com.waywalkers.kbook.handler;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.waywalkers.kbook.config.jwt.JwtTokenProvider;
import com.waywalkers.kbook.config.oauth2.CustomUserDetails;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    @Value("${app.oauth2.registerUri}")
    private String registerUri;
    private final JwtTokenProvider tokenProvider;
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl;
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Account account = accountRepository.findById(Long.parseLong(user.getName())).get();
        Map<String, Object> kakaoAccount = (Map<String, Object>) user.getAttributes().get("kakao_account");
        String nickname = kakaoAccount.get("profile") != null ? (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname") : "";
        String email = kakaoAccount.get("email") != null ? (String) kakaoAccount.get("email") : "";
        // JWT 생성
        String accessToken = tokenProvider.createAccessToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        if(account.getEmail() != null ){
            return UriComponentsBuilder.fromUriString(redirectUri)
                    .queryParam("accessToken", accessToken)
                    .queryParam("id", account.getId())
                    .build().toUriString();
        }else{
            return UriComponentsBuilder.fromUriString(registerUri)
                    .queryParam("accessToken", accessToken)
                    .queryParam("id", account.getId())
                    .queryParam("nickname", nickname)
                    .queryParam("email", email)
                    .build().toUriString();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }

}