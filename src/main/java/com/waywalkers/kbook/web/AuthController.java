package com.waywalkers.kbook.web;

import com.waywalkers.kbook.constant.Path;
import com.waywalkers.kbook.dto.KeyDto;
import com.waywalkers.kbook.dto.ResultDto;
import com.waywalkers.kbook.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = Path.API_APPLE_LOGIN)
    @ApiOperation(
            value = "애플 로그인"
    )
    public String appleLogin(HttpServletResponse response, @RequestBody KeyDto.AppleKey appleKey) throws Exception{
        return authService.appleLogin(response, appleKey);
    }

    @PostMapping(path = Path.API_REFRESH)
    @ApiOperation(
            value = "user token 갱신"
    )
    public ResultDto<String> refreshToken(HttpServletRequest request, HttpServletResponse response, @RequestBody String accessToken) {
        return authService.refreshToken(request, response, accessToken);
    }

    @PostMapping(path = Path.API_REDIRECT_LOGOUT)
    @ApiOperation(
            value = "로그 아웃",
            hidden = true
    )
    public ResultDto logout(@RequestBody String accessToken) {
        return authService.logout(accessToken);
    }
}
