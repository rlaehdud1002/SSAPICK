package com.ssapick.server.domain.pick.controller;

import java.util.List;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.service.PickService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pick")
public class PickController {

	private final PickService pickService;

	/**
	 * 받은 픽 조회하기
	 * @param userId
	 * @return
	 */
	@Authenticated
	@GetMapping("/received")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> getReceivedPick(Long userId) {
		return SuccessResponse.of(pickService.searchReceiver(userId));
	}

	/**
	 * 보낸 픽 조회하기
	 * @param userId
	 * @return
	 */
	@Authenticated
	@GetMapping("/sent")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> getSentPick(Long userId) {
		return SuccessResponse.of(pickService.searchSender(userId));
	}

	/**
	 * 픽 생성하기
	 * @param request
	 * @return
	 */
	@Authenticated
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<Void> createPick(
			@CurrentUser User user,
			@RequestBody PickData.Create request
	) {
		pickService.createPick(user, request);
		return SuccessResponse.empty();
	}
}
