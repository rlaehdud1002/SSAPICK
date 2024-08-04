package com.ssapick.server.domain.attendance.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.attendance.dto.AttendanceData;
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

import static com.ssapick.server.core.constants.PickConst.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApplicationEventPublisher publisher;

    private final int ATTENDANCE_RESET_CYCLE = 14;

    private static int getStreak(LocalDate today, List<Attendance> attendances) {
        LocalDate date = today;
        int streak = 0;
        for (Attendance attendance : attendances) {
            if (attendance.getCreatedAt().toLocalDate().isEqual(date)) {
                streak += 1;
                date = date.minusDays(1);
            }
        }
        return streak;
    }

    public AttendanceData.Status getUserAttendanceStatus(User user) {
        LocalDate today = LocalDate.now();

        boolean todayChecked = attendanceRepository.existsByUserAndCreatedAtDate(user, today);

        List<Attendance> attendances = attendanceRepository.findAllByUserOrderByCreatedAtDesc(user);
        int streak = getStreak(today, attendances);
        return AttendanceData.CreateStatus(streak, todayChecked);
    }

    @Transactional
    public int checkIn(User user) {
        LocalDate today = LocalDate.now();

        if (attendanceRepository.existsByUserAndCreatedAtDate(user, today)) {
            throw new BaseException(ErrorCode.ALREADY_CHECKIN_TODAY);
        }
        attendanceRepository.save(Attendance.Create(user));
        attendanceRepository.flush();

        List<Attendance> attendances = attendanceRepository.findAllByUserOrderByCreatedAtDesc(user);
        int rewardPickcoAmount = getRewardPickcoAmount(today, attendances);
        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.ATTENDANCE, rewardPickcoAmount));

        return rewardPickcoAmount;
    }

    private int getRewardPickcoAmount(LocalDate today, List<Attendance> attendances) {
        int streak = getStreak(today, attendances);

        int streakInCycle = streak % ATTENDANCE_RESET_CYCLE;
        int rewardPickcoAmount;

        if (streakInCycle == 7) {
            rewardPickcoAmount = WEEK_ATTENDANCE_COIN;
        } else if (streakInCycle == 0) {
            rewardPickcoAmount = TWO_WEEK_ATTENDANCE_COIN;
        } else {
            rewardPickcoAmount = DAILY_ATTENDANCE_COIN;
        }
        return rewardPickcoAmount;
    }

}
