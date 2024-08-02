package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
class UserRepositoryTest extends TestDatabaseContainer {
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
    @DisplayName("유저 ID 데이터 조회 테스트")
    void 유저_ID_데이터_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = createUser("test-user");
        em.flush();
        em.clear();

        // * WHEN: 이걸 실행하면
        User findUser = userRepository.findById(user.getId()).orElseThrow();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
        Assertions.assertThat(utils.isLoaded(findUser, "profile")).isTrue();
        Assertions.assertThat(user.getProfile().getId()).isEqualTo(findUser.getProfile().getId());
    }

    @Test
    @DisplayName("유저 이름 데이터 조회 테스트")
    void 유저_이름_데이터_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = createUser("test-user");
        em.flush();
        em.clear();

        // * WHEN: 이걸 실행하면
        User findUser = userRepository.findByUsername(user.getUsername()).orElseThrow();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
        Assertions.assertThat(utils.isLoaded(findUser, "profile")).isTrue();
        Assertions.assertThat(user.getProfile().getId()).isEqualTo(findUser.getProfile().getId());
    }

    @Test
    @DisplayName("존재하지_않는_유저_테스트")
    void 존재하지_않는_유저_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = createUser("test-user");
        em.flush();
        em.clear();

        // * WHEN: 이걸 실행하면
        Optional<User> findUser = userRepository.findByUsername("not-user");

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(findUser).isEqualTo(Optional.empty());
    }



    private User createUser(String username) {
        User user = User.createUser(username, "테스트 유저", 'M', ProviderType.KAKAO, "123");
        return userRepository.save(user);
    }
}