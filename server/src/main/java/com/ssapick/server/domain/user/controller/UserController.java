package com.ssapick.server.domain.user.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.core.service.S3Service;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.dto.UserData;
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
	private final S3Service s3Service;
	private final AmazonS3 s3;

	/**
	 * 로그인한 사용자 정보 조회 API
	 *
	 * @return {@link ProfileData.Search} 로그인한 사용자 정보
	 */
	@Authenticated
	@GetMapping(value = "/me")
	public SuccessResponse<UserData.UserInfo> findLoggedInUser(@CurrentUser User user) {
		return SuccessResponse.of(userService.getUserInfo(user));
	}

	@PatchMapping(value = "", consumes = "multipart/form-data")
	public SuccessResponse<Void> updateProfile(
		@CurrentUser User user,
		@RequestPart(value = "profileImage", required = false) MultipartFile profileImage,
		@Validated @RequestPart UserData.Update update,
		Errors errors
	) {
		if (errors.hasErrors()) {
			log.error("사용자 수정 입력 값 에러 발생: {}", errors);
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		userService.updateUser(user.getId(), update, profileImage);
		return SuccessResponse.of(null);
	}

}
