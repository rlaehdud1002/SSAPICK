package com.ssapick.server.domain.pick.controller;

import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.service.MessageService;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MessageController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
    }
)
class MessageControllerTest extends RestDocsSupport {
    @MockBean
    private MessageService messageService;

    @Test
    @DisplayName("받은 메시지 성공 테스트")
    @WithMockUser(username = "test-user")
    void 받은_메시지_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User sender = this.createUser("보낸 사람");
        User receiver = this.createUser("받은 사람");
        List<Message> messages = List.of(
                createMessage(sender, receiver, "테스트 메시지 1"),
                createMessage(sender, receiver, "테스트 메시지 2"),
                createMessage(sender, receiver, "테스트 메시지 3")
        );
        when(messageService.searchSendMessage(any())).thenReturn(
                messages.stream()
                        .map((message) -> MessageData.Search.fromEntity(message, true))
                        .toList()
        );

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/message/receive"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("보낸 메시지 성공 테스트")
    void 보낸_메시지_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면

        // * THEN: 이런 결과가 나와야 한다

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