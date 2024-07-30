package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("인증 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest extends UserSupport {
    @Mock
    private AuthCacheRepository authCacheRepository;

    @Mock
    private MattermostConfirmService mattermostConfirmService;

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
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BaseException.class)
                .hasMessage("만료된 리프레시 토큰입니다.");
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
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BaseException.class)
                .hasMessage("만료된 토큰입니다.");
    }

    @Test
    @DisplayName("회원 탈퇴에 성공하면 유저의 delete 함수 호출")
    void deleteUserSuccess() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser();

        // * WHEN: 이걸 실행하면
        authService.deleteUser(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(user).delete();
    }

    @Test
    @DisplayName("MM 이름이 1학기 형식일때 성공 테스트")
    void authenticate_성공_테스트() {
        // * GIVEN
        User user = this.createUser();
        MattermostData.Request request = new MattermostData.Request("test", "1234");
        MattermostData.Response response = new MattermostData.Response();
        response.setNickname("이인준[광주_2반]");

        when(mattermostConfirmService.authenticate(request)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.OK));

        // * WHEN
        ProfileData.InitialProfileInfo result = authService.authenticate(user, request);

        // * THEN
        assertEquals("이인준", result.getName());
        assertEquals("광주", result.getLocation());
        assertEquals(2, result.getSection());
    }

    @Test
    @DisplayName("MM 이름이 2학기 형식일때 성공 테스트")
    void authenticate_성공_테스트2() {
        // * GIVEN
        User user = this.createUser();
        MattermostData.Request request = new MattermostData.Request("test", "1234");
        MattermostData.Response response = new MattermostData.Response();
        response.setNickname("이인준[광주_2반_C211]");

        when(mattermostConfirmService.authenticate(request)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.OK));

        // * WHEN
        ProfileData.InitialProfileInfo result = authService.authenticate(user, request);

        // * THEN
        assertEquals("이인준", result.getName());
        assertEquals("광주", result.getLocation());
        assertEquals(2, result.getSection());
    }

    @Test
    @DisplayName("유저 정보가 일치하지 않을 때 실패 테스트")
    void authenticate_유저_실패_테스트() {
        // * GIVEN
        User user = this.createUser();
        MattermostData.Request request = new MattermostData.Request("test", "1234");
        MattermostData.Response response = new MattermostData.Response();
        response.setNickname("이인준[광주_2반]");

        when(mattermostConfirmService.authenticate(request)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));

        // * WHEN
        Runnable runnable = () -> authService.authenticate(user, request);

        // * THEN
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BaseException.class)
                .hasMessage("메타모스트 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("닉네임의 형식이 일치하지 않을 때 실패 테스트")
    void authenticate_닉네임_실패_테스트() {
        // * GIVEN
        User user = this.createUser();
        MattermostData.Request request = new MattermostData.Request("test", "1234");
        MattermostData.Response response = new MattermostData.Response();
        response.setNickname("이인준");

        when(mattermostConfirmService.authenticate(request)).thenReturn(
                new ResponseEntity<>(response, HttpStatus.OK));

        // * WHEN
        Runnable runnable = () -> authService.authenticate(user, request);

        // * THEN
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BaseException.class)
                .hasMessage("올바르지 않은 입력 값입니다.");
    }
}