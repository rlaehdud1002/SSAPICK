package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.config.RedisTestConfig;
import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.AuthenticatedSupport;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import feign.FeignException;
import io.micrometer.observation.Observation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthService.class)
class AuthServiceTest extends AuthenticatedSupport {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthCacheRepository authCacheRepository;

    @MockBean
    private MattermostConfirmService mattermostConfirmService;

    @Autowired
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

    @Test
    @DisplayName("mattermost 인증 정상 테스트")
    void mattermost정상인증되면_사용자인증_확인_테스트() {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", "exampleToken"); // 헤더에 "token" 이름으로 토큰 추가

        ResponseEntity<MattermostData.Response> response = new ResponseEntity<>(new MattermostData.Response(), headers, HttpStatus.OK);

        when(mattermostConfirmService.authenticate(any())).thenReturn(response);

        // * WHEN: 이걸 실행하면
        authService.authenticate(user, new MattermostData.Request());

        // * THEN: 이런 결과가 나와야 한다
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("mattermost 인증 실패 테스트")
    void mattermost인증_실패_예외처리_테스트() {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser();
        MattermostData.Request request = new MattermostData.Request(); // 테스트에 필요한 데이터를 적절히 생성

        when(mattermostConfirmService.authenticate(request))
                .thenThrow(FeignException.Unauthorized.class);

        // * THEN: 이런 결과가 나와야 한다
        assertThrows(IllegalArgumentException.class, () ->
                authService.authenticate(user, request));
    }
}