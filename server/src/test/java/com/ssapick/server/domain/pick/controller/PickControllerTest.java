package com.ssapick.server.domain.pick.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.service.PickService;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("싸픽 컨트롤러 테스트")
@WebMvcTest(
	value = PickController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class)
	}
)
class PickControllerTest extends RestDocsSupport {
	@MockBean
	private PickService pickService;

	@Test
	@DisplayName("받은 픽 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 받은_픽_조회_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");
		List<PickData.Search> searches = Stream.of(1, 2, 3).map((i) -> {
			Question question = spy(createQuestion("테스트 질문 " + i));
			QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리 썸네일"));

			when(category.getId()).thenReturn((long)i);
			when(question.getQuestionCategory()).thenReturn(category);
			when(question.getId()).thenReturn((long)i);
			when(question.getCreatedAt()).thenReturn(LocalDateTime.now());

			Pick pick = spy(createPick(sender, receiver, question));
			when(pick.getId()).thenReturn((long)i);
			when(pick.getCreatedAt()).thenReturn(LocalDateTime.now());
			when(pick.getHintOpens()).thenReturn(List.of(
				HintOpen.builder()
					.hint(Hint.createHint("힌트1", HintType.COHORT))
					.pick(pick)
					.build()
			));
			return pick;
		}).map((pick) -> PickData.Search.fromEntity(pick, true)).toList();

