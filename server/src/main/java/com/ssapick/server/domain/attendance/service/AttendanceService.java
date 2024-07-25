package com.ssapick.server.domain.attendance.service;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.attendance.repository.AttendanceRepository;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApplicationEventPublisher publisher;

    private final int ATTENDANCE_RESET_CYCLE = 14;

    @Transactional
    public int checkIn(User user) {
        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByUserAndCreatedAt(user, today)) {
            throw new IllegalArgumentException("이미 출석했습니다.");
        }
        attendanceRepository.save(Attendance.Create(user));
        attendanceRepository.flush();

        List<Attendance> attendances = attendanceRepository.findAllByUserOrderByCreatedAtDesc(user);
        int rewardPickcoAmount = getRewardPickcoAmount(today, attendances);
        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.ATTENDANCE, rewardPickcoAmount, user.getProfile().getPickco()));

        return rewardPickcoAmount;
    }

    private int getRewardPickcoAmount(LocalDate today, List<Attendance> attendances) {
        LocalDate date = today;
        int streak = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getCreatedAt().toLocalDate().isEqual(date)) {
                streak += 1;
                date = date.minusDays(1);
            }
        }

        int streakInCycle = streak % ATTENDANCE_RESET_CYCLE;
        int rewardPickcoAmount = 0;

        if (streakInCycle == 7) {
            rewardPickcoAmount = 5;
        } else if (streakInCycle == 0) {
            rewardPickcoAmount = 10;
        } else {
            rewardPickcoAmount = 1;
        }
        return rewardPickcoAmount;
    }
}
