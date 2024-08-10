package com.ssapick.server.domain.notification.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.notification.entity.Notification;

import io.lettuce.core.dynamic.annotation.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("SELECT n FROM Notification n WHERE n.user.id = :userId")
	Page<Notification> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

	@Query("SELECT n FROM Notification n WHERE n.id IN :ids ORDER BY n.id DESC")
	List<Notification> findAllByIdLatest(List<Long> ids);
}
