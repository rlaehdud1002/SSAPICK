package com.ssapick.server.domain.pick.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.service.PickService;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;

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
		// GIVEN: 테스트 데이터 설정
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");
		List<PickData.Search> searches = Stream.of(1, 2, 3).map(i -> {
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
					.pick(pick)
					.hintType(HintType.NAME)
					.build()
			));
			return PickData.Search.fromEntity(pick, true);
		}).toList();

		// Mocking pageable response
		Pageable pageable = PageRequest.of(0, 10);
		Page<PickData.Search> pickPage = new PageImpl<>(searches, pageable, searches.size());

		when(pickService.searchReceivePick(
			argThat(user -> user.getUsername().equals("test-user")),
			any(Pageable.class)
		)).thenReturn(pickPage);

		// WHEN: API 호출
		ResultActions perform = this.mockMvc.perform(get("/api/v1/pick/receive")
			.param("page", "0")
			.param("size", "10"));

		// THEN: 결과 검증
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(
				resource(
					ResourceSnippetParameters.builder()
						.tag("픽")
						.summary("받은 픽 조회 API")
						.description("로그인된 사용자가 받은 픽을 조회한다.")
						.queryParameters(
							parameterWithName("page").description("페이지 번호"),
							parameterWithName("size").description("페이지 크기")
						)
						.responseFields(
							fieldWithPath("success").description("성공 여부"),
							fieldWithPath("status").description("HTTP 상태 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("응답 데이터").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.totalElements").description("총 요소 수")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.totalPages").description("총 페이지 수")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.size").description("페이지당 요소 수").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.number").description("현재 페이지 번호").type(JsonFieldType.NUMBER).optional(),
							fieldWithPath("data.first").description("첫 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.last").description("마지막 페이지 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.sort").description("정렬 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.sort.sorted").description("정렬 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.sort.unsorted").description("정렬되지 않음 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.sort.empty").description("정렬 정보 비어 있음 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.pageable").description("페이지 정보").type(JsonFieldType.OBJECT).optional(),
							fieldWithPath("data.pageable.pageNumber").description("페이지 번호")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.pageable.pageSize").description("페이지 크기")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.pageable.offset").description("오프셋")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.pageable.sort").description("정렬 정보")
								.type(JsonFieldType.OBJECT)
								.optional(),
							fieldWithPath("data.pageable.sort.sorted").description("정렬 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.pageable.sort.unsorted").description("정렬되지 않음 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.pageable.sort.empty").description("정렬 정보 비어 있음 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.pageable.unpaged").description("비페이지 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.pageable.paged").description("페이지 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.numberOfElements").description("페이지 내 요소 수")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.empty").description("비어 있음 여부").type(JsonFieldType.BOOLEAN).optional(),
							fieldWithPath("data.content").description("픽 데이터 목록").type(JsonFieldType.ARRAY).optional(),
							fieldWithPath("data.content[].id").description("픽 ID")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].sender").description("송신자 정보")
								.type(JsonFieldType.OBJECT)
								.optional(),
							fieldWithPath("data.content[].sender.userId").description("송신자 ID")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].sender.nickname").description("송신자 닉네임")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].sender.gender").description("송신자 성별")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].sender.campusName").description("송신자 캠퍼스 이름")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].sender.campusSection").description("송신자 캠퍼스 섹션")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].sender.campusDescription").description("송신자 캠퍼스 설명")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].sender.profileImage").description("송신자 프로필 이미지")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].sender.cohort").description("송신자 코호트")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].receiver").description("수신자 정보")
								.type(JsonFieldType.OBJECT)
								.optional(),
							fieldWithPath("data.content[].receiver.userId").description("수신자 ID")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].receiver.nickname").description("수신자 닉네임")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].receiver.gender").description("수신자 성별")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].receiver.campusName").description("수신자 캠퍼스 이름")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].receiver.campusSection").description("수신자 캠퍼스 섹션")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].receiver.campusDescription").description("수신자 캠퍼스 설명")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].receiver.profileImage").description("수신자 프로필 이미지")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].receiver.cohort").description("수신자 코호트")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].question").description("질문 정보")
								.type(JsonFieldType.OBJECT)
								.optional(),
							fieldWithPath("data.content[].question.id").description("질문 ID")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].question.banCount").description("질문 금지 수")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].question.skipCount").description("질문 스킵 수")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].question.category").description("질문 카테고리")
								.type(JsonFieldType.OBJECT)
								.optional(),
							fieldWithPath("data.content[].question.category.id").description("질문 카테고리 ID")
								.type(JsonFieldType.NUMBER)
								.optional(),
							fieldWithPath("data.content[].question.category.name").description("질문 카테고리 이름")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].question.category.thumbnail").description("질문 카테고리 썸네일")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].question.content").description("질문 내용")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].alarm").description("알림 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.content[].createdAt").description("생성 일시")
								.type(JsonFieldType.STRING)
								.optional(),
							fieldWithPath("data.content[].messageSend").description("메시지 전송 여부")
								.type(JsonFieldType.BOOLEAN)
								.optional(),
							fieldWithPath("data.content[].openedHints").description("열린 힌트 목록")
								.type(JsonFieldType.ARRAY)
								.optional(),
							fieldWithPath("data.content[].openedHints[].id").description("힌트 ID")
								.type(JsonFieldType.NUMBER)
								.optional()
						)
						.build()
				)
			));
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

		PickData.PickCondition pickCondition = PickData.PickCondition.builder()
			.index(1)
			.pickCount(1)
			.blockCount(1)
			.passCount(1)
			.isCooltime(false)
			.build();

		when(pickService.createPick(any(User.class), any(PickData.Create.class))).thenReturn(pickCondition);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
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
					.responseFields(response(
						fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
						fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
						fieldWithPath("data.index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("data.pickCount").type(JsonFieldType.NUMBER).description("픽한 횟수"),
						fieldWithPath("data.blockCount").type(JsonFieldType.NUMBER).description("차단한 횟수"),
						fieldWithPath("data.passCount").type(JsonFieldType.NUMBER).description("패스한 횟수"),
						fieldWithPath("data.cooltime").type(JsonFieldType.BOOLEAN).description("쿨타임 여부"),
						fieldWithPath("data.endTime").type(JsonFieldType.NULL).description("쿨타임 종료 시간")

					))
					.build()
			)));
	}

	@Test
	@DisplayName("픽_선택_시_질문_차단")
	@WithMockUser(username = "test-user")
	void 픽_선택_시_질문_차단() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User user = createUser("test");

		Question question = spy(this.createQuestion("테스트 질문"));
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setQuestionId(question.getId());
		create.setIndex(1);
		create.setStatus(PickData.PickStatus.BLOCK);

		PickData.PickCondition pickCondition = PickData.PickCondition.builder()
			.index(1)
			.pickCount(1)
			.blockCount(1)
			.passCount(1)
			.isCooltime(false)
			.build();

		when(pickService.createPick(any(User.class), any(PickData.Create.class))).thenReturn(pickCondition);
		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
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
					.responseFields(response(
						fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
						fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
						fieldWithPath("data.index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("data.pickCount").type(JsonFieldType.NUMBER).description("픽한 횟수"),
						fieldWithPath("data.blockCount").type(JsonFieldType.NUMBER).description("차단한 횟수"),
						fieldWithPath("data.passCount").type(JsonFieldType.NUMBER).description("패스한 횟수"),
						fieldWithPath("data.cooltime").type(JsonFieldType.BOOLEAN).description("쿨타임 여부"),
						fieldWithPath("data.endTime").type(JsonFieldType.NULL).description("쿨타임 종료 시간")
					))
					.build()
			)));
	}

	@Test
	@DisplayName("픽_선택_테스트")
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

		PickData.PickCondition pickCondition = PickData.PickCondition.builder()
			.index(1)
			.pickCount(1)
			.blockCount(1)
			.passCount(1)
			.isCooltime(false)
			.build();

		when(pickService.createPick(any(User.class), any(PickData.Create.class))).thenReturn(pickCondition);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
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
					.responseFields(response(
						fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
						fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
						fieldWithPath("data.index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("data.pickCount").type(JsonFieldType.NUMBER).description("픽한 횟수"),
						fieldWithPath("data.blockCount").type(JsonFieldType.NUMBER).description("차단한 횟수"),
						fieldWithPath("data.passCount").type(JsonFieldType.NUMBER).description("패스한 횟수"),
						fieldWithPath("data.cooltime").type(JsonFieldType.BOOLEAN).description("쿨타임 여부"),
						fieldWithPath("data.endTime").type(JsonFieldType.NULL).description("쿨타임 종료 시간")
					))
					.build()
			)));
	}

	@Test
	@DisplayName("픽_진행_상태_조회_테스트")
	@WithMockUser(username = "test-user")
	void 픽_진행_상태_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		PickData.PickCondition pickCondition = PickData.PickCondition.builder()
			.index(1)
			.pickCount(1)
			.blockCount(1)
			.passCount(1)
			.isCooltime(false)
			.build();

		when(pickService.getPickCondition(any())).thenReturn(pickCondition);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/pick")
			.contentType(MediaType.APPLICATION_JSON)
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 진행 상태 조회 API")
					.description("사용자의 픽 진행 상태를 조회한다.")
					.responseFields(
						fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("응답 성공 여부"),
						fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 상태 코드"),
						fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
						fieldWithPath("data.index").type(JsonFieldType.NUMBER).description("현재 질문 리스트의 인덱스 번호"),
						fieldWithPath("data.pickCount").type(JsonFieldType.NUMBER).description("픽한 횟수"),
						fieldWithPath("data.blockCount").type(JsonFieldType.NUMBER).description("차단한 횟수"),
						fieldWithPath("data.passCount").type(JsonFieldType.NUMBER).description("패스한 횟수"),
						fieldWithPath("data.cooltime").type(JsonFieldType.BOOLEAN).description("쿨타임 여부"),
						fieldWithPath("data.endTime").type(JsonFieldType.OBJECT).optional().description("쿨타임 종료 시간")

					)
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
					.pick(pick)
					.hintType(HintType.COHORT)
					.build()
			));
			return pick;
		}).map((pick) -> PickData.Search.fromEntity(pick, true)).toList();

		when(pickService.getPickWithAlarmOn(
			argThat(user -> user.getUsername().equals("test-user"))
		)).thenReturn(searches.get(0));

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(patch("/api/v1/pick/{pickId}", 1L));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("픽 알람 설정 API")
					.description("사용자가 선택한 픽의 알람을 설정한다.")
					.responseFields(empty())
					.build()
			)));
	}

	@Test
	@DisplayName("픽 사용자 리롤시 픽코 사용")
	@WithMockUser(username = "test-user")
	void 픽_사용자_리롤시_픽코_사용() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(patch("/api/v1/pick/re-roll"));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("사용자 리롤 픽코 사용 API")
					.description("사용자를 리롤하면 픽코를 사용한다")
					.responseFields(empty())
					.build()
			)));
	}


	@Test
	@DisplayName("알람설정한 픽 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 알람설정한_픽_조회_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받은 사람");

		Question question = spy(createQuestion("테스트 질문"));
		QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리 썸네일"));
		when(category.getId()).thenReturn(1L);
		when(question.getQuestionCategory()).thenReturn(category);
		when(question.getId()).thenReturn(1L);
		when(question.getCreatedAt()).thenReturn(LocalDateTime.now());

		Pick pick = spy(createPick(sender, receiver, question));
		when(pick.getId()).thenReturn(1L);
		when(pick.getCreatedAt()).thenReturn(LocalDateTime.now());

		PickData.Search search = PickData.Search.fromEntity(pick, false);

		when(pickService.getPickWithAlarmOn(
			argThat(user -> user.getUsername().equals("test-user")))
		).thenReturn(search);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/pick/alarm"));

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(this.restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("픽")
					.summary("알람설정한 픽 조회 API")
					.description("알람설정한 픽을 조회한다.")
					.responseFields(response(
						fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("픽 ID"),
						fieldWithPath("data.sender.userId").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 ID (익명 처리)"),
						fieldWithPath("data.sender.nickname").type(JsonFieldType.STRING).description("픽 보낸 사람 익명 처리"),
						fieldWithPath("data.sender.gender").type(JsonFieldType.STRING).description("픽 보낸 사람 성별"),
						fieldWithPath("data.sender.campusName").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 캠퍼스 소속"),
						fieldWithPath("data.sender.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 보낸 사람 캠퍼스 반"),
						fieldWithPath("data.sender.campusDescription").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 전공"),
						fieldWithPath("data.sender.profileImage").type(JsonFieldType.STRING)
							.description("픽 보낸 사람 프로필 이미지 익명 처리"),
						fieldWithPath("data.sender.cohort").type(JsonFieldType.NUMBER).description("픽 보낸 사람 기수"),
						fieldWithPath("data.receiver.userId").type(JsonFieldType.NUMBER).description("픽 받은 사람 ID"),
						fieldWithPath("data.receiver.nickname").type(JsonFieldType.STRING).description("픽 받은 사람 이름"),
						fieldWithPath("data.receiver.gender").type(JsonFieldType.STRING).description("픽 받은 사람 성별"),
						fieldWithPath("data.receiver.campusName").type(JsonFieldType.STRING)
							.description("픽 받은 사람 캠퍼스 소속"),
						fieldWithPath("data.receiver.campusSection").type(JsonFieldType.NUMBER)
							.description("픽 받은 사람 캠퍼스 반"),
						fieldWithPath("data.receiver.campusDescription").type(JsonFieldType.STRING)
							.description("픽 받은 사람 전공"),
						fieldWithPath("data.receiver.profileImage").type(JsonFieldType.STRING)
							.description("픽 받은 사람 프로필 이미지"),
						fieldWithPath("data.receiver.cohort").type(JsonFieldType.NUMBER).description("픽 받은 사람 기수"),
						fieldWithPath("data.question.id").type(JsonFieldType.NUMBER).description("질문 ID"),
						fieldWithPath("data.question.banCount").description("질문을 차단한 횟수"),
						fieldWithPath("data.question.skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
						fieldWithPath("data.question.category.id").type(JsonFieldType.NUMBER)
							.description("질문 카테고리 ID"),
						fieldWithPath("data.question.category.name").type(JsonFieldType.STRING)
							.description("질문 카테고리명"),
						fieldWithPath("data.question.category.thumbnail").type(JsonFieldType.STRING)
							.description("질문 카테고리 썸네일"),
						fieldWithPath("data.openedHints[]").type(JsonFieldType.ARRAY).description("현재 오픈된 힌트 정보"),
						fieldWithPath("data.question.content").type(JsonFieldType.STRING).description("질문 내용"),
						fieldWithPath("data.messageSend").type(JsonFieldType.BOOLEAN).description("해당 픽 쪽지 전송 여부"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("픽 생성일시"),
						fieldWithPath("data.alarm").type(JsonFieldType.BOOLEAN).description("픽 알람 설정 여부")

					))
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