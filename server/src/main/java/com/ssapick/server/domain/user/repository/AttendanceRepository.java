package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.Attendance;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * 사용자의 출석 기록을 생성일자 내림차순으로 조회
     * @param user
     * @return
     */
    @Query("SELECT a FROM Attendance a WHERE a.user = :user ORDER BY a.createdAt DESC")
    List<Attendance> findAllByUserOrderByCreatedAtDesc(@Param("user") User user);

    /**
     * 오늘 날짜 출석이 있는지 확인
     * @param user
     * @param createdAt
     * @return
     */
    @Query("SELECT COUNT(a) > 0 FROM Attendance a WHERE a.user = :user AND CAST(a.createdAt as LocalDate) = :createdAt")
    boolean existsByUserAndCreatedAtDate(User user, LocalDate createdAt);
}
