//package com.ssapick.server.domain.pick.controller;
//
//import static com.epages.restdocs.apispec.ResourceDocumentation.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.argThat;
//import static org.mockito.Mockito.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.ResultActions;
//
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.epages.restdocs.apispec.SimpleType;
//import com.ssapick.server.core.configuration.SecurityConfig;
//import com.ssapick.server.core.filter.JWTFilter;
//import com.ssapick.server.core.support.RestDocsSupport;
//import com.ssapick.server.domain.pick.dto.MessageData;
//import com.ssapick.server.domain.pick.entity.Message;
//import com.ssapick.server.domain.pick.entity.Pick;
//import com.ssapick.server.domain.pick.repository.MessageRepository;
//import com.ssapick.server.domain.pick.repository.PickRepository;
//import com.ssapick.server.domain.pick.service.MessageService;
//import com.ssapick.server.domain.question.entity.Question;
//import com.ssapick.server.domain.question.entity.QuestionCategory;
//import com.ssapick.server.domain.user.entity.User;
//
//@DisplayName("메시지 컨트롤러 테스트")
//@WebMvcTest(value = MessageController.class,
//	excludeFilters = {
//		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
//		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
//	}
//)
//class MessageControllerTest extends RestDocsSupport {
//	@MockBean
//	private MessageService messageService;
//
//	@MockBean
//	private PickRepository pickRepository;
//
//	@MockBean
//	private MessageRepository messageRepository;
//
//	@Test
//	@DisplayName("받은 메시지 성공 테스트")
//	@WithMockUser(username = "test-user")
//	void 받은_메시지_성공_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		User sender = this.createUser("보낸 사람");
//		User receiver = this.createUser("받은 사람");
//
//		List<MessageData.Search> searches = Stream.of(1, 2, 3).map((i) -> {
//			Message message = spy(this.createMessage(sender, receiver, "테스트 메시지 " + i));
//			when(message.getId()).thenReturn((long)i);
//			when(message.getCreatedAt()).thenReturn(LocalDateTime.now());
//			return message;
//		}).map((message) -> MessageData.Search.fromEntity(message, true)).toList();
//
//		when(messageService.searchReceiveMessage(any())).thenReturn(searches);
//
//		// * WHEN: 이걸 실행하면
//		ResultActions perform = this.mockMvc.perform(get("/api/v1/message/receive"));
//
//		// * THEN: 이런 결과가 나와야 한다
//		perform.andExpect(status().isOk())
//			.andDo(this.restDocs.document(resource(
//				ResourceSnippetParameters.builder()
//					.tag("message")
//					.summary("받은 메시지 목록 조회 API")
//					.description("받은 메시지 목록을 조회한다.")
//					.responseFields(response(
//						fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("메시지 ID"),
//						fieldWithPath("data[].senderName").type(JsonFieldType.STRING).description("보낸 사람 정보 (익명 처리)"),
//						fieldWithPath("data[].receiverName").type(JsonFieldType.STRING).description("받은 사람 정보 (본인)"),
//						fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("받은 일시"),
//						fieldWithPath("data[].questionContent").type(JsonFieldType.STRING).description("메시지 받은 질문 내용"),
//						fieldWithPath("data[].content").type(JsonFieldType.STRING).description("메시지 내용")
//					))
//					.build()
//			)));
//	}
//
//	@Test
//	@DisplayName("보낸 메시지 성공 테스트")
//	@WithMockUser(username = "test-user")
//	void 보낸_메시지_성공_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		User sender = this.createUser("보낸 사람");
//		User receiver = this.createUser("받은 사람");
//
//		List<MessageData.Search> searches = Stream.of(1, 2, 3).map((i) -> {
//			Message message = spy(this.createMessage(sender, receiver, "테스트 메시지 " + i));
//			when(message.getId()).thenReturn((long)i);
//			when(message.getCreatedAt()).thenReturn(LocalDateTime.now());
//			return message;
//		}).map((message) -> MessageData.Search.fromEntity(message, false)).toList();
//
//		when(messageService.searchSendMessage(any())).thenReturn(searches);
//
//		// * WHEN: 이걸 실행하면
//		ResultActions perform = this.mockMvc.perform(get("/api/v1/message/send"));
//
//		// * THEN: 이런 결과가 나와야 한다
//		perform.andExpect(status().isOk())
//			.andDo(this.restDocs.document(resource(
//				ResourceSnippetParameters.builder()
//					.tag("message")
//					.summary("보낸 메시지 목록 조회 API")
//					.description("보낸 메시지 목록을 조회한다.")
//					.responseFields(response(
//						fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("메시지 ID"),
//						fieldWithPath("data[].senderName").type(JsonFieldType.STRING).description("보낸 사람 정보 (본인)"),
//						fieldWithPath("data[].receiverName").type(JsonFieldType.STRING).description("받은 사람 정보 (실명 처리)"),
//						fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("받은 일시"),
//						fieldWithPath("data[].questionContent").type(JsonFieldType.STRING).description("메시지 받은 질문 내용"),
//						fieldWithPath("data[].content").type(JsonFieldType.STRING).description("메시지 내용")
//					))
//					.build()
//			)));
//	}
//
//	@Test
//	@DisplayName("메시지 보내기 성공 테스트")
//	@WithMockUser(username = "test-user")
//	void 메시지_보내기_성공_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		User receiver = this.createUser("받은 사람");
//
//		MessageData.Create create = new MessageData.Create();
//		create.setReceiverId(receiver.getId());
//		create.setPickId(1L);
//		create.setContent("테스트 메시지");
//
//		// * WHEN: 이걸 실행하면
//		ResultActions perform = this.mockMvc.perform(post("/api/v1/message")
//			.contentType("application/json")
//			.content(toJson(create))
//		);
//
//		// * THEN: 이런 결과가 나와야 한다
//		perform.andExpect(status().isCreated())
//			.andDo(this.restDocs.document(resource(
//				ResourceSnippetParameters.builder()
//					.tag("message")
//					.summary("메시지 보내기 API")
//					.description("자신이 받은 픽 기반으로 메시지를 보낸다. (픽 1개당 메시지 1번 가능)")
//					.requestFields(
//						fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("받는 사람 ID"),
//						fieldWithPath("pickId").type(JsonFieldType.NUMBER).description("픽 ID"),
//						fieldWithPath("content").type(JsonFieldType.STRING).description("메시지 내용")
//					)
//					.build()
//			)));
//
//		verify(messageService).createMessage(
//			argThat(user -> user.getUsername().equals("test-user")),
//			argThat(
//				message -> message.getReceiverId().equals(receiver.getId()) && message.getContent().equals("테스트 메시지"))
//		);
//	}
//
//	@Test
//	@DisplayName("받은 메시지 삭제 테스트")
//	@WithMockUser(username = "test-user")
//	void 받은_메시지_삭제_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		Long messageId = 1L;
//
//		// * WHEN: 이걸 실행하면
//		ResultActions perform = this.mockMvc.perform(
//			delete("/api/v1/message/{messageId}/receive", messageId)
//		);
//
//		// * THEN: 이런 결과가 나와야 한다
//		perform.andExpect(status().isNoContent())
//			.andDo(this.restDocs.document(resource(
//				ResourceSnippetParameters.builder()
//					.tag("message")
//					.summary("받은 메시지 삭제 API")
//					.description("자신이 받은 메시지를 삭제한다.")
//					.pathParameters(parameterWithName("messageId").type(SimpleType.NUMBER).description("메시지 ID"))
//					.build()
//			)));
//		verify(messageService).deleteReceiveMessage(
//			argThat(user -> user.getUsername().equals("test-user")),
//			eq(messageId)
//		);
//	}
//
//	@Test
//	@DisplayName("보낸 메시지 삭제 테스트")
//	@WithMockUser(username = "test-user")
//	void 보낸_메시지_삭제_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		Long messageId = 1L;
//
//		// * WHEN: 이걸 실행하면
//		ResultActions perform = this.mockMvc.perform(
//			delete("/api/v1/message/{messageId}/send", messageId)
//		);
//
//		// * THEN: 이런 결과가 나와야 한다
//		perform.andExpect(status().isNoContent())
//			.andDo(this.restDocs.document(resource(
//				ResourceSnippetParameters.builder()
//					.tag("message")
//					.summary("보낸 메시지 삭제 API")
//					.description("자신이 보낸 메시지를 삭제한다.")
//					.pathParameters(parameterWithName("messageId").type(SimpleType.NUMBER).description("메시지 ID"))
//					.build()
//			)));
//		verify(messageService).deleteSendMessage(
//			argThat(user -> user.getUsername().equals("test-user")),
//			eq(messageId)
//		);
//	}
//
//	private Message createMessage(User sender, User receiver, String content) {
//		Pick pick = Pick.of(sender, receiver, createQuestion(sender));
//		return Message.createMessage(sender, receiver, pick, content);
//	}
//
//	private Question createQuestion(User user) {
//		QuestionCategory category = QuestionCategory.create("테스트 카테고리", "");
//		return Question.createQuestion(category, "테스트 질문", user);
//	}
//}