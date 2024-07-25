package com.ssapick.server.domain.attendance.repository;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    @Query("SELECT a FROM Attendance a WHERE a.user = :user AND DATE(a.createdAt) = :attendanceDate")
    Optional<Attendance> findByUserAndAttendanceDate(@Param("user") User user, @Param("attendanceDate") LocalDate attendanceDate);
}
