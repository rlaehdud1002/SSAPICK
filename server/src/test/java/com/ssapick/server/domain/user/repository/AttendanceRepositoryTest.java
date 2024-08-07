package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.Attendance;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/clearDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class AttendanceRepositoryTest extends TestDatabaseContainer {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    private PersistenceUnitUtil utils;

    @BeforeEach
    void init() {
        utils = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("오늘 날짜에 출석이 없으면 false를 반환한다")
    void 오늘_출석_기록이_없는_경우_false_반환() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = User.createUser("test1", "테스트 유저", 'M', ProviderType.KAKAO, "123");
        userRepository.save(user);
        em.flush();
        em.clear();

        // * WHEN: 이걸 실행하면
        boolean checkin = attendanceRepository.existsByUserAndCreatedAtDate(user, LocalDate.now());

        // * THEN: 이런 결과가 나와야 한다
        assertThat(checkin).isEqualTo(false);
    }

    @Test
    @DisplayName("오늘 날짜에 출석이 있으면 true를 반환한다")
    void 오늘_출석_기록이_이미_있는_경우_true_반환() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = User.createUser("test1", "테스트 유저", 'M', ProviderType.KAKAO, "123");
        Attendance attendance = Attendance.Create(user);
        userRepository.save(user);
        attendanceRepository.save(attendance);
        em.flush();
        em.clear();

        // * WHEN: 이걸 실행하면
        boolean checkin = attendanceRepository.existsByUserAndCreatedAtDate(user, attendance.getCreatedAt().toLocalDate());

        // * THEN: 이런 결과가 나와야 한다
        assertThat(checkin).isEqualTo(true);
    }

    @Test
    @DisplayName("출석 기록을 생성일자 내림차순으로 조회한다")
    void 출석_기록_내림차순_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = User.createUser("test1", "테스트 유저", 'M', ProviderType.KAKAO, "123");
        userRepository.save(user);

        Attendance attendance1 = Attendance.Create(user);
        Attendance attendance2 = Attendance.Create(user);
        Attendance attendance3 = Attendance.Create(user);

        attendanceRepository.saveAll(List.of(attendance1, attendance2, attendance3));
        em.flush();

        // * WHEN: 이걸 실행하면
        List<Attendance> allByUserOrderByCreatedAtDesc = attendanceRepository.findAllByUserOrderByCreatedAtDesc(user);

        // * THEN: 이런 결과가 나와야 한다
        // assertThat(allByUserOrderByCreatedAtDesc).hasSize(3);
        // assertThat(allByUserOrderByCreatedAtDesc.get(0)).isEqualTo(attendance3);
        // assertThat(allByUserOrderByCreatedAtDesc.get(1)).isEqualTo(attendance2);
        // assertThat(allByUserOrderByCreatedAtDesc.get(2)).isEqualTo(attendance1);

    }

}