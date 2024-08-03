package com.ssapick.server.domain.user.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.Follow;
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
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("팔로우 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
class FollowRepositoryTest extends TestDatabaseContainer {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    private PersistenceUnitUtil utils;

    private List<Follow> follows;
    private List<User> users;

    @BeforeEach
    void setup() {
        utils = em.getEntityManagerFactory().getPersistenceUnitUtil();

        users = Stream.of("user1", "user2", "user3")
                .map(this::createUser)
                .toList();

        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        follows = List.of(
                Follow.follow(user1, user2),
                Follow.follow(user1, user3),
                Follow.follow(user2, user3)
        );

        followRepository.saveAll(follows);
    }

    @Test
    @DisplayName("팔로우 조회 테스트")
    void 팔로우_조회_테스트() {
        User user1 = users.get(0);

        followRepository.findByFollowUser(user1);

        List<Follow> followList = followRepository.findByFollowUser(user1);

        assertThat(followList).hasSize(2);
        followList.stream().forEach(follow -> {
            assertThat(follow.getFollowUser()).isEqualTo(user1);
            assertThat(utils.isLoaded(follow.getFollowingUser())).isTrue();
            assertThat(utils.isLoaded(follow.getFollowingUser().getProfile())).isTrue();
        });

    }

    @Test
    @DisplayName("팔로잉 조회 테스트")
    void 팔로잉_조회_테스트() {
        User user3 = users.get(2);

        followRepository.findByFollowUser(user3);

        List<Follow> followList = followRepository.findByFollowingUser(user3);

        assertThat(followList).hasSize(2);
        followList.stream().forEach(follow -> {
            assertThat(follow.getFollowingUser()).isEqualTo(user3);
            assertThat(utils.isLoaded(follow.getFollowingUser())).isTrue();
            assertThat(utils.isLoaded(follow.getFollowingUser().getProfile())).isTrue();
        });

    }

    private User createUser(String username) {
        User user = User.createUser(username, "테스트 유저", 'M', ProviderType.KAKAO, "123");

        return userRepository.save(user);
    }

}
