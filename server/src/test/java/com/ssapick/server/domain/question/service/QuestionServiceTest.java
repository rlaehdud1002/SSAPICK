package com.ssapick.server.domain.question.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.service.CommentAnalyzerService;
import com.ssapick.server.core.service.SentenceSimilarityAnalyzerService;
import com.ssapick.server.core.service.SentenceSimilarityResponse;
import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.entity.QuestionRegistration;
import com.ssapick.server.domain.question.repository.QuestionBanRepository;
import com.ssapick.server.domain.question.repository.QuestionCacheRepository;
import com.ssapick.server.domain.question.repository.QuestionCategoryRepository;
import com.ssapick.server.domain.question.repository.QuestionRegistrationRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.ApplicationEventPublisher;

@DisplayName("질문 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest extends UserSupport {

	@InjectMocks
	private QuestionService questionService;
	@Mock
	private EntityManager em;
	@Mock
	private QuestionRepository questionRepository;
	@Mock
	private QuestionRegistrationRepository questionRegistrationRepository;
	@Mock
	private QuestionBanRepository questionBanRepository;
	@Mock
	private QuestionCategoryRepository questionCategoryRepository;
	@Mock
	private CommentAnalyzerService commentAnalyzerService;
	@Mock
	private SentenceSimilarityAnalyzerService sentenceSimilarityAnalyzerService;
	@Mock
	private QuestionCacheRepository questionCacheRepository;
	@Mock
	private ApplicationEventPublisher publisher;

	private final AtomicLong atomicLong = new AtomicLong(1);

	@Test
	@DisplayName("모든_질문_조회_테스트")
	void 모든_질문_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		User user = createUser("test");

		QuestionData.Search search1 = QuestionData.Search.fromEntity(this.createQuestion(user));
		QuestionData.Search search2 = QuestionData.Search.fromEntity(this.createQuestion(user));
		QuestionData.Search search3 = QuestionData.Search.fromEntity(this.createQuestion(user));

		when(questionCacheRepository.findAll()).thenReturn(List.of(
			search1, search2, search3
		));

		// * WHEN: 이걸 실행하면
		List<QuestionData.Search> searches = questionService.searchQuestions();

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);

	}

	@Test
	@DisplayName("카테고리별_질문_조회_테스트")
	void 카테고리별_질문_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");

		QuestionCategory category1 = this.createCategory();

		QuestionData.Search search1 = QuestionData.Search.fromEntity(this.createQuestion(user, category1));
		QuestionData.Search search2 = QuestionData.Search.fromEntity(this.createQuestion(user, category1));

		lenient().when(questionCategoryRepository.findById(category1.getId())).thenReturn(Optional.of(category1));

		lenient().when(questionCacheRepository.findQuestionsByCategory(category1.getId())).thenReturn(
			List.of(search1, search2)
		);

		// * WHEN: 이걸 실행하면
		List<QuestionData.Search> searches = questionService.searchQuestionsByCategory(category1.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(2);
	}

	@Test
	@DisplayName("질문_ID로_질문_조회_테스트")
	void 질문_ID로_질문_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		QuestionData.Search questionData = QuestionData.Search.fromEntity(this.createQuestion(user));

		when(questionCacheRepository.findById(questionData.getId())).thenReturn(
			Optional.of(questionData)
		);

		// * WHEN: 이걸 실행하면
		QuestionData.Search search = questionService.searchQuestionByQuestionId(questionData.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(search).isEqualTo(questionData);
	}

	// ! 캐시로 적용하며 삭제된건지 없는건지 확인이 불가능
	// @Test
	// @DisplayName("질문_ID_로_삭제된_질문_조회_테스트")
	// void 질문_ID_로_삭제된_질문_조회_테스트() throws Exception {
	//     // * GIVEN: 이런게 주어졌을 때
	// 	User user = createUser("test");
	// 	Question question1 = this.createQuestion(user);
	// 	question1.delete();
	//
	// 	when(questionRepository.findById(question1.getId())).thenReturn(
	// 		Optional.of(question1)
	// 	);
	//
	// 	// * WHEN: 이걸 실행하면;
	// 	Runnable runnable = () -> questionService.searchQuestionByQuestionId(question1.getId());
	//
	//     // * THEN: 이런 결과가 나와야 한다
	// 	assertThatThrownBy(runnable::run)
	// 		.isInstanceOf(BaseException.class)
	// 		.hasMessage(ErrorCode.NOT_FOUND_QUESTION.getMessage());
	// }

	@Test
	@DisplayName("질문_생성_요청_테스트")
	void 질문_생성_요청_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");

		when(questionCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
		when(commentAnalyzerService.isCommentOffensive(any())).thenReturn(false);
		when(sentenceSimilarityAnalyzerService.analyzeSentenceSimilarity(any())).thenReturn(
			new SentenceSimilarityResponse(0.1, "테스트"));
		when(questionRepository.save(any(Question.class))).thenReturn(
			Question.createQuestion(category, "테스트 질문", user));

		QuestionData.Create create = new QuestionData.Create();

		create.setCategoryId(category.getId());
		create.setContent("테스트 질문");

		// * WHEN: 이걸 실행하면
		questionService.createQuestion(user, create);

		// * THEN: 이런 결과가 나와야 한다
		verify(questionCacheRepository).add(any(Question.class));
	}


	@Test
	@DisplayName("없는_카테고리_입력_시_질문_생성_요청_테스트")
	void 없는_카테고리_입력_시_질문_생성_요청_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");

		lenient().when(questionCategoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

		QuestionData.Create create = new QuestionData.Create();

		create.setCategoryId(-1L);
		create.setContent("테스트 질문");

		// * WHEN: 이걸 실행하면

		Runnable runnable = () -> questionService.createQuestion(user, create);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(BaseException.class)
			.hasMessage(ErrorCode.NOT_FOUND_QUESTION_CATEGORY.getMessage());
	}

	@Test
	@DisplayName("질문_차단_테스트")
	void 질문_차단_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		Question question = this.createQuestion(user);

		when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

		// * WHEN: 이걸 실행하면
		questionService.banQuestion(user, question.getId());

		// * THEN: 이런 결과가 나와야 한다
		verify(questionBanRepository).save(any(QuestionBan.class));
	}

	@Test
	@DisplayName("없는_질문을_차단하려고_할_때_예외발생_테스트")
	void 없는_질문을_차단하려고_할_때_예외발생_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> questionService.banQuestion(user, -1L);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(BaseException.class)
			.hasMessage(ErrorCode.NOT_FOUND_QUESTION.getMessage());
	}

	@Test
	@DisplayName("이미_차단한_질문을_차단하려고_할_때_예외발생_테스트")
	void 이미_차단한_질문을_차단하려고_할_때_예외발생_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		Question question = this.createQuestion(user);
		QuestionBan questionBan = this.createQuestionBan(user, question);

		when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));
		when(questionBanRepository.findBanByUserIdAndQuestionId(user.getId(), question.getId())).thenReturn(
			Optional.of(questionBan));

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> questionService.banQuestion(user, question.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(BaseException.class)
			.hasMessage(ErrorCode.EXIST_QUESTION_BAN.getMessage());
	}

	@Test
	@DisplayName("내가_차단한_질문_조회_테스트")
	void 내가_차단한_질문_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");
		Question question = this.createQuestion(user);
		QuestionBan questionBan = this.createQuestionBan(user, question);

		when(questionBanRepository.findQuestionBanByUserId(user.getId())).thenReturn(List.of(question));

		// * WHEN: 이걸 실행하면
		List<QuestionData.Search> searches = questionService.searchBanQuestions(user.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(1);
		assertThat(searches.get(0)).isEqualTo(QuestionData.Search.fromEntity(question));
	}

	@Test
	@DisplayName("내가_지목당한_픽에대한_랭킹_조회_테스트")
	void 내가_지목당한_픽에대한_랭킹_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user1 = createUser("test");
		User user2 = createUser("test2");

		Question question1 = this.createQuestion(user2);
		Question question2 = this.createQuestion(user2);
		Question question3 = this.createQuestion(user2);

		Pick pick1 = this.createPick(user2, user1, question1);
		Pick pick2 = this.createPick(user2, user1, question2);
		Pick pick3 = this.createPick(user2, user1, question2);
		Pick pick4 = this.createPick(user2, user1, question3);
		Pick pick5 = this.createPick(user2, user1, question3);
		Pick pick6 = this.createPick(user2, user1, question3);

		when(questionRepository.findQRankingByUserId(user1.getId())).thenReturn(
			List.of(question3, question2, question1));

		// * WHEN: 이걸 실행하면
		List<QuestionData.Search> searches = questionService.searchQuestionsRank(user1.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches).containsExactly(
			QuestionData.Search.fromEntity(question3),
			QuestionData.Search.fromEntity(question2),
			QuestionData.Search.fromEntity(question1)
		);
	}

	@Test
	@DisplayName("사용자에게_뿌려줄_질문_리스트_테스트")
	void 사용자에게_뿌려줄_질문_리스트_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");

		Question question1 = this.createQuestion(user);
		Question question2 = this.createQuestion(user);
		Question question3 = this.createQuestion(user);
		Question question4 = this.createQuestion(user);
		Question question5 = this.createQuestion(user);

		QuestionData.Search search1 = QuestionData.Search.fromEntity(question1);
		QuestionData.Search search2 = QuestionData.Search.fromEntity(question2);
		QuestionData.Search search3 = QuestionData.Search.fromEntity(question3);
		QuestionData.Search search4 = QuestionData.Search.fromEntity(question4);
		QuestionData.Search search5 = QuestionData.Search.fromEntity(question5);

		when(questionCacheRepository.findAll()).thenReturn(
			List.of(search1, search2, search3, search4, search5)
		);

		when(questionBanRepository.findQuestionBanByUserId(user.getId())).thenReturn(
			List.of(question3, question4)
		);

		// * WHEN: 이걸 실행하면
		List<QuestionData.Search> searches = questionService.searchQeustionList(user);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches.size()).isLessThan(16);
	}

	private Question createQuestion(User user) {
		long id = atomicLong.incrementAndGet();
		QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");
		Question question = spy(Question.createQuestion(category, "테스트 질문" + id, user));
		when(question.getId()).thenReturn(id);
		return question;
	}

	private Question createQuestion(User user, QuestionCategory category) {
		long id = atomicLong.incrementAndGet();
		Question question = spy(Question.createQuestion(category, "테스트 질문" + id, user));
		lenient().when(question.getId()).thenReturn(id);
		return question;
	}

	private QuestionCategory createCategory() {
		long id = atomicLong.incrementAndGet();
		QuestionCategory category = spy(QuestionCategory.create("테스트 카테고리", ""));
		when(category.getId()).thenReturn(id);

		return category;
	}

	private QuestionBan createQuestionBan(User user, Question question) {
		QuestionBan questionBan = spy(QuestionBan.of(user, question));
		lenient().when(questionBan.getId()).thenReturn(atomicLong.incrementAndGet());
		return questionBan;
	}

	private Pick createPick(User sender, User receiver, Question question) {
		return Pick.of(sender, receiver, question);
	}
}