package com.ssapick.server.domain.question.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

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
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;

@DisplayName("질문 벤 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/baseInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class QuestionBanRepositoryTest extends TestDatabaseContainer {
	@Autowired
	private QuestionBanRepository questionBanRepository;
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
	@DisplayName("유저의가_특정_질문을_벤했는지_조회하는_테스트")
	void 유저의가_특정_질문을_벤했는지_조회하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

		// * WHEN: 이걸 실행하면
		Optional<QuestionBan> exist1 = questionBanRepository.findBanByUserIdAndQuestionId(
			user.getId(), 3L);

		Optional<QuestionBan> exist2 = questionBanRepository.findBanByUserIdAndQuestionId(
			user.getId(), 2L);

		Optional<QuestionBan> notExist = questionBanRepository.findBanByUserIdAndQuestionId(
			user.getId(), 1L);



		// * THEN: 이런 결과가 나와야 한다
		assertThat(utils.isLoaded(exist1.get(), "question")).isTrue();
		assertThat(utils.isLoaded(exist2.get(), "question")).isTrue();
		assertThat(exist1).isPresent();
		assertThat(exist2).isPresent();
		assertThat(notExist).isEmpty();
	}


	@Test
	@DisplayName("유저의_질문_벤_정보들을_조회하는_테스트")
	void 유저의_질문_벤_정보들을_조회하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

		// * WHEN: 이걸 실행하면
		List<Question> result = questionBanRepository.findQuestionBanByUserId(user.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(result.size()).isEqualTo(2);
		assertThat(result).extracting("id").containsExactly(2L, 3L);
	}
}