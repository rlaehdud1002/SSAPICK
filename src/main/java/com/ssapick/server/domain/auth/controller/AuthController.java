package com.ssapick.server.domain.auth.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.core.util.CookieUtils;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.service.AuthService;
import com.ssapick.server.domain.user.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
    private final JwtProperties properties;
    private final AuthService authService;

    @Authenticated
    @PostMapping(value = "/sign-out")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> signOut(
            @CurrentUser User user,
            HttpServletRequest request
    ) {
        Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
                () -> new IllegalArgumentException("로그인 되어있지 않습니다.")
        );

        authService.signOut(user, cookie.getValue());
        CookieUtils.removeCookie(AuthConst.REFRESH_TOKEN);
        return SuccessResponse.empty();
    }

    @Authenticated
    @PostMapping(value = "/refresh")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Void> refresh(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
                () -> new IllegalArgumentException("로그인 되어있지 않습니다.")
        );

        JwtToken refreshedToken = authService.refresh(cookie.getValue());

        CookieUtils.removeCookie(AuthConst.REFRESH_TOKEN);
        CookieUtils.addCookie(AuthConst.REFRESH_TOKEN, refreshedToken.getRefreshToken(), properties.getRefreshExpire(), true);

        return SuccessResponse.created();
    }
}
