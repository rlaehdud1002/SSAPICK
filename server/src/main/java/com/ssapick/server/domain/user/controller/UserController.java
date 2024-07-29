package com.ssapick.server.domain.user.controller;

import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	/**
	 * 로그인한 사용자 정보 조회 API
	 * @return {@link ProfileData.Search} 로그인한 사용자 정보
	 */
	@Authenticated
	@GetMapping(value = "")
	public SuccessResponse<ProfileData.Search> findLoggedInUser(@CurrentUser User user) {
		return SuccessResponse.of(null);
	}

	@PatchMapping(value = "", consumes = "multipart/form-data")
	public SuccessResponse<Void> updateProfile(
		@CurrentUser User user,
		@RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
		@Valid @RequestBody ProfileData.Update update
	) {
		userService.updateUser(user, update);
		return SuccessResponse.of(null);
	}

}
