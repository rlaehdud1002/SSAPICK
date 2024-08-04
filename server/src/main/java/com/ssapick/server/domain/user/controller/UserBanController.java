package com.ssapick.server.domain.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.UserBanservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-ban")
@RequiredArgsConstructor
public class UserBanController {
	private final UserBanservice userBanService;


	/**
	 * 사용자 차단 API
	 *
	 * @param user   로그인한 사용자
	 * @param userId 차단할 사용자 ID
	 */
	@Authenticated
	@PostMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.CREATED)
	public SuccessResponse<Void> banUser(@CurrentUser User user,  @PathVariable("userId") Long userId) {
		userBanService.banUser(user, userId);
		return SuccessResponse.created();
	}

	/**
	 * 사용자 차단 목록 조회 API
	 *
	 * @param user 로그인한 사용자
	 * @return {@link ProfileData.Search} 차단한 사용자 목록
	 */
	@Authenticated
	@GetMapping
	public SuccessResponse<List<ProfileData.Search>> searchBanUsers(@CurrentUser User user) {
		return SuccessResponse.of(userBanService.searchBanUsers(user));
	}

	/**
	 * 사용자 차단 해제 API
	 *
	 * @param user   로그인한 사용자
	 * @param userId 차단 해제할 사용자 ID
	 */
	@Authenticated
	@DeleteMapping(value = "/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public SuccessResponse<Void> unbanUser(@CurrentUser User user, @PathVariable("userId") Long userId) {
		userBanService.unbanUser(user, userId);
		return SuccessResponse.empty();
	}
}