package com.waywalkers.kbook.service;

import com.waywalkers.kbook.constant.AccountType;
import com.waywalkers.kbook.domain.account.Account;
import com.waywalkers.kbook.domain.key.AppleKeySet;
import com.waywalkers.kbook.domain.key.Key;
import com.waywalkers.kbook.dto.KeyDto;
import com.waywalkers.kbook.service.util.CookieUtil;
import com.waywalkers.kbook.config.jwt.JwtTokenProvider;
import com.waywalkers.kbook.config.oauth2.CustomUserDetails;
import com.waywalkers.kbook.domain.account.AccountRepository;
import com.waywalkers.kbook.dto.ResultDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.security.auth.login.LoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

    @Value("${app.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    @Value("${app.oauth2.registerUri}")
    private String registerUri;
    @Value("${app.auth.token.refresh-cookie-key}")
    private String cookieKey;
    private final AccountRepository accountRepository;
    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;
    private final AccountService accountService;


    public ResultDto refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {
        // 1. Validation Refresh Token
        String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
                .map(Cookie::getValue).orElseThrow(() -> new RuntimeException("no Refresh Token Cookie"));

        if (!tokenProvider.validateToken(oldRefreshToken)) {
            throw new RuntimeException("Not Validated Refresh Token");
        }

        // 2. 유저정보 얻기
        Authentication authentication = tokenProvider.getAuthentication(oldAccessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());

        // 3. Match Refresh Token
        String savedToken = accountRepository.getRefreshTokenById(id);

        if (!savedToken.equals(oldRefreshToken)) {
            throw new RuntimeException("Not Matched Refresh Token");
        }

        // 4. JWT 갱신
        String accessToken = tokenProvider.createAccessToken(authentication);
        tokenProvider.createRefreshToken(authentication, response);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(accessToken)
                .build();
    }

    public ResultDto logout(String accessToken) {
        //access token 블랙리스트 추가
        Long expiration = tokenProvider.getExpiration(accessToken);
        redisTemplate.opsForValue()
                .set(accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

        // refresh token 삭제
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        Long id = Long.valueOf(user.getName());
        accountRepository.updateRefreshToken(id, null);

        return ResultDto.builder()
                .httpStatus(HttpStatus.OK)
                .build();
    }

    public String appleLogin(HttpServletResponse response, KeyDto.AppleKey appleKey) throws Exception{
        AppleKeySet appleKeySet = new RestTemplateBuilder().build()
                .getForObject("https://appleid.apple.com/auth/keys", AppleKeySet.class);
        String identityToken = appleKey.getIdentityToken();
        String nonceToken = appleKey.getNonceToken();
        String uri = new String();

        List<Key> applePublicKeys = appleKeySet.getKeys();

        for (Key applePublicKey : applePublicKeys) {
            try{
                BigInteger n = new BigInteger(1, Base64.getUrlDecoder().decode(applePublicKey.getN()));
                BigInteger e = new BigInteger(1, Base64.getUrlDecoder().decode(applePublicKey.getE()));

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                KeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                Claims claims = Jwts.parser()
                        .setSigningKey(publicKey)
                        .parseClaimsJws(identityToken)
                        .getBody();

                String nonceInIdentityToken = claims.get("nonce").toString();
                String hashedNonce = hashTextBySHA256(nonceToken);

                if (nonceInIdentityToken.equals(hashedNonce)) {
                    String sub = String.valueOf(claims.get("sub")).split("\\.")[0];
                    Long id = Long.valueOf(sub);
                    Optional<Account> accountOptional = accountRepository.findById(id);
                    String email = claims.get("email").toString();
                    Account account;
                    if (accountOptional.isPresent()) {		// 이미 가입된 경우
                        account = accountOptional.get();
                    } else {			// 가입되지 않은 경우
                        account = accountService.createAccount(sub, AccountType.APPLE);
                    }

                    String accessToken = tokenProvider.createAccessToken(account);
                    tokenProvider.createRefreshToken(id, response);

                    if(account.getEmail() != null ){
                        uri =  UriComponentsBuilder.fromUriString(redirectUri)
                                .queryParam("accessToken", accessToken)
                                .queryParam("id", account.getId())
                                .build().toUriString();
                    }else{
                        uri = UriComponentsBuilder.fromUriString(registerUri)
                                .queryParam("accessToken", accessToken)
                                .queryParam("id", account.getId())
                                .queryParam("email", email)
                                .build().toUriString();
                    }
                }else{
                    throw new LoginException();
                }
            }catch (io.jsonwebtoken.SignatureException e){
                continue;
            }catch (LoginException e){
                throw new LoginException();
            }
        }

        return uri;
    }

    private String hashTextBySHA256(String text) throws Exception{
        MessageDigest mdSHA256 = MessageDigest.getInstance("SHA-256");
        mdSHA256.update(text.getBytes("UTF-8"));

        byte[] sha256Hash = mdSHA256.digest();

        StringBuffer hexSHA256hash = new StringBuffer();

        for(byte b : sha256Hash) {
            String hexString = String.format("%02x", b);
            hexSHA256hash.append(hexString);
        }
        return hexSHA256hash.toString();
    }

}
