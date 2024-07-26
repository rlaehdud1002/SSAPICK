package com.ssapick.server.domain.auth.handler;


import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.util.CookieUtils;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.response.CustomOAuth2User;
import com.ssapick.server.domain.auth.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTService jwtService;
    private final JwtProperties properties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        JwtToken jwtToken = jwtService.generateToken(customUserDetails.getUsername(), customUserDetails.getAuthorities());
        response.addCookie(CookieUtils.addCookie(AuthConst.REFRESH_TOKEN, jwtToken.getRefreshToken(), properties.getRefreshExpire(), true));

        String redirectURI = UriComponentsBuilder.fromUriString("http://localhost:3000/")
                .queryParam("access_token", jwtToken.getAccessToken())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectURI);
    }
}
