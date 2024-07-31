package com.ssapick.server.domain.attendance.repository;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a WHERE a.user = :user ORDER BY a.createdAt DESC")
    List<Attendance> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);

    void deleteAllByUser(User user);

    @Query("SELECT COUNT(a) > 0 FROM Attendance a WHERE a.user = :user AND CAST(a.createdAt as LocalDate) = :createdAt")
    boolean existsByUserAndCreatedAtDate(User user, LocalDate createdAt);
}
