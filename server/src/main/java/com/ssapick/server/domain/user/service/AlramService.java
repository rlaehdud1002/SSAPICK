package com.ssapick.server.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.user.dto.AlarmData;
import com.ssapick.server.domain.user.repository.AlarmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlramService {
	private final AlarmRepository alarmRepository;

	/**
	 * 알람 조회 API
	 * @return {@link AlarmData.Response} 조회된 알람 리스트
	 */
	public AlarmData.Response getAlarm(Long userId) {
		return AlarmData.Response.fromEntity(alarmRepository.findByUserId(userId).orElseThrow());
	}

	/**
	 * 알람 정보 업데이트 API
	 * @param update 업데이트할 알람 정보
	 * {@link AlarmData.Update}
	 */
	@Transactional
	public void updateAlarm(Long userId, AlarmData.Update update) {
		alarmRepository.findByUserId(userId).ifPresent(alarm -> alarm.update(update));
	}
}
