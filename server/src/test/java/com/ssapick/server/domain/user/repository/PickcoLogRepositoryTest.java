package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.PickcoLogType;
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("픽코 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/clearDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PickcoLogRepositoryTest extends TestDatabaseContainer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PickcoLogRepository pickcoLogRepository;

    @Autowired
    private EntityManager em;

    private PersistenceUnitUtil utils;

    @BeforeEach
    void setup() {
        utils = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("유저의 모든 픽코 소비 로그 조회 테스트")
    void findAllSpendWithUser() {
        // given
        User user = User.createUser("testUser", "테스트 유저", 'M', ProviderType.KAKAO, "123");
        userRepository.save(user);

        List<PickcoLog> pickcoLog = List.of(
                PickcoLog.createPickcoLog(user, PickcoLogType.SIGN_UP, 100, 100),
                PickcoLog.createPickcoLog(user, PickcoLogType.ATTENDANCE, 10, 110),
                PickcoLog.createPickcoLog(user, PickcoLogType.HINT_OPEN, -1, 109),
                PickcoLog.createPickcoLog(user, PickcoLogType.HINT_OPEN, -1, 108),
                PickcoLog.createPickcoLog(user, PickcoLogType.HINT_OPEN, -1, 107),
                PickcoLog.createPickcoLog(user, PickcoLogType.PICK, 10, 118)
        );

        pickcoLogRepository.saveAll(pickcoLog);

        // when
        List<PickcoLog> findSpendPickLogs = pickcoLogRepository.findAllSpendWithUser();


        // then
        assertThat(findSpendPickLogs.size()).isEqualTo(3);
        assertThat(findSpendPickLogs.get(0).getPickcoLogType()).isEqualTo(PickcoLogType.HINT_OPEN);
        assertThat(findSpendPickLogs.get(0).getChange()).isEqualTo(-1);
        assertThat(utils.isLoaded(findSpendPickLogs.get(0).getUser())).isTrue();
    }
}
