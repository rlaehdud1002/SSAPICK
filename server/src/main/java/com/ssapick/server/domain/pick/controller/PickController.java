package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.service.PickService;
import com.ssapick.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pick")
public class PickController {
	private final PickService pickService;

	/**
	 * 받은 픽 조회하는 API
	 *
	 * @param user 로그인한 유저
	 * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 받은 픽 리스트
	 */
	@Authenticated
	@GetMapping("/receive")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<Page<PickData.Search>> getReceivePick(
		@CurrentUser User user,
		Pageable pageable) {

		Page<PickData.Search> pickPage = pickService.searchReceivePick(user, pageable);
		return SuccessResponse.of(pickPage);
	}

	/**
	 * 보낸 픽 조회하는 API
	 *
	 * @param user 로그인한 유저
	 * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 보낸 픽 리스트
	 */
	@Authenticated
	@GetMapping("/send")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> getSendPick(@CurrentUser User user) {
		return SuccessResponse.of(pickService.searchSendPick(user));
	}

	/**
	 * 픽 생성하는 API
	 *
	 * @param user   로그인한 유저
	 * @param create {@link com.ssapick.server.domain.pick.dto.PickData.Create} 픽 생성 정보
	 * @return 처리 성공 응답
	 */
	@Authenticated
	@PostMapping("")
	public SuccessResponse<PickData.PickCondition> createPick(
		@CurrentUser User user,
		@Validated @RequestBody PickData.Create create
	) {
		PickData.PickCondition pickCondition = pickService.createPick(user, create);

		return SuccessResponse.of(pickCondition);
	}

	/**
	 * 현재 픽 진행 상태 조회하는 API
	 *
	 * @param user   로그인한 유저
	 * @return 처리 성공 응답
	 */
	@Authenticated
	@GetMapping("")
	public SuccessResponse<PickData.PickCondition> getPickCondition(
		@CurrentUser User user
	) {
		PickData.PickCondition pickCondition = pickService.getPickCondition(user);

		return SuccessResponse.of(pickCondition);
	}

	/**
	 * 픽 알람설정 API
	 *
	 *
	 */
	@PatchMapping("/{pickId}")
	public SuccessResponse<List<PickData.Search>> updatePickAlarm(
		@CurrentUser User user,
		@PathVariable("pickId") Long pickId
	) {
		pickService.updatePickAlarm(user, pickId);
		return SuccessResponse.of(pickService.searchReceivePick(user));
	}

	/**
	 * 선택지의 사용자 리롤
	 */
	@PatchMapping("/re-roll")
	public SuccessResponse<Void> rerollPick(
		@CurrentUser User user
	) {
		pickService.reRoll(user);
		return SuccessResponse.empty();
	}

}
