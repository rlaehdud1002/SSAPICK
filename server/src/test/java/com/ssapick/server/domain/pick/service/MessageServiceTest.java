package com.ssapick.server.domain.pick.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.EntityManager;

@DisplayName("메시지 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MessageServiceTest extends UserSupport {
	@InjectMocks
	private MessageService messageService;

	@Mock
	private MessageRepository messageRepository;

	@Mock
	private PickRepository pickRepository;

	@Mock
	private EntityManager em;

	@Test
	@DisplayName("보낸 메시지 확인 테스트")
	void 보낸_메시지_확인_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");

		when(messageRepository.findSentMessageByUserId(sender.getId())).thenReturn(List.of(
			this.createMessage(sender, receiver, "테스트 메시지 1"),
			this.createMessage(sender, receiver, "테스트 메시지 2"),
			this.createMessage(sender, receiver, "테스트 메시지 3")
		));

		// * WHEN: 이걸 실행하면
		List<MessageData.Search> searches = messageService.searchSendMessage(sender);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches.stream().map(MessageData.Search::getContent)).contains("테스트 메시지 1", "테스트 메시지 2",
			"테스트 메시지 3");
		assertThat(searches.stream().map(MessageData.Search::getReceiverName)).contains("receiver", "receiver",
			"receiver");
		assertThat(searches.stream().map(MessageData.Search::getSenderName)).contains("sender", "sender", "sender");
	}

	@Test
	@DisplayName("받은 메시지 확인 테스트")
	void 받은_메시지_확인_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");

		when(messageRepository.findReceivedMessageByUserId(receiver.getId())).thenReturn(List.of(
			this.createMessage(sender, receiver, "테스트 메시지 1"),
			this.createMessage(sender, receiver, "테스트 메시지 2"),
			this.createMessage(sender, receiver, "테스트 메시지 3")
		));

		// * WHEN: 이걸 실행하면
		List<MessageData.Search> searches = messageService.searchReceiveMessage(receiver);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches.stream().map(MessageData.Search::getContent)).contains("테스트 메시지 1", "테스트 메시지 2",
			"테스트 메시지 3");
		assertThat(searches.stream().map(MessageData.Search::getReceiverName)).contains("receiver", "receiver",
			"receiver");
		assertThat(searches.stream().map(MessageData.Search::getSenderName)).contains("익명", "익명", "익명");
	}

	@Test
	@DisplayName("신규 메시지 생성 테스트")
	void 신규_메시지_생성_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");
		Pick pick = spy(Pick.of(sender, receiver, createQuestion(sender)));

		when(pickRepository.findById(1L)).thenReturn(Optional.of(pick));
		when(pick.getId()).thenReturn(1L);
		when(pick.isMessageSend()).thenReturn(false);

		MessageData.Create create = new MessageData.Create();
		create.setPickId(pick.getId());
		create.setContent("테스트 메시지");
		create.setReceiverId(receiver.getId());

		when(em.getReference(User.class, receiver.getId())).thenReturn(receiver);

		// * WHEN: 이걸 실행하면
		messageService.createMessage(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		verify(pick).send();
		verify(messageRepository).save(any(Message.class));
	}

	@Test
	@DisplayName("존재하지 않는 픽 ID 테스트")
	void 존재하지_않는_픽ID_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");
		Long pickId = 1L;

		when(pickRepository.findById(pickId)).thenReturn(Optional.empty());

		MessageData.Create create = new MessageData.Create();
		create.setPickId(pickId);
		create.setContent("테스트 메시지");
		create.setReceiverId(receiver.getId());

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> messageService.createMessage(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("존재하지 않는 픽입니다.");
	}

	@Test
	@DisplayName("이미 보낸 메시지 테스트")
	void 이미_보낸_메시지_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");
		Pick pick = spy(Pick.of(sender, receiver, createQuestion(sender)));

		when(pickRepository.findById(1L)).thenReturn(Optional.of(pick));
		when(pick.getId()).thenReturn(1L);
		when(pick.isMessageSend()).thenReturn(true);

		MessageData.Create create = new MessageData.Create();
		create.setPickId(pick.getId());
		create.setContent("테스트 메시지");
		create.setReceiverId(receiver.getId());

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> messageService.createMessage(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("하나의 픽에 대해서는 하나의 메시지만 보낼 수 있습니다.");
	}

	@Test
	@DisplayName("보낸 메시지 삭제 테스트")
	void 보낸_메시지_삭제_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");

		Message message = spy(this.createMessage(sender, receiver, "테스트 메시지"));

		when(message.getId()).thenReturn(1L);
		when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

		// * WHEN: 이걸 실행하면
		messageService.deleteSendMessage(sender, message.getId());

		// * THEN: 이런 결과가 나와야 한다
		verify(message).deleteMessageOfSender();
	}

	@Test
	@DisplayName("보낸 메시지 ID 존재 X 테스트")
	void 보낸_메시지_ID_존재_X_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		Long wrongId = 2L;

		when(messageRepository.findById(wrongId)).thenReturn(Optional.empty());

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> messageService.deleteSendMessage(sender, wrongId);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("존재하지 않는 메시지입니다.");
	}

	@Test
	@DisplayName("타인 메시지 삭제 방지 테스트")
	void 타인_메시지_삭제_방지_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("sender");
		User receiver = this.createUser("receiver");

		Message message = spy(this.createMessage(sender, receiver, "테스트 메시지"));

		when(message.getId()).thenReturn(1L);
		when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> messageService.deleteSendMessage(receiver, message.getId());

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("본인이 보낸 메시지만 삭제할 수 있습니다.");
	}

	private Message createMessage(User sender, User receiver, String content) {
		Pick pick = Pick.of(sender, receiver, createQuestion(sender));
		return Message.createMessage(sender, receiver, pick, content);
	}

	private Question createQuestion(User user) {
		QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");
		return Question.createQuestion(category, "테스트 질문", user);
	}
}