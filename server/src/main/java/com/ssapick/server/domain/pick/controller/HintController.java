package com.ssapick.server.domain.pick.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.service.HintService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hint")
public class HintController {
	private final HintService hintService;

	/**
	 * 랜덤한 힌트 리턴하는 API
	 *
	 * @param pickId 조회할 픽 아이디
	 * @return {@link String} 랜덤한 힌트 내용
	 */
	@GetMapping(value = "/{pickId}")
	public SuccessResponse<String> getRandomHintByPickId(@PathVariable("pickId") Long pickId) {
		return SuccessResponse.of(hintService.getRandomHintByPickId(pickId));
	}
}
