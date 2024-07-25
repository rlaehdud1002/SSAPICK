package com.ssapick.server.domain.attendance.service;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.attendance.repository.AttendanceRepository;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {
    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    private User user;
    private Profile profile;

    @BeforeEach
    void setup() {
        user = mock(User.class);
    }

    @Test
    @DisplayName("오늘 출석을 처음 할 때 출석을 생성하고 코인이벤트 발행")
    void firstAttendance() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository).save(any());
        verify(publisher).publishEvent(any(PickcoEvent.class));
    }

    @Test
    @DisplayName("오늘 이미 출석을 한 경우 예외처리")
    void alreadyMakeAttendanceToday() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        LocalDateTime today = LocalDateTime.now();
        Attendance attendance = mock(Attendance.class);
        when(attendanceRepository.findByUserAndAttendanceDate(user, today.toLocalDate()))
                .thenReturn(Optional.of(attendance));

        // * WHEN: 이걸 실행하면
        Runnable runnable = () -> attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository, never()).save(any());
        assertThrows(IllegalArgumentException.class, runnable::run);
    }

}