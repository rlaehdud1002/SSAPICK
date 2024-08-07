package com.ssapick.server.domain.auth.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.core.util.CookieUtils;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.service.AuthService;
import com.ssapick.server.domain.user.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
		HttpServletRequest request,
		HttpServletResponse response
	) {
		Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
			() -> new BaseException(ErrorCode.UNAUTHORIZED)
		);

		authService.signOut(user, cookie.getValue());
		CookieUtils.removeCookie(response, AuthConst.REFRESH_TOKEN);
		return SuccessResponse.empty();
	}

	@Authenticated
	@PostMapping(value = "/refresh")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<JwtToken> refresh(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = CookieUtils.getCookie(request, AuthConst.REFRESH_TOKEN).orElseThrow(
			() -> new BaseException(ErrorCode.UNAUTHORIZED)
		);

		JwtToken refreshedToken = authService.refresh(cookie.getValue());

		CookieUtils.removeCookie(response, AuthConst.REFRESH_TOKEN);
		CookieUtils.addCookie(response, AuthConst.REFRESH_TOKEN, refreshedToken.getRefreshToken(), properties.getRefreshExpire(), true);
		return SuccessResponse.of(refreshedToken);
	}

	@Authenticated
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse<Void> deleteUser(@CurrentUser User user) {
		authService.deleteUser(user);
		return SuccessResponse.empty();
	}

	@Authenticated
	@PostMapping("/mattermost-confirm")
	public SuccessResponse<Void> authenticate(
		@CurrentUser User user,
		@RequestBody MattermostData.Request request
	) {
		authService.authenticate(user, request);
		return SuccessResponse.empty();
	}

	@Authenticated
	@GetMapping("/mattermost-confirm")
	public SuccessResponse<MattermostData.Authenticated> isAuthenticated(
			@CurrentUser User user
	) {
		return SuccessResponse.of(new MattermostData.Authenticated(authService.isUserAuthenticated(user)));
	}
}
