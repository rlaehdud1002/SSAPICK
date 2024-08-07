package com.ssapick.server.domain.question.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

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
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import jakarta.persistence.EntityManager;

@DisplayName("질문 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
@Sql(scripts = "/sql/baseInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class QuestionRepositoryTest extends TestDatabaseContainer {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private QuestionQueryRepositoryImpl questionQueryRepository;
	@Autowired
	private EntityManager em;
	@Autowired
	private UserRepository userRepository;


	@Test
	@DisplayName("특정_카테고리의_질문들을_조회하는_테스트")
	void 특정_카테고리의_질문들을_조회하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		QuestionCategory category = em.getReference(QuestionCategory.class, 1L);

		// * WHEN: 이걸 실행하면
		List<Question> questions = questionRepository.findQuestionsByQuestionCategory(category);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(questions.size()).isEqualTo(2);
		assertThat(questions.stream().map(question -> question.getQuestionCategory().getId()).allMatch(qc -> qc.equals(category.getId()))).isTrue();
	}

	@Test
	@DisplayName("전체_질문_목록_삭제된_질문_제외_을_조회하는_테스트")
	void 전체_질문_목록_삭제된_질문_제외_을_조회하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때


		// * WHEN: 이걸 실행하면
		List<Question> questions = questionQueryRepository.findQuestions();

		// * THEN: 이런 결과가 나와야 한다
		assertThat(questions.size()).isEqualTo(3);
		assertThat(questions.stream().map(Question::getId).toList()).containsExactly(1L, 2L, 4L);
		assertThat(questions.stream().map(Question::isDeleted).allMatch(deleted -> !deleted)).isTrue();
	}

	@Test
	@DisplayName("사용자가_받은_픽의_질문_랭킹_테스트")
	void 사용자가_받은_픽의_질문_랭킹_테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
		User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

		// * WHEN: 이걸 실행하면
		List<Question> questions = questionQueryRepository.findQRankingByUserId(user.getId());

	    // * THEN: 이런 결과가 나와야 한다
		assertThat(questions.size()).isEqualTo(3);
		assertThat(questions.stream().map(Question::getId).toList()).containsExactlyElementsOf(List.of(1L, 2L, 4L));
	}

	@Test
	@DisplayName("사용자가_등록한_질문_조회_테스트")
	void 사용자가_등록한_질문_조회_테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
	    User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

	    // * WHEN: 이걸 실행하면
		List<Question> questions = questionQueryRepository.findAddedQsByUserId(
			user.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(questions.size()).isEqualTo(2);
		assertThat(questions.stream().map(Question::getId).toList()).containsExactly(1L, 4L);
	}
}