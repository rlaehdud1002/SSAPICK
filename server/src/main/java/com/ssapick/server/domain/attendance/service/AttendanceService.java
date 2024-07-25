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
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void checkIn(User user) {
        LocalDate today = LocalDate.now();

        Optional<Attendance> lastAttendance = attendanceRepository.findByUserAndAttendanceDate(user, today);

        if (lastAttendance.isPresent()) {
            throw new IllegalArgumentException("이미 출석했습니다.");
        }

        attendanceRepository.save(Attendance.Create(user));
        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.ATTENDANCE, 3, user.getProfile().getPickco()));
    }
}
