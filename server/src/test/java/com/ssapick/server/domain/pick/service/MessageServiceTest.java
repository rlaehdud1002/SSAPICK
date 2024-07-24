package com.ssapick.server.domain.pick.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest extends UserSupport {
    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private PickRepository pickRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("보낸_메시지_확인")
    void 보낸_메시지_확인() throws Exception {
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
        assertThat(searches.stream().map(MessageData.Search::getContent)).contains("테스트 메시지 1", "테스트 메시지 2", "테스트 메시지 3");
        assertThat(searches.stream().map(MessageData.Search::getReceiverName)).contains("receiver", "receiver", "receiver");
        assertThat(searches.stream().map(MessageData.Search::getSenderName)).contains("sender", "sender", "sender");
    }

    private Message createMessage(User sender, User receiver, String content) {
        Pick pick = Pick.createPick(sender, receiver, createQuestion(sender));
        return Message.createMessage(sender, receiver, pick, content);
    }

    private Question createQuestion(User user) {
        QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");
        return Question.createQuestion(category, "테스트 질문", user);
    }
}