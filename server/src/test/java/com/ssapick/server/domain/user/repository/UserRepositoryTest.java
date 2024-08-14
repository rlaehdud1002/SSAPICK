package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@DisplayName("유저 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/baseInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserRepositoryTest extends TestDatabaseContainer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryRepositoryImpl userQueryRepository;
    @Autowired
    private EntityManager em;
    private PersistenceUnitUtil utils;
	@Autowired
	private FollowRepository followRepository;

    @BeforeEach
    void init() {
        utils = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("유저 ID 데이터 조회 테스트")
    void 유저_ID_데이터_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때


        // * WHEN: 이걸 실행하면
        User user = userRepository.findById(1L).orElseThrow();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(user.getUsername()).isEqualTo("user1");
        Assertions.assertThat(user.getName()).isEqualTo("User One");
        Assertions.assertThat(utils.isLoaded(user, "profile")).isTrue();
        Assertions.assertThat(user.getProfile().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("유저 이름 데이터 조회 테스트")
    void 유저_이름_데이터_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        User user = userRepository.findByUsername("user1").orElseThrow();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(user.getUsername()).isEqualTo("user1");
        Assertions.assertThat(user.getName()).isEqualTo("User One");
        Assertions.assertThat(utils.isLoaded(user, "profile")).isTrue();
        Assertions.assertThat(user.getProfile().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지_않는_유저_테스트")
    void 존재하지_않는_유저_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        Optional<User> findUser = userRepository.findByUsername("not-user");

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(findUser).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("프로필_아이디로_유저_조회")
    void 프로필_아이디로_유저_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        User user = userRepository.findUserWithProfileById(1L).orElseThrow();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(user.getUsername()).isEqualTo("user1");
        Assertions.assertThat(user.getName()).isEqualTo("User One");
        Assertions.assertThat(utils.isLoaded(user.getProfile(), "campus")).isTrue();
        Assertions.assertThat(utils.isLoaded(user.getProfile(), "hints")).isTrue();
    }

    @Test
    @DisplayName("메터모스트_인증_여부_체크_테스트")
    void 메터모스트_인증_여부_체크_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        boolean isMattermostConfirmed = userRepository.isUserAuthenticated(1L);
        boolean isNotMattermostConfirmed = userRepository.isUserAuthenticated(4L);

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(isMattermostConfirmed).isTrue();
        Assertions.assertThat(isNotMattermostConfirmed).isFalse();
    }

    @Test
    @DisplayName("유저가_팔로우한_유저들과_같은_반_유저들을_조회하는_테스트")
    void 유저가_팔로우한_유저들_조회하는_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        // 유저는 2 번, 4번 유저를 팔로우 중이고 유저 3과 같은 반이다.

        // * WHEN: 이걸 실행하면
        List<ProfileData.Friend> findUsers = userQueryRepository.findFollowUserByUserId(1L);

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(findUsers.size()).isEqualTo(2);
        Assertions.assertThat(findUsers.stream().map(ProfileData.Friend::getUserId)).containsExactlyInAnyOrder(3L, 4L);

    }

    @Test
    @DisplayName("키워드로 유저를 검색하면 키워드가 포함된 유저 조회")
    void 키워드로_유저_조회() {
        // * GIVEN: 이런게 주어졌을 때

        Page<ProfileData.Friend> user = userRepository.searchUserByKeyword(1L, "User", PageRequest.of(0, 10));

        List<ProfileData.Friend> content = user.getContent();


        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(content.size()).isEqualTo(2);
    }
    @Test
    @DisplayName("빈_키워드로 유저를 검색하면 전체 조회")
    void 빈_키워드로_유저_조회() {
        // * GIVEN: 이런게 주어졌을 때

        Page<ProfileData.Friend> user = userRepository.searchUserByKeyword(1L, "", PageRequest.of(0, 10));

        List<ProfileData.Friend> content = user.getContent();


        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(content.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("픽코가 가장 많은 유저 TOP3 조회")
    void 픽코가_가장_많은_유저_TOP3_조회() {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        List<User> findUsers = userRepository.findTopPickcoUsers();

        // * THEN: 이런 결과가 나와야 한다
        Assertions.assertThat(findUsers.size()).isEqualTo(3);
        Assertions.assertThat(findUsers.get(0).getProfile().getPickco()).isEqualTo(250);
        Assertions.assertThat(findUsers.get(1).getProfile().getPickco()).isEqualTo(200);
        Assertions.assertThat(findUsers.get(2).getProfile().getPickco()).isEqualTo(150);
    }
}