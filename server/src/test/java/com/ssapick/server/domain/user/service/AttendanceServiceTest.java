package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.entity.Attendance;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.AttendanceRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("출석 서비스 테스트")
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
        Attendance attendance = mock(Attendance.class);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today))
                .thenReturn(false);
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user))
                .thenReturn(List.of(attendance));
        when(attendance.getCreatedAt())
                .thenReturn(today.atStartOfDay());

        // * WHEN: 이걸 실행하면
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository).save(any());
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(50);
    }

    @Test
    @DisplayName("오늘 이미 출석을 한 경우 예외처리")
    void alreadyMakeAttendanceToday() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today))
                .thenReturn(true);

        // * WHEN: 이걸 실행하면
        Runnable runnable = () -> attendanceService.checkIn(user);

        // * THEN: 이런 결과가 나와야 한다
        verify(attendanceRepository, never()).save(any());
        assertThatThrownBy(runnable::run)
                .isInstanceOf(BaseException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.ALREADY_CHECKIN_TODAY);
    }

    @Test
    @DisplayName("7일 연속 출석을 한 경우 200코인이벤트 발행")
    void 연속_출석_7일한_경우_200코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 7);

        // 기존 출석 기록이 있을 경우의 동작을 설정
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today)).thenReturn(false);

        // * WHEN: 출석 체크 실행
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(200);
    }

    @Test
    @DisplayName("8일 연속 출석을 한 경우 50코인이벤트 발행")
    void 연속_출석_8일한_경우_50코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 8);

        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today)).thenReturn(false);

        // * WHEN: 출석 체크 실행
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(50);
    }

    @Test
    @DisplayName("14일 연속 출석을 한 경우 500코인이벤트 발행")
    void 연속_출석_14일한_경우_500코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 14);

        // 기존 출석 기록이 있을 경우의 동작을 설정
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today)).thenReturn(false);

        // * WHEN: 출석 체크 실행
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(500);
    }

    @Test
    @DisplayName("15일 연속 출석을 한 경우 50코인이벤트 발행")
    void 연속_출석_15일한_경우_50코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 15);

        // 기존 출석 기록이 있을 경우의 동작을 설정
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today)).thenReturn(false);

        // * WHEN: 출석 체크 실행
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(50);
    }

    @Test
    @DisplayName("21일 연속 출석을 한 경우 200코인이벤트 발행")
    void 연속_출석_21일한_경우_200코인이벤트_발행() throws Exception {
        // * GIVEN: 7일 연속 출석 기록을 설정
        List<Attendance> attendances = createConsecutiveAttendanceRecords(today, 21);

        // 기존 출석 기록이 있을 경우의 동작을 설정
        when(attendanceRepository.findAllByUserOrderByCreatedAtDesc(user)).thenReturn(attendances);
        when(attendanceRepository.existsByUserAndCreatedAtDate(user, today)).thenReturn(false);

        // * WHEN: 출석 체크 실행
        int rewardPickco = attendanceService.checkIn(user);

        // * THEN: 출석 기록 저장 및 이벤트 발행 검증
        verify(publisher).publishEvent(any(PickcoEvent.class));
        assertThat(rewardPickco).isEqualTo(200);
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
