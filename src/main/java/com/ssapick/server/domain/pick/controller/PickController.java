package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.service.PickService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pick")
public class PickController {

	private final PickService pickService;

	@GetMapping(value = "")
	@ResponseStatus(value = HttpStatus.OK)
	public SuccessResponse<List<PickData.Search>> searchPick(Long userId) {

		List<PickData.Search> userPicksInfo = pickService.searchToPickInfos(userId);

		return SuccessResponse.of(userPicksInfo);
	}

	@PostMapping("")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<Void> createPick(PickData.Create create) {

		pickService.createPick(create);

		return SuccessResponse.empty();
	}
}
