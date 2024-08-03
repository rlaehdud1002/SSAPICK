package com.ssapick.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.user.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
	Optional<Alarm> findByUserId(Long userId);
}
