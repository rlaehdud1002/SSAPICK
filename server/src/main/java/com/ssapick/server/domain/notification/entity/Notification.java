package com.ssapick.server.domain.notification.entity;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification extends TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_notification_user_id"))
	private User user;

	@Column(name = "notification_type")
	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	/** 울린 알림의 ID 값을 저장 */
	@Column(name = "reference_id")
	private Long referenceId;

	@Column(name = "is_read")
	private boolean isRead = false;

	private String title;

	private String message;

	@Column(name = "fcm_id")
	private String fcmId;

	public static Notification createNotification(User user, NotificationType notificationType, Long referenceId,
		String title, String message, String fcmId) {
		Notification notification = new Notification();
		notification.user = user;
		notification.notificationType = notificationType;
		notification.referenceId = referenceId;
		notification.title = title;
		notification.message = message;
		notification.fcmId = fcmId;
		return notification;
	}
}
