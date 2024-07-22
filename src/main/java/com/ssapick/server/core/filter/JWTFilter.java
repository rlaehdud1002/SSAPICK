package com.ssapick.server.core.filter;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.util.CookieUtils;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final AuthCacheRepository authCacheRepository;
    public final String AUTHORIZATION_HEADER = "Authorization";
    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = resolveToken(request);

        if (accessToken != null) {
            try {
                Authentication authentication = jwtService.parseAuthentication(accessToken);
                validateToken(request, authentication.getName());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // FIXME: 뭔가 문제가 있었는데 기억이 안남.
                SecurityContextHolder.clearContext();
                request.setAttribute("error-message", e.getMessage());
            }
        }
        doFilter(request, response, filterChain);
    }

    private void validateToken(HttpServletRequest request, String username) {
        Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
                () -> new IllegalArgumentException("로그인 되어있지 않습니다.")
        );

        String refreshToken = authCacheRepository.findByUsername(username);

        if (refreshToken == null || refreshToken.equals(cookie.getValue())) {
            throw new IllegalArgumentException("로그아웃된 사용자입니다.");
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
