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

    @Transactional
    public void checkIn(User user) {
        LocalDate today = LocalDate.now();
        // 오늘 날짜 출석이 없으면 출석 생성
        if (attendanceRepository.existByUserAndCreatedAt(user, today)) {
            throw new IllegalArgumentException("이미 출석했습니다.");
        }
        attendanceRepository.save(Attendance.Create(user));
        attendanceRepository.flush();

//      출석 기록을 내림차순으로 전부 가져와서 최근 출석일이 오늘이 아니면 출석 생성
        List<Attendance> attendances = attendanceRepository.findAllByUserOrderByCreatedAtDesc(user);
        System.out.println("attendances.size() = " + attendances.size());;

        // 누적 출석일수로 코인이벤트 발행
        int consecutiveDays = calculateConsecutiveAttendanceDays(attendances, today, user);
        int rewordPickcoAmount;

        if (consecutiveDays == 6) {
            rewordPickcoAmount = 5;
        } else if (consecutiveDays == 13) {
            rewordPickcoAmount = 10;
        } else {
            rewordPickcoAmount = 1;
        }


        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.ATTENDANCE, rewordPickcoAmount, user.getProfile().getPickco()));
    }

    private int calculateConsecutiveAttendanceDays(List<Attendance> attendances, LocalDate today, User user) {
        // 어제 출석이 없으면 1일
        if (attendances.stream().noneMatch(attendance -> attendance.getCreatedAt().toLocalDate().isEqual(today.minusDays(1)))) {
            attendanceRepository.deleteAllByUser(user);
            return 1;
        }

        LocalDate currentDay = today;
        int consecutiveDays = 0;

        for (Attendance attendance : attendances) {
            if (attendance.getCreatedAt().toLocalDate().isEqual(currentDay)) {
                consecutiveDays += 1;
                currentDay = currentDay.minusDays(1);
            } else {
                break;
            }
        }


        if (consecutiveDays == 13) {
            attendanceRepository.deleteAllByUser(user);
        }

        return consecutiveDays;
    }
}
