package com.ssapick.server.domain.attendance.repository;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{

    @Query("SELECT a FROM Attendance a WHERE a.user = :user ORDER BY a.createdAt DESC")
    List<Attendance> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);

    void deleteAllByUser(User user);

    boolean existsByUserAndCreatedAt(User user, LocalDate today);
}
