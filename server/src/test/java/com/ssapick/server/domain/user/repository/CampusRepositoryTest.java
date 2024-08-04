package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.Campus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Fail.fail;


@DisplayName("캠퍼스 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/clearDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CampusRepositoryTest extends TestDatabaseContainer {
    @Autowired
    private CampusRepository campusRepository;

    @Test
    @DisplayName("동일한 캠퍼스명 반이 존재하는지 확인 테스트")
    void 동일한_캠퍼스명_반이_존재하는지_확인_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        campusRepository.save(Campus.createCampus("광주", (short) 2, null));
        campusRepository.save(Campus.createCampus("광주", (short) 1, null));

        // * WHEN: 이걸 실행하면
        Optional<Campus> exist = campusRepository.findByNameAndSection("광주", (short) 2);
        Optional<Campus> notExist = campusRepository.findByNameAndSection("광주", (short) 3);

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(exist).isNotEmpty();
        Assertions.assertThat(notExist).isEmpty();
    }

    @Test
    @DisplayName("동일한 캠퍼스가 있는경우 새로운 캠퍼스가 생성되지 않는지 테스트")
    void 동일한_캠퍼스가_있는경우_새로운_캠퍼스가_생성되지_않는지_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        campusRepository.save(Campus.createCampus("광주", (short) 2, null));

        // * WHEN: 이걸 실행하면
        Optional<Campus> campus = campusRepository.findByNameAndSection("광주", (short) 2);

        // * THEN: 이런 결과가 나와야 한다
        Campus newCampus = campus.orElseGet(
                () -> {
                    fail("신규 캠퍼스가 생성됨.");
                    return Campus.createCampus("광주", (short) 2, null);
            }
        );
    }
}