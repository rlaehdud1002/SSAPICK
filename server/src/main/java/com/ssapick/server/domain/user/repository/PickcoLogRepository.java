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

    @Query("SELECT p FROM PickcoLog p LEFT JOIN FETCH p.user LEFT JOIN FETCH p.user.alarm LEFT JOIN FETCH p.user.profile LEFT JOIN FETCH p.user.profile.campus WHERE p.change < 0")
    List<PickcoLog> findAllSpendWithUser();

    @Query("SELECT p FROM PickcoLog p WHERE p.user.id = :id ORDER BY p.createdAt DESC")
    Page<PickcoLog> findAllByUserId(Long id, Pageable pageable);
}
