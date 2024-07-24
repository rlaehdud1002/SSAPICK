package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	 * @param user
	 * @return
	 */
	@Authenticated
	@GetMapping("/received")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> getReceivedPick(@CurrentUser User user) {
		return SuccessResponse.of(pickService.searchReceiver(user.getId()));
	}

	/**
	 * 보낸 픽 조회하는 API
	 * @param user
	 * @return
	 */
	@Authenticated
	@GetMapping("/sent")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> getSentPick(@CurrentUser User user) {
		return SuccessResponse.of(pickService.searchSender(user.getId()));
	}

	/**
	 * 픽 생성하는 API
	 * @param user
	 * @param create
	 * @return
	 */
	@Authenticated
	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<Void> createPick(@CurrentUser User user,
		@RequestBody PickData.Create create) {

		create.setUser(user);

		pickService.createPick(create);

		return SuccessResponse.empty();
	}
}
