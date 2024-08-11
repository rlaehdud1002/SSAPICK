package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PickcoLogRepository extends JpaRepository<PickcoLog, Long> {
    List<PickcoLog> findByUser(User user);

    @Query("SELECT p FROM PickcoLog p JOIN FETCH p.user WHERE p.change < 0")
    List<PickcoLog> findAllSpendWithUser();

    @Query("SELECT p FROM PickcoLog p WHERE p.user.id = :id")
    Page<PickcoLog> findAllByUserId(Long id, Pageable pageable);
}
