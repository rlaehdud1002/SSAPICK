package com.ssapick.server.domain.user.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.entity.UserBan;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;

@DisplayName("유저 차단 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/baseInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserBanRepositoryTest  extends TestDatabaseContainer {

	@Autowired
	private UserBanRepository userBanRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager em;
	private PersistenceUnitUtil utils;

	@BeforeEach
	void init() {utils = em.getEntityManagerFactory().getPersistenceUnitUtil();}

	@Test
	@DisplayName("유저 차단 데이터 조회 테스트")
	void 유저_차단_데이터_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		// * WHEN: 이걸 실행하면
		User user = userRepository.findById(1L).orElseThrow();
		List<User> banUsers = userBanRepository.findBanUsersByFromUser(user);

		// * THEN: 이런 결과가 나와야 한다
		Assertions.assertThat(banUsers).hasSize(1);
		Assertions.assertThat(utils.isLoaded(banUsers.get(0), "profile")).isTrue();
	}
	
	@Test
	@DisplayName("유저가 특정 유저를 차단했는지 조회하는 테스트")
	void 유저가_특정_유저를_차단했는지_조회하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		// * WHEN: 이걸 실행하면
		User user = userRepository.findById(1L).orElseThrow();
		User findBanUser = userRepository.findById(2L).orElseThrow();
		User findNotBanUser = userRepository.findById(3L).orElseThrow();

		Optional<UserBan> existUserBan = userBanRepository.findBanByFromUserAndToUser(user, findBanUser);
		Optional<UserBan> notExistUserBan = userBanRepository.findBanByFromUserAndToUser(user, findNotBanUser);

		// * THEN: 이런 결과가 나와야 한다
		Assertions.assertThat(existUserBan).isPresent();
		Assertions.assertThat(notExistUserBan).isEmpty();
	}
}