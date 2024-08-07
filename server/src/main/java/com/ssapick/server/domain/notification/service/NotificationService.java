package com.ssapick.server.domain.notification.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public List<NotificationData.Search> list(Long userId) {

		return notificationRepository.findAllByUserId(userId)
			.orElseThrow()
			.stream()
			.map(NotificationData.Search::fromEntity)
			.toList();

	}
}
