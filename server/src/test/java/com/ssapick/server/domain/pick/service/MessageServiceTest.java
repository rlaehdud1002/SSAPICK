package com.ssapick.server.domain.pick.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

	 Logger log = Logger.getLogger(MessageServiceTest.class.getName());

	@InjectMocks
	private  MessageService messageService;


	@Mock
	private MessageRepository messageRepository;
	@Mock
	private PickRepository pickRepository;

	@Mock
	private UserRepository userRepository;



	static User receiver;
	static User sender;
	static Pick pick;
	static Question question;

	@BeforeEach
	void setUp() {
		// 1L이 2L을 픽함
		receiver = userCreate(1L, "test-user1", '여');
		sender = userCreate(2L, "test-user2", '남');
		question = Question.builder().id(1L).content("질문").build();
		pick = pickCreate(receiver, sender, question);
		pickRepository.save(pick);

	}

	@Test
	@WithMockUser
	@DisplayName("받은 메시지를 조회하는 테스트")
	void searchReceiveMessage() {
		// given
		Message message = messageCreate(receiver, sender, pick, "메시지1");

		when(messageRepository.findReceivedMessageByUserId(1L)).thenReturn(List.of(message));
		// when
		List<MessageData.Search> result = messageService.searchReceiveMessage(1L);
		// then
		verify(messageRepository).findReceivedMessageByUserId(1L);

		assertThat(result.size()).isEqualTo(1);

		log.info("result : " + result);

		// 받은 메시지의 보낸이는 익명으로 나타내야한다.
		assertThat(result.get(0).getSenderName()).isEqualTo("익명");

		assertThat(result.get(0).getReceiverName()).isEqualTo("test-user1");
	}

	@Test
	@WithMockUser
	@DisplayName("보낸 메시지를 조회하는 테스트")
	void searchSendMessage() {
		// given
		Message message = messageCreate(sender, receiver, pick, "메시지1");

		when(messageRepository.findSentMessageByUserId(2L)).thenReturn(List.of(message));
		// when
		List<MessageData.Search> result = messageService.searchSendMessage(2L);
		// then
		verify(messageRepository).findSentMessageByUserId(2L);

		assertThat(result.size()).isEqualTo(1);

		log.info("result : " + result);

		// 보낸 메시지의 받는이는 익명으로 나타내야한다.
		assertThat(result.get(0).getReceiverName()).isEqualTo("test-user2");

		assertThat(result.get(0).getSenderName()).isEqualTo("test-user1");
	}

	@Test
	@WithMockUser
	@DisplayName("메시지를 생성하는 테스트")
	void createMessage() {
		// given
		MessageData.Create create = new MessageData.Create();
		create.setSender(sender);
		create.setReceiver(receiver);
		create.setPick(pick);
		create.setContent("메시지1");

		when(pickRepository.findById(anyLong())).thenReturn(Optional.of(pick));
		// when
		messageService.createMessage(create);
		// then
		verify(messageRepository).save(any(Message.class));
		verify(pickRepository).updateMessageSendTrue(1L);
	}


	private User userCreate(Long id, String username, char gender) {
		return User.builder()
			.id(id)
			.username(username)
			.name(username)
			.email("이메일")
			.gender(gender)
			.providerType(ProviderType.GOOGLE)
			.roleType(RoleType.USER)
			.providerId("프로바이더 아이디")
			.isMattermostConfirmed(true)
			.isLocked(false)
			.build();
	}


	private Pick pickCreate(User receiver, User sender, Question question) {
		return Pick.builder()
			.receiver(receiver)
			.sender(sender)
			.question(question)
			.build();
	}

	private Message messageCreate(User sender, User receiver, Pick pick, String content) {
		MessageData.Create create = new MessageData.Create();
		create.setSender(sender);
		create.setReceiver(receiver);
		create.setPick(pick);
		create.setContent(content);
		return Message.of(create);
	}


}