package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.support.AuthenticatedSupport;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest extends AuthenticatedSupport {

    @Mock
    private AuthCacheRepository authCacheRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthService authService;


    @Test
    @DisplayName("로그아웃 정상 테스트")
    @WithMockUser(username = "test")
    void 로그아웃_정상_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser();
        String refreshToken = "refreshToken";
        when(authCacheRepository.existsByUsername(anyString())).thenReturn(false);

        // * WHEN: 이걸 실행하면
        authService.signOut(user, refreshToken);

        // * THEN: 이런 결과가 나와야 한다
        verify(authCacheRepository).save(AuthConst.SIGN_OUT_CACHE_KEY + user.getUsername(), refreshToken);
    }

    @Test
    @DisplayName("이미 로그아웃된 사용자 로그아웃 요청 테스트")
    void 이미_로그아웃된_사용자_로그아웃_요청_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser();
        String refreshToken = "refreshToken";
        when(authCacheRepository.existsByUsername(anyString())).thenReturn(true);

        // * WHEN: 이걸 실행하면
        Runnable runnable = () -> authService.signOut(user, refreshToken);

        // * THEN: 이런 결과가 나와야 한다
        assertThrows(IllegalArgumentException.class, runnable::run);
    }

    @Test
    @DisplayName("토큰 재발급 정상 테스트")
    void 토큰_재발급_정상_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        JwtToken token = JwtToken.of("accessToken", "refreshToken");
        when(jwtService.refreshToken(anyString())).thenReturn(token);

        // * WHEN: 이걸 실행하면
        JwtToken created = authService.refresh("refreshToken");

        // * THEN: 이런 결과가 나와야 한다
        verify(jwtService).refreshToken("refreshToken");
        assertEquals(token, created);
    }

    @Test
    @DisplayName("이미 로그아웃된 사용자 토큰 재발급 X 테스트")
    void 이미_로그아웃된_사용자_토큰_재발급X_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        when(authCacheRepository.existsByUsername(anyString())).thenReturn(true);

        // * WHEN: 이걸 실행하면
        Runnable runnable = () -> authService.refresh("refreshToken");

        // * THEN: 이런 결과가 나와야 한다
        assertThrows(IllegalArgumentException.class, runnable::run);
    }
}