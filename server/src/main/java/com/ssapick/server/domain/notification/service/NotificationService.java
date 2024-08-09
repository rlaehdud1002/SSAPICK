package com.ssapick.server.domain.notification.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.entity.Notification;
import com.ssapick.server.domain.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public Page<NotificationData.Search> list(Long userId, Pageable pageable) {
		Page<Notification> notificationIds = notificationRepository.findAllByUserId(userId, pageable);
		List<Long> ids = notificationIds.getContent().stream().map(Notification::getId).toList();

		if (ids.isEmpty()) {
			return Page.empty();
		}

		List<Notification> notifications = notificationRepository.findAllById(ids);

		List<NotificationData.Search> searchList = notifications.stream()
			.map(NotificationData.Search::fromEntity)
			.toList();

		return new PageImpl<>(searchList, pageable, notificationIds.getTotalElements());
	}
}
