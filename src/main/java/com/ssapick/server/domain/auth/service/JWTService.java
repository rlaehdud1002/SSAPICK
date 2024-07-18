package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.response.CustomOAuth2User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JWTService {
    private final JwtProperties properties;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = new SecretKeySpec(properties.getSecret().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /**
     * 인증된 사용자 정보를 통해 토큰을 생성
     * @param user {@link CustomOAuth2User} 인증된 사용자 정보
     * @return {@link JwtToken} 토큰
     */
    public JwtToken generateToken(CustomOAuth2User user) {
        String authority = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();

        Date accessTokenExpire = new Date(now + properties.getAccessExpire());
        Date refreshTokenExpire = new Date(now + properties.getRefreshExpire());

        String accessToken = Jwts.builder()
                .header().add("typ", "JWT").add("alg", "HS256")
                .and()
                .subject(user.getUsername())
                .claim("authorities", authority)
                .issuedAt(new Date(now))
                .expiration(accessTokenExpire)
                .signWith(secretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .subject(user.getUsername())
                .claim("authorities", authority)
                .issuedAt(new Date(now))
                .expiration(refreshTokenExpire)
                .signWith(secretKey)
                .compact();

        return JwtToken.of(accessToken, refreshToken);
    }

    /**
     * accessToken 파싱
     *
     * @param accessToken JWT 토큰
     * @return 토큰의 클레임
     */
    private Claims parseClaims(String accessToken) throws Exception {
        String message;
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            message = "유효기간이 만료된 토큰입니다.";
        } catch (MalformedJwtException e) {
            message = "잘못된 형식의 토큰입니다.";
        } catch (IllegalArgumentException e) {
            message = "잘못된 인자입니다.";
        } catch (Exception e) {
            message = "토큰 파싱 중 에러가 발생했습니다.";
        }
        throw new Exception(message);
    }

    public Authentication parseAuthentication(String accessToken) throws Exception {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("authorities").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        UserDetails principal = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
}
