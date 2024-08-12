package com.ssapick.server.domain.notification.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.entity.Notification;
import com.ssapick.server.domain.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public Page<NotificationData.Search> list(Long userId, Pageable pageable) {
		List<Long> ids = notificationRepository.findAllByUserId(userId);

		if (ids.isEmpty()) {
			return Page.empty();
		}

		List<Notification> notifications = notificationRepository.findAllByIdLatest(ids, pageable);

		List<NotificationData.Search> searchList = notifications.stream()
			.map(NotificationData.Search::fromEntity)
			.toList();

		return new PageImpl<>(searchList, pageable, ids.size());
	}

	@Transactional
	public void readAll(Long userId) {
		notificationRepository.updateAllRead(userId);
	}
}
