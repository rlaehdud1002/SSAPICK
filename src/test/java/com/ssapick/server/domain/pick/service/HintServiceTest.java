package com.ssapick.server.domain.pick.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.auth.service.JWTService;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintOpenRepository;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.repository.QuestionCategoryRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class HintServiceTest extends RestDocsSupport {

	@Autowired
	private HintService hintService;

	@Autowired
	private HintRepository hintRepository;

	@Autowired
	private HintOpenRepository hintOpenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PickRepository pickRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private QuestionCategoryRepository questionCategoryRepository;

	@MockBean
	private JWTService jwtService;

	@MockBean
	private JwtProperties jwtProperties;

	@MockBean
	private ClientRegistrationRepository clientRegistrationRepository;

	@BeforeEach
	void setUp() {
		Mockito.when(jwtProperties.getSecret()).thenReturn("test-secret-key");
		Mockito.when(jwtProperties.getAccessExpire()).thenReturn(3600000);
		Mockito.when(jwtProperties.getRefreshExpire()).thenReturn(7200000);
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 0 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickIdTest0() {

		// given
		User user1 = userCreate(1L, "test1");
		User user2 = userCreate(2L, "test2");

		User savedUser1 = userRepository.save(user1);
		User savedUser2 = userRepository.save(user2);

		Hint hint1 = createHint(1L, "장덕동1", savedUser1);
		Hint hint2 = createHint(2L, "장덕동2", savedUser1);
		Hint hint3 = createHint(3L, "장덕동3", savedUser1);
		Hint hint4 = createHint(4L, "장덕동4", savedUser1);
		Hint hint5 = createHint(5L, "장덕동5", savedUser1);
		Hint hint6 = createHint(6L, "장덕동6", savedUser1);

		hintRepository.saveAll(List.of(hint1, hint2, hint3, hint4, hint5, hint6));

		Question testQuestion = getQuestion(user1);

		Pick pick = Pick.builder()
			.fromUser(savedUser1)
			.toUser(savedUser2)
			.question(testQuestion)
			.build();

		Pick savedPick = pickRepository.save(pick);

		// when
		Hint findHint = hintService.getRandomHintByPickId(savedPick.getId());

		// then
		Assertions.assertThat(findHint.getId()).isNotNull();

	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 1 개일때 힌트를 랜덤으로 가져오는 테스트")
	@Transactional
	void getRandomHintByPickIdTest1() {

		// given
		User user1 = userCreate(1L, "test1");
		User user2 = userCreate(2L, "test2");

		User savedUser1 = userRepository.save(user1);
		User savedUser2 = userRepository.save(user2);

		Hint hint1 = createHint(1L, "장덕동1", savedUser1);
		Hint hint2 = createHint(2L, "장덕동2", savedUser1);
		Hint hint3 = createHint(3L, "장덕동3", savedUser1);
		Hint hint4 = createHint(4L, "장덕동4", savedUser1);
		Hint hint5 = createHint(5L, "장덕동5", savedUser1);
		Hint hint6 = createHint(6L, "장덕동6", savedUser1);

		hintRepository.saveAll(List.of(
			hint1, hint2, hint3, hint4, hint5, hint6
		));

		Question testQuestion = getQuestion(user1);

		Pick pick = Pick.builder()
			.fromUser(savedUser1)
			.toUser(savedUser2)
			.question(testQuestion)
			.build();

		Pick savedPick = pickRepository.save(pick);

		HintOpen hintOpen = HintOpen.builder()
			.hint(hint1)
			.pick(savedPick)
			.build();

		savedPick.getHintOpens().add(hintOpen);

		pickRepository.save(savedPick);

		// when
		Hint findHint = hintService.getRandomHintByPickId(savedPick.getId());

		// then
		Assertions.assertThat(findHint.getId()).isNotEqualTo(hint1.getId());

	}

	private Question getQuestion(User user1) {
		QuestionCategory testCategory = QuestionCategory.builder()
			.id(1L)
			.name("장소")
			.build();

		questionCategoryRepository.save(testCategory);

		Question testQuestion = Question.builder()
			.id(1L)
			.questionCategory(testCategory)
			.content("장덕동")
			.author(user1)
			.banCount(0)
			.build();

		questionRepository.save(testQuestion);
		return testQuestion;
	}

	private User userCreate(Long id, String username) {
		return User.builder()
			.id(id)
			.username(username)
			.name("이름")
			.email("이메일")
			.providerType(ProviderType.GOOGLE)
			.roleType(RoleType.USER)
			.providerId("프로바이더 아이디")
			.isEmailVerified(true)
			.isLocked(false)
			.build();
	}

	private Hint createHint(Long id, String content, User user) {
		return Hint.builder()
			.id(id)
			.content(content)
			.user(user)
			.hintType(HintType.RESIDENTIAL_AREA)
			.visibility(false)
			.build();
	}

}
