package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickcoLogRepository extends JpaRepository<PickcoLog, Long> {
    List<PickcoLog> findByUser(User user);
}
