package com.ssapick.server.domain.notification.repository;

import com.ssapick.server.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
