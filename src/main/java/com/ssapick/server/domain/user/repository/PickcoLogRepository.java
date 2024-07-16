package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.PickcoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickcoLogRepository extends JpaRepository<PickcoLog, Long> {
}
