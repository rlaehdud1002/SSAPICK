// package com.ssapick.server.domain.question.repository;
//
// import static org.junit.jupiter.api.Assertions.*;
//
// import java.util.List;
// import java.util.Optional;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.context.annotation.Import;
// import org.springframework.test.context.jdbc.Sql;
//
// import com.ssapick.server.core.config.JpaTestConfig;
// import com.ssapick.server.core.container.TestDatabaseContainer;
// import com.ssapick.server.domain.question.entity.Question;
// import com.ssapick.server.domain.question.entity.QuestionBan;
// import com.ssapick.server.domain.user.entity.User;
// import com.ssapick.server.domain.user.repository.UserRepository;
//
// @DisplayName("질문 벤 레포지토리 테스트")
// @DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// @Import({JpaTestConfig.class})
// @Sql(scripts = "/sql/baseInsert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
// class QuestionBanRepositoryTest extends TestDatabaseContainer {
// 	@Autowired
// 	private QuestionBanRepository questionBanRepository;
// 	@Autowired
// 	private UserRepository userRepository;
//
// 	@Test
// 	@DisplayName("유저의가_특정_질문을_벤했는지_조회하는_테스트")
// 	void 유저의가_특정_질문을_벤했는지_조회하는_테스트() throws Exception {
// 		// * GIVEN: 이런게 주어졌을 때
// 		User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
//
// 		// * WHEN: 이걸 실행하면
// 		Optional<QuestionBan> exist1 = questionBanRepository.findBanByUserIdAndQuestionId(
// 			user.getId(), 3L);
//
// 		Optional<QuestionBan> exist2 = questionBanRepository.findBanByUserIdAndQuestionId(
// 			user.getId(), 2L);
//
// 		Optional<QuestionBan> notExist = questionBanRepository.findBanByUserIdAndQuestionId(
// 			user.getId(), 1L);
//
// 		// * THEN: 이런 결과가 나와야 한다
// 		assertTrue(exist1.isPresent());
// 		assertTrue(exist2.isPresent());
// 		assertFalse(notExist.isPresent());
// 	}
//
//
// 	@Test
// 	@DisplayName("유저의_질문_벤_정보들을_조회하는_테스트")
// 	void 유저의_질문_벤_정보들을_조회하는_테스트() throws Exception {
// 		// * GIVEN: 이런게 주어졌을 때
// 		User user = userRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
//
// 		// * WHEN: 이걸 실행하면
// 		List<Question> result = questionBanRepository.findQBanByUserId(user.getId());
//
// 		// * THEN: 이런 결과가 나와야 한다
// 		assertEquals(result.size(), 2);
//
// 	}
// }