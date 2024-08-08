package com.ssapick.server.domain.pick.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("메시지 컨트롤러 테스트")
@WebMvcTest(value = MessageController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
	}
)
class MessageControllerTest extends RestDocsSupport {
	@MockBean
	private MessageService messageService;

	@MockBean
	private MessageRepository messageRepository;

	@Test
	@DisplayName("받은 메시지 페이징 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 받은_메시지_페이징_조회_성공_테스트() throws Exception {
		// * GIVEN: 테스트 데이터 설정
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");

		// 메시지 리스트 생성
		List<MessageData.Search> searches = Stream.of(16, 15, 14).map(i -> {
			Message message = spy(this.createMessage(sender, receiver, "내용" + i));
			when(message.getId()).thenReturn((long)i);
			when(message.getCreatedAt()).thenReturn(LocalDateTime.now());
			return MessageData.Search.fromEntity(message, false);
		}).toList();


		// Mocking pageable response
		Pageable pageable = PageRequest.of(0, 3);
		Page<MessageData.Search> searchPage = new PageImpl<>(searches, pageable, 16); // totalElements = 16

		when(messageService.searchSendMessage(
			argThat(user -> user.getUsername().equals("test-user")),
			any(Pageable.class)
		)).thenReturn(searchPage);

		// * WHEN: API 호출
		ResultActions perform = this.mockMvc.perform(get("/api/v1/message/receive")
			.param("page", "0")
			.param("size", "3"));

		// * THEN: 결과 검증
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(
				resource(
					ResourceSnippetParameters.builder()
						.tag("쪽지")
						.summary("받은 메시지 목록 조회 API")
						.description("받은 메시지 목록을 페이징하여 조회한다.")
						.queryParameters(
							parameterWithName("page").description("페이지 번호"),
							parameterWithName("size").description("페이지 크기")
						)
						.responseFields(
							fieldWithPath("success").description("성공 여부"),
							fieldWithPath("status").description("HTTP 상태 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.totalElements").description("총 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.totalPages").description("총 페이지 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.size").description("페이지당 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.number").description("현재 페이지 번호").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.first").description("첫 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.last").description("마지막 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort").description("정렬 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.sort.sorted").description("정렬 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort.unsorted").description("정렬되지 않음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort.empty").description("정렬 정보 비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable").description("페이지 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.pageable.pageNumber").description("페이지 번호").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.pageSize").description("페이지 크기").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.offset").description("오프셋").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.sort").description("정렬 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.pageable.sort.sorted").description("정렬 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.sort.unsorted").description("정렬되지 않음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.sort.empty").description("정렬 정보 비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.unpaged").description("비페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.paged").description("페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.numberOfElements").description("페이지 내 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.empty").description("비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.content").description("메시지 데이터 목록").type(JsonFieldType.ARRAY).optional(),
							fieldWithPath("data.content[].id").description("메시지 ID").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.content[].senderId").description("메시지 보낸 사람 ID").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.content[].senderName").description("보낸 사람 정보 (익명 처리)").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].senderGender").description("보낸 사람 성별").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].receiverName").description("받은 사람 정보 (본인)").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].receiverGender").description("받은 사람 성별").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].createdAt").description("받은 일시").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].questionContent").description("메시지 받은 질문 내용").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].content").description("메시지 내용").type(JsonFieldType.STRING).optional()
						)
						.build()
				)
			));
	}

	@Test
	@DisplayName("보낸 메시지 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 보낸_메시지_조회_성공_테스트() throws Exception {
		// GIVEN: 테스트 데이터 설정
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");

		List<MessageData.Search> searches = Stream.of(16, 15, 14).map(i -> {
			Message message = spy(this.createMessage(sender, receiver, "내용" + i));
			when(message.getId()).thenReturn((long)i);
			when(message.getCreatedAt()).thenReturn(LocalDateTime.now());
			return MessageData.Search.fromEntity(message, false);
		}).toList();

		// Mocking pageable response
		Pageable pageable = PageRequest.of(0, 3);
		Page<MessageData.Search> searchPage = new PageImpl<>(searches, pageable, 16); // totalElements = 16

		when(messageService.searchSendMessage(
			argThat(user -> user.getUsername().equals("test-user")),
			any(Pageable.class)
		)).thenReturn(searchPage);

		// WHEN: API 호출
		ResultActions perform = this.mockMvc.perform(get("/api/v1/message/send")
			.param("page", "0")
			.param("size", "3"));

		// THEN: 결과 검증
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(
				resource(
					ResourceSnippetParameters.builder()
						.tag("쪽지")
						.summary("보낸 메시지 목록 조회 API")
						.description("로그인된 사용자가 보낸 메시지 목록을 조회한다.")
						.queryParameters(
							parameterWithName("page").description("페이지 번호"),
							parameterWithName("size").description("페이지 크기")
						)
						.responseFields(
							fieldWithPath("success").description("응답 성공 여부"),
							fieldWithPath("status").description("HTTP 상태 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.totalElements").description("총 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.totalPages").description("총 페이지 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.size").description("페이지당 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.number").description("현재 페이지 번호").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.first").description("첫 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.last").description("마지막 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort").description("정렬 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.sort.empty").description("정렬 정보 비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort.sorted").description("정렬 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort.unsorted").description("정렬되지 않음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable").description("페이지 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.pageable.pageNumber").description("페이지 번호").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.pageSize").description("페이지 크기").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.offset").description("오프셋").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.pageable.sort").description("정렬 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.pageable.sort.empty").description("정렬 정보 비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.sort.sorted").description("정렬 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.sort.unsorted").description("정렬되지 않음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.unpaged").description("비페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.pageable.paged").description("페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.numberOfElements").description("페이지 내 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.empty").description("비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.content").description("메시지 목록").type(JsonFieldType.ARRAY).optional(),
							fieldWithPath("data.content[].id").description("메시지 ID").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.content[].senderId").description("메시지 보낸 사람 ID").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.content[].senderName").description("보낸 사람 이름").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].receiverName").description("받은 사람 이름").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].senderGender").description("보낸 사람 성별").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].receiverGender").description("받은 사람 성별").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].createdAt").description("메시지 생성 일시").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].content").description("메시지 내용").type(JsonFieldType.STRING).optional(),
							fieldWithPath("data.content[].questionContent").description("메시지 질문 내용").type(JsonFieldType.STRING).optional()
						)
						.build()
				)
			));
	}



	@Test
	@DisplayName("메시지 보내기 성공 테스트")
	@WithMockUser(username = "test-user")
	void 메시지_보내기_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User receiver = this.createUser("받은 사람");

		MessageData.Create create = new MessageData.Create();
		create.setPickId(1L);
		create.setContent("테스트 메시지");

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/message")
			.contentType("application/json")
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("쪽지")
					.summary("메시지 보내기 API")
					.description("자신이 받은 픽 기반으로 메시지를 보낸다. (픽 1개당 메시지 1번 가능)")
					.requestFields(
						fieldWithPath("pickId").type(JsonFieldType.NUMBER).description("픽 ID"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("메시지 내용")
					)
					.build()
			)));

		verify(messageService).createMessage(any(), any());
	}

	@Test
	@DisplayName("받은 메시지 삭제 테스트")
	@WithMockUser(username = "test-user")
	void 받은_메시지_삭제_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Long messageId = 1L;

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(
			delete("/api/v1/message/{messageId}/receive", messageId)
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isNoContent())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("쪽지")
					.summary("받은 메시지 삭제 API")
					.description("자신이 받은 메시지를 삭제한다.")
					.pathParameters(parameterWithName("messageId").type(SimpleType.NUMBER).description("메시지 ID"))
					.build()
			)));
		verify(messageService).deleteReceiveMessage(
			argThat(user -> user.getUsername().equals("test-user")),
			eq(messageId)
		);
	}

	@Test
	@DisplayName("보낸 메시지 삭제 테스트")
	@WithMockUser(username = "test-user")
	void 보낸_메시지_삭제_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Long messageId = 1L;

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(
			delete("/api/v1/message/{messageId}/send", messageId)
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isNoContent())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("쪽지")
					.summary("보낸 메시지 삭제 API")
					.description("자신이 보낸 메시지를 삭제한다.")
					.pathParameters(parameterWithName("messageId").type(SimpleType.NUMBER).description("메시지 ID"))
					.build()
			)));
		verify(messageService).deleteSendMessage(
			argThat(user -> user.getUsername().equals("test-user")),
			eq(messageId)
		);
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
