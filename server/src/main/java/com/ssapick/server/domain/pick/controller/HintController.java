package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.UserData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.service.HintService;
import com.ssapick.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hint")
public class HintController {

	private static final Logger log = LoggerFactory.getLogger(HintController.class);
	private final HintService hintService;

	/**
	 * 힌트 조회 API
	 * @param userId 조회할 사용자 아이디
	 * @return {@link List<Hint>} 힌트 리스트
	 */
	@GetMapping(value = "/user")
	public SuccessResponse<List<Hint>> getHintsByUserId(Long userId) {
		return SuccessResponse.of(hintService.getHintsByUserId(userId));
	}

	/**
	 * 해당 pick의 오픈된 힌트를 조회하는 API
	 * @param pickId 조회할 픽 아이디
	 * @return {@link List<HintOpen>} 힌트 리스트
	 */
	@GetMapping(value = "/open")
	public SuccessResponse<List<HintOpen>> getOpenHintsByPickId(Long pickId) {
		return SuccessResponse.of(hintService.getHintOpensByPickId(pickId));
	}

	/**
	 * 랜덤한 힌트 리턴하는 API
	 * @param pickId 조회할 픽 아이디
	 * @return {@link String} 랜덤한 힌트 내용
	 */
	@GetMapping(value = "/random")
	public SuccessResponse<String> getRandomHintByPickId(Long pickId) {
		return SuccessResponse.of(hintService.getRandomHintByPickId(pickId));
	}

	/**
	 * 힌트 리스트 저장, 업테이트 API
	 * @param create 저장할 힌트 정보
	 */
	@PostMapping(value = "/save")
	@ResponseStatus(value = HttpStatus.CREATED)
	public SuccessResponse<Void> saveHint(
		@CurrentUser User user,
		@Validated @RequestBody UserData.Create create,
		Error error) {
		log.info("힌트 저장 API 요청: {}", create);
		hintService.saveHint(user, create);
		return SuccessResponse.created();
	}

}
