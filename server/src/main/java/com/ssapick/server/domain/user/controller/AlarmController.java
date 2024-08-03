package com.ssapick.server.domain.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.AlarmData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.AlramService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/alarm")
public class AlarmController {
	private final AlramService alarmService;

	/**
	 * 알람 조회 API
	 * @return {@link AlarmData.Response} 조회된 알람 리스트
	 */
	@Authenticated
	@GetMapping(value = "")
	public SuccessResponse<AlarmData.Response> getAlarm(@CurrentUser User user) {
		return SuccessResponse.of(alarmService.getAlarm(user.getId()));
	}

	/**
	 * 알람 정보 업데이트 API
	 * @param update 업데이트할 알람 정보
	 * {@link AlarmData.Update}
	 */
	@Authenticated
	@PostMapping(value = "")
	public SuccessResponse<Void> updateAlarm(@CurrentUser User user, @RequestBody AlarmData.Update update) {
		alarmService.updateAlarm(user.getId(), update);
		return SuccessResponse.empty();
	}
}
