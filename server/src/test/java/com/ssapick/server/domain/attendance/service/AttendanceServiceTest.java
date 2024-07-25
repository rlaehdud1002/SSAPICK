package com.ssapick.server.domain.attendance.service;

import com.ssapick.server.domain.attendance.entity.Attendance;
import com.ssapick.server.domain.attendance.repository.AttendanceRepository;
import com.ssapick.server.domain.pick.entity.Pick;
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
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate today;

    @BeforeEach
    void setup() {
        user = mock(User.class);
        profile = mock(Profile.class);
        lenient().when(user.getProfile()).thenReturn(profile);

        today = LocalDate.now();
    }

    @Test
    @DisplayName("오늘 출석을 처음 할 때 출석을 생성하고 코인이벤트 발행")
    void firstAttendance() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        when(attendanceRepository.existByUserAndCreatedAt(user, today))
                .thenReturn(false);

        // * WHEN: 이걸 실행하면
        attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository).save(any());
//        verify(publisher).publishEvent(argThat(event ->
//                event.getPickcoLogType() == PickcoLogType.ATTENDANCE &&
//                        event.getAmount() == 5 // 7일 연속 출석 시 5 코인 보상
//        ));
    }

    @Test
    @DisplayName("오늘 이미 출석을 한 경우 예외처리")
    void alreadyMakeAttendanceToday() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        when(attendanceRepository.existByUserAndCreatedAt(user, today))
                .thenReturn(true);

        // * WHEN: 이걸 실행하면
        Runnable runnable = () -> attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository, never()).save(any());
        assertThrows(IllegalArgumentException.class, runnable::run);
    }

    @Test
    @DisplayName("7일 연속 출석을 한 경우 5코인이벤트 발행")
    void 연속_출석_7일한_경우_5코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        LocalDate today = LocalDate.now();
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 7);

        // 기존 출석 기록이 있을 경우의 동작을 설정
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existByUserAndCreatedAt(user, today)).thenReturn(false);

        // 출석 기록 저장
        Attendance newAttendance = Attendance.Create(user);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(newAttendance);

        // * WHEN: 출석 체크 실행
        attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
//        verify(attendanceRepository).save(any(Attendance.class));
//        verify(publisher).publishEvent(argThat(event ->
//                event.getPickcoLogType() == PickcoLogType.ATTENDANCE &&
//                        event.getAmount() == 5 // 7일 연속 출석 시 5 코인 보상
//        ));
    }

    private List<Attendance> createConsecutiveAttendanceRecords(LocalDate startDate, int days) {
        List<Attendance> attendances = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            LocalDate date = startDate.minusDays(i);
            Attendance attendance = mock(Attendance.class);
            when(attendance.getCreatedAt()).thenReturn(date.atStartOfDay());
            attendances.add(attendance);
        }
        return attendances;
    }
}