package com.ssapick.server.domain.notification.dto;

import com.ssapick.server.domain.notification.entity.Notification;
import com.ssapick.server.domain.notification.entity.NotificationType;

import lombok.Data;

public class NotificationData {

	@Data
	public static class Search {
		private NotificationType type;
		private String message;
		private boolean isRead;

		public static Search fromEntity(Notification notification) {
			Search data = new Search();
			data.type = notification.getNotificationType();
			data.message = notification.getMessage();
			data.isRead = notification.isRead();
			return data;
		}
	}
}
