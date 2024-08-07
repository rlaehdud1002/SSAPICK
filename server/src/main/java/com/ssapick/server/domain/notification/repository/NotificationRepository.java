package com.ssapick.server.domain.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.user.id = :userId")
	Optional<List<Notification>> findAllByUserId(Long userId);

}
