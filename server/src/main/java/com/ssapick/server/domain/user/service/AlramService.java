package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.dto.AlarmData;
import com.ssapick.server.domain.user.entity.Alarm;
import com.ssapick.server.domain.user.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Alarm alarm = alarmRepository.findByUserId(userId).orElseThrow(
				() -> new BaseException(ErrorCode.NOT_FOUND_USER)
		);

		alarm.update(update);
	}

	/**
	 * 전체 알람 업데이트 API
	 * @param userId 업데이트할 유저 아이디
	 */
	@Transactional
	public void updateAllAlarm(Long userId, boolean onOff) {
		Alarm alarm = alarmRepository.findByUserId(userId).orElseThrow(
				() -> new BaseException(ErrorCode.NOT_FOUND_USER)
		);

		alarm.updateAll(onOff);
	}
}
