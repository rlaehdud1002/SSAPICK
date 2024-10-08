package com.ssapick.server.domain.notification.dto;

import com.ssapick.server.domain.notification.entity.Notification;
import com.ssapick.server.domain.notification.entity.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

public class NotificationData {

	@Data
	public static class Search {
		private NotificationType type;
		private String title;
		private String message;
		private boolean isRead;
		private LocalDateTime createdAt;

		public static Search fromEntity(Notification notification) {
			Search data = new Search();
			data.type = notification.getNotificationType();
			data.title = notification.getTitle();
			data.message = notification.getMessage();
			data.isRead = notification.isRead();
			data.createdAt = notification.getCreatedAt();
			return data;
		}
	}
}
