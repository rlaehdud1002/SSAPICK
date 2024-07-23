package com.ssapick.server.domain.pick.service;

import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PickServiceTest  {

	private static final Logger log = LoggerFactory.getLogger(PickServiceTest.class);

	@InjectMocks
	private PickService pickService;

	@Mock
	private PickRepository pickRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private QuestionRepository questionRepository;

	static User receiver;
	static User sender;
	static Pick pick;
	static Question question;

	@BeforeEach
	void setUp() {
		 receiver = userCreate(1L, "test-user1", '여');
		 sender = userCreate(2L, "test-user2", '남');
		 pick = pickCreate(receiver, sender);
		 question = Question.builder().id(1L).content("질문").build();

	}

	@Test
	@WithMockUser
	@DisplayName("유저가 받은 픽을 조회하는 테스트")
	void searchReceivedPick() {
		// given
		when(pickRepository.findReceiverByUserId(1L)).thenReturn(List.of(pick));

		// when
		List<PickData.Search> picks = pickService.searchReceiver(1L);

		// then
		verify(pickRepository, times(1)).findReceiverByUserId(1L);
		Assertions.assertThat(picks.get(0).getReceiver().getId()).isEqualTo(1L);
	}


	@Test
	@WithMockUser
	@DisplayName("유저가 보낸 픽을 조회하는 테스트")
	void searchSentPick() {
		// given
		when(pickRepository.findSenderByUserId(2L)).thenReturn(List.of(pick));

		// when
		List<PickData.Search> picks = pickService.searchSender(2L);

		// then
		verify(pickRepository, times(1)).findSenderByUserId(2L);
		Assertions.assertThat(picks.get(0).getSender().getId()).isEqualTo(2L);
		Assertions.assertThat(picks.get(0).getReceiver().getId()).isEqualTo(1L);
		Assertions.assertThat(picks.get(0).getSender().getGender()).isEqualTo('남');

	}



	@Test
	@WithMockUser
	@DisplayName("유저1이 유저2를 픽하는 테스트")
	void setPick() {
		PickData.Create createData = new PickData.Create();
		createData.setReceiver(receiver);
		createData.setSender(sender);
		createData.setQuestion(question);

		// when
		pickService.createPick(createData);

		// then
		verify(pickRepository, times(1)).save(argThat(pick ->
			pick.getReceiver().equals(receiver) &&
				pick.getSender().equals(sender) &&
				(pick.getQuestion() == null ? question == null : pick.getQuestion().equals(question))
		));
	}
	private Pick pickCreate(User receiver, User sender) {
		return Pick.builder().receiver(receiver).sender(sender).build();
	}

	private User userCreate(Long id, String username, char gender) {
		return User.builder()
			.id(id)
			.username(username)
			.name("이름")
			.email("이메일")
			.gender(gender)
			.providerType(ProviderType.GOOGLE)
			.roleType(RoleType.USER)
			.providerId("프로바이더 아이디")
			.isEmailVerified(true)
			.isLocked(false)
			.build();
	}
}