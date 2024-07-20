package com.ssapick.server.domain.pick.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.service.HintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hint")
public class HintController {

	private final HintService hintService;

	/**
	 * 랜덤한 힌트를 조회하는 API
	 * @param pickId 조회할 픽 아이디
	 * @return {@link Hint} 랜덤한 힌트
	 */
	@GetMapping(value = "/random")
	public SuccessResponse<Hint> getRandomHintByPickId(Long pickId) {

		return SuccessResponse.of(hintService.getRandomHintByPickId(pickId));
		
	}

}