		when(pickService.searchReceivePick(
			argThat(user -> user.getUsername().equals("test-user")))
		).thenReturn(searches);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/pick/receive"));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("받은 픽 조회 API")
					.description("로그인된 사용자가 받은 픽을 조회한다.")
					.responseFields(response(
						fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("픽 ID"),
						fieldWithPath("data[].sender.userId").type(JsonFieldType.NULL)
							.description("픽 보낸 사람 ID (익명 처리)"),
						fieldWithPath("data[].sender.nickname").type(JsonFieldType.NULL).description("픽 보낸 사람 익명 처리"),
						fieldWithPath("data[].sender.gender").type(JsonFieldType.STRING).description("픽 보낸 사람 성별"),
						fieldWithPath("data[].sender.campusName").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 캠퍼스 소속"),
						fieldWithPath("data[].sender.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 캠퍼스 반"),
						fieldWithPath("data[].sender.campusDescription").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 전공"),
						fieldWithPath("data[].sender.profileImage").type(JsonFieldType.NULL)
							.description("픽 보낸 사람 프로필 이미지 익명 처리"),
						fieldWithPath("data[].sender.cohort").type(JsonFieldType.NUMBER).description("픽 보낸 사람 기수"),
						fieldWithPath("data[].receiver.userId").type(JsonFieldType.NUMBER).description("픽 받은 사람 ID"),
						fieldWithPath("data[].receiver.nickname").type(JsonFieldType.STRING).description("픽 받은 사람 이름"),
						fieldWithPath("data[].receiver.gender").type(JsonFieldType.STRING).description("픽 받은 사람 성별"),
						fieldWithPath("data[].receiver.campusName").type(JsonFieldType.STRING)
							.description("픽 받은 사람 캠퍼스 소속"),
						fieldWithPath("data[].receiver.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 받은 사람 캠퍼스 반"),
						fieldWithPath("data[].receiver.campusDescription").type(JsonFieldType.STRING)
							.description("픽 받은 사람 전공"),
						fieldWithPath("data[].receiver.profileImage").type(JsonFieldType.STRING)
							.description("픽 받은 사람 프로필 이미지"),
						fieldWithPath("data[].receiver.cohort").type(JsonFieldType.NUMBER).description("픽 받은 사람 기수"),
						fieldWithPath("data[].question.id").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("data[].question.banCount").description("질문을 차단한 횟수"),
						fieldWithPath("data[].question.skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
						fieldWithPath("data[].question.category.id").type(JsonFieldType.NUMBER)
							.description("질문 카테고리 ID"),
						fieldWithPath("data[].question.category.name").type(JsonFieldType.STRING)
							.description("질문 카테고리명"),
						fieldWithPath("data[].question.category.thumbnail").type(JsonFieldType.STRING)
							.description("질문 카테고리 썸네일"),
						fieldWithPath("data[].openedHints[]").type(JsonFieldType.ARRAY).description("현재 오픈된 힌트 정보"),
						fieldWithPath("data[].question.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data[].messageSend").type(JsonFieldType.BOOLEAN).description("해당 픽 쪽지 전송 여부"),
						fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("픽 생성일시"),
						fieldWithPath("data[].alarm").type(JsonFieldType.BOOLEAN).description("픽 알람 설정 여부")
					))
					.build())
			));

		verify(pickService, times(1)).searchReceivePick(argThat(user -> user.getUsername().equals("test-user")));
	}

	@Test
	@DisplayName("보낸 픽 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 보낸_픽_조회_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");
		List<PickData.Search> searches = Stream.of(1, 2, 3).map((i) -> {
			Question question = spy(createQuestion("테스트 질문 " + i));
			QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리 썸네일"));
			when(category.getId()).thenReturn((long)i);
			when(question.getQuestionCategory()).thenReturn(category);
			when(question.getId()).thenReturn((long)i);
			when(question.getCreatedAt()).thenReturn(LocalDateTime.now());

			Pick pick = spy(createPick(sender, receiver, question));
			when(pick.getId()).thenReturn((long)i);
			when(pick.getCreatedAt()).thenReturn(LocalDateTime.now());
			return pick;
		}).map((pick) -> PickData.Search.fromEntity(pick, false)).toList();

		when(pickService.searchSendPick(
			argThat(user -> user.getUsername().equals("test-user")))
		).thenReturn(searches);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/pick/send"));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("보낸 픽 조회 API")
					.description("로그인된 사용자가 보낸 픽을 조회한다.")
					.responseFields(response(
						fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("픽 ID"),
						fieldWithPath("data[].sender.userId").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 ID (익명 처리)"),
						fieldWithPath("data[].sender.nickname").type(JsonFieldType.STRING).description("픽 보낸 사람 익명 처리"),
						fieldWithPath("data[].sender.gender").type(JsonFieldType.STRING).description("픽 보낸 사람 성별"),
						fieldWithPath("data[].sender.campusName").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 캠퍼스 소속"),
						fieldWithPath("data[].sender.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 캠퍼스 반"),
						fieldWithPath("data[].sender.campusDescription").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 전공"),
						fieldWithPath("data[].sender.profileImage").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 프로필 이미지 익명 처리"),
						fieldWithPath("data[].sender.cohort").type(JsonFieldType.NUMBER).description("픽 보낸 사람 기수"),
						fieldWithPath("data[].receiver.userId").type(JsonFieldType.NUMBER).description("픽 받은 사람 ID"),
						fieldWithPath("data[].receiver.nickname").type(JsonFieldType.STRING).description("픽 받은 사람 이름"),
						fieldWithPath("data[].receiver.gender").type(JsonFieldType.STRING).description("픽 받은 사람 성별"),
						fieldWithPath("data[].receiver.campusName").type(JsonFieldType.STRING)
							.description("픽 받은 사람 캠퍼스 소속"),
						fieldWithPath("data[].receiver.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 받은 사람 캠퍼스 반"),
						fieldWithPath("data[].receiver.campusDescription").type(JsonFieldType.STRING)
							.description("픽 받은 사람 전공"),
						fieldWithPath("data[].receiver.profileImage").type(JsonFieldType.STRING)
							.description("픽 받은 사람 프로필 이미지"),
						fieldWithPath("data[].receiver.cohort").type(JsonFieldType.NUMBER).description("픽 받은 사람 기수"),
						fieldWithPath("data[].question.id").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("data[].question.banCount").description("질문을 차단한 횟수"),
						fieldWithPath("data[].question.skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
						fieldWithPath("data[].question.category.id").type(JsonFieldType.NUMBER)
							.description("질문 카테고리 ID"),
						fieldWithPath("data[].question.category.name").type(JsonFieldType.STRING)
							.description("질문 카테고리명"),
						fieldWithPath("data[].question.category.thumbnail").type(JsonFieldType.STRING)
							.description("질문 카테고리 썸네일"),
						fieldWithPath("data[].openedHints[]").type(JsonFieldType.ARRAY).description("현재 오픈된 힌트 정보"),
						fieldWithPath("data[].question.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data[].messageSend").type(JsonFieldType.BOOLEAN).description("해당 픽 쪽지 전송 여부"),
						fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("픽 생성일시"),
						fieldWithPath("data[].alarm").type(JsonFieldType.BOOLEAN).description("픽 알람 설정 여부")

					))
					.build()
			)));

		verify(pickService, times(1)).searchSendPick(argThat(user -> user.getUsername().equals("test-user")));
	}

	@Test
	@DisplayName("픽_선택_패스_테스트")
	@WithMockUser(username = "test-user")
	void 픽_선택_패스_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Question question = spy(this.createQuestion("테스트 질문"));
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setQuestionId(question.getId());
		create.setIndex(1);
		create.setStatus(PickData.PickStatus.PASS);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 생성 API")
					.description("사용자가 패스한 픽을 처리한다.")
					.requestFields(
						fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("status").type(JsonFieldType.STRING).description("픽 상태 (선택, 패스, 차단)")
					)
					.responseFields(empty())
					.build()
			)));
	}

	@Test
	@DisplayName("픽_선택_시_질문_차단")
	@WithMockUser(username = "test-user")
	void 픽_선택_시_질문_차단() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Question question = spy(this.createQuestion("테스트 질문"));
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setQuestionId(question.getId());
		create.setIndex(1);
		create.setStatus(PickData.PickStatus.PASS);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 생성 API")
					.description("픽_선택_시_질문_차단을_하는_경우를_처리한다.")
					.requestFields(
						fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("status").type(JsonFieldType.STRING).description("픽 상태 (선택, 패스, 차단)")
					)
					.responseFields(empty())
					.build()
			)));
	}

	@Test
	@DisplayName("픽_패스_테스트")
	@WithMockUser(username = "test-user")
	void 픽_선택_생성_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User receiver = this.createUser("받은 사람");
		Question question = spy(this.createQuestion("테스트 질문"));
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setReceiverId(receiver.getId());
		create.setQuestionId(question.getId());
		create.setIndex(1);
		create.setStatus(PickData.PickStatus.PICKED);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 생성 API")
					.description("사용자가 선택한 픽을 데이터베이스에 생성한다.")
					.requestFields(
						fieldWithPath("receiverId").type(JsonFieldType.NUMBER).description("픽 받을 사람 ID"),
						fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("status").type(JsonFieldType.STRING).description("픽 상태 (선택, 패스, 차단)")
					)
					.responseFields(empty())
					.build()
			)));
	}

	@Test
	@DisplayName("픽_알람_설정_테스트")
	@WithMockUser(username = "test-user")
	void 픽_알람_설정_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");
		List<PickData.Search> searches = Stream.of(1, 2, 3).map((i) -> {
			Question question = spy(createQuestion("테스트 질문 " + i));
			QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리 썸네일"));

			when(category.getId()).thenReturn((long)i);
			when(question.getQuestionCategory()).thenReturn(category);
			when(question.getId()).thenReturn((long)i);
			when(question.getCreatedAt()).thenReturn(LocalDateTime.now());

			Pick pick = spy(createPick(sender, receiver, question));
			when(pick.getId()).thenReturn((long)i);
			when(pick.getCreatedAt()).thenReturn(LocalDateTime.now());
			when(pick.getHintOpens()).thenReturn(List.of(
				HintOpen.builder()
					.hint(Hint.createHint("힌트1", HintType.COHORT))
					.pick(pick)
					.build()
			));
			return pick;
		}).map((pick) -> PickData.Search.fromEntity(pick, true)).toList();

		when(pickService.searchReceivePick(
			argThat(user -> user.getUsername().equals("test-user")))
		).thenReturn(searches);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(patch("/api/v1/pick/{pickId}", 1L));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 알람 설정 API")
					.description("사용자가 선택한 픽의 알람을 설정한다.")
					.responseFields(
						fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
						fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
						fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("픽 ID"),
						fieldWithPath("data[].sender.userId").type(JsonFieldType.NULL)
							.description("픽 보낸 사람 ID (익명 처리)"),
						fieldWithPath("data[].sender.nickname").type(JsonFieldType.NULL).description("픽 보낸 사람 익명 처리"),
						fieldWithPath("data[].sender.gender").type(JsonFieldType.STRING).description("픽 보낸 사람 성별"),
						fieldWithPath("data[].sender.campusName").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 캠퍼스 소속"),
						fieldWithPath("data[].sender.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 캠퍼스 반"),
						fieldWithPath("data[].sender.campusDescription").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 전공"),
						fieldWithPath("data[].sender.profileImage").type(JsonFieldType.NULL)
							.description("픽 보낸 사람 프로필 이미지 익명 처리"),
						fieldWithPath("data[].sender.cohort").type(JsonFieldType.NUMBER).description("픽 보낸 사람 기수"),
						fieldWithPath("data[].receiver.userId").type(JsonFieldType.NUMBER).description("픽 받은 사람 ID"),
						fieldWithPath("data[].receiver.nickname").type(JsonFieldType.STRING).description("픽 받은 사람 이름"),
						fieldWithPath("data[].receiver.gender").type(JsonFieldType.STRING).description("픽 받은 사람 성별"),
						fieldWithPath("data[].receiver.campusName").type(JsonFieldType.STRING)
							.description("픽 받은 사람 캠퍼스 소속"),
						fieldWithPath("data[].receiver.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 받은 사람 캠퍼스 반"),
						fieldWithPath("data[].receiver.campusDescription").type(JsonFieldType.STRING)
							.description("픽 받은 사람 전공"),
						fieldWithPath("data[].receiver.profileImage").type(JsonFieldType.STRING)
							.description("픽 받은 사람 프로필 이미지"),
						fieldWithPath("data[].receiver.cohort").type(JsonFieldType.NUMBER).description("픽 받은 사람 기수"),
						fieldWithPath("data[].question.id").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("data[].question.banCount").description("질문을 차단한 횟수"),
						fieldWithPath("data[].question.skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
						fieldWithPath("data[].question.category.id").type(JsonFieldType.NUMBER)
							.description("질문 카테고리 ID"),
						fieldWithPath("data[].question.category.name").type(JsonFieldType.STRING)
							.description("질문 카테고리명"),
						fieldWithPath("data[].question.category.thumbnail").type(JsonFieldType.STRING)
							.description("질문 카테고리 썸네일"),
						fieldWithPath("data[].openedHints[]").type(JsonFieldType.ARRAY).description("현재 오픈된 힌트 정보"),
						fieldWithPath("data[].question.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data[].messageSend").type(JsonFieldType.BOOLEAN).description("해당 픽 쪽지 전송 여부"),
						fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("픽 생성일시"),
						fieldWithPath("data[].alarm").type(JsonFieldType.BOOLEAN).description("픽 알람 설정 여부")
					)
					.build()
			)));
	}

	private Pick createPick(User sender, User receiver, Question question) {
		return Pick.of(sender, receiver, question);
	}

	private Question createQuestion(String content) {
		QuestionCategory category = QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리");
		return Question.createQuestion(category, content, createUser("author"));
	}
}