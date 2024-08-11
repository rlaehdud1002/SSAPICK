package com.ssapick.server.domain.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.FollowService;
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

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("팔로우 컨트롤러 테스트")
@WebMvcTest(
	value = FollowController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
	}
)
class FollowControllerTest extends RestDocsSupport {
	@MockBean
	private FollowService followService;

	@Test
	@DisplayName("팔로우 조회 성공 테스트")
	@WithMockUser(username = "test-user")
	void 팔로우_조회_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		List<User> users = List.of(this.createUser("테스트 유저 1"), this.createUser("테스트 유저 2"),
			this.createUser("테스트 유저 3"));
		List<ProfileData.Friend> profiles = users.stream()
			.map(ProfileData.Friend::fromEntity)
			.toList();

		when(followService.findFollowUsers(any())).thenReturn(profiles);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/follow")
			.contentType(MediaType.APPLICATION_JSON)
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(restDocs.document(resource(
				ResourceSnippetParameters
					.builder()
					.tag("팔로우")
					.description("팔로우한 유저 목록 조회 API")
					.summary("로그인된 사용자가 팔로우한 유저 목록을 조회한다.")
					.responseFields(response(
						fieldWithPath("data[]").description("팔로우한 유저 목록"),
						fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
						fieldWithPath("data[].name").type(JsonFieldType.STRING).description("유저 닉네임"),
						fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
						fieldWithPath("data[].cohort").type(JsonFieldType.NUMBER).description("기수 정보"),
						fieldWithPath("data[].campusSection").type(JsonFieldType.NUMBER).description("캠퍼스 반 정보"),
						fieldWithPath("data[].follow").type(JsonFieldType.BOOLEAN).description("유저 팔로우 여부"),
						fieldWithPath("data[].sameCampus").type(JsonFieldType.BOOLEAN).description("유저 동일 캠퍼스 여부")
					))
					.build()
			)));
	}

	@Test
	@DisplayName("팔로우 생성 성공 테스트")
	void 팔로우_생성_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Long userId = 1L;

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/follow/{userId}", userId)
			.contentType(MediaType.APPLICATION_JSON)
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(restDocs.document(resource(
					ResourceSnippetParameters
						.builder()
						.tag("팔로우")
						.description("팔로우 생성 API")
						.summary("로그인된 사용자가 다른 사용자를 팔로우한다.")
						.pathParameters(parameterWithName("userId").type(SimpleType.NUMBER).description("팔로우할 사용자 식별자"))
						.responseFields(empty())
						.build()
				))
			);
	}

	@Test
	@DisplayName("팔로우 삭제 성공 테스트")
	void 팔로우_삭제_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Long userId = 1L;

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(delete("/api/v1/follow/{userId}", userId)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer access-token")
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isNoContent())
			.andDo(restDocs.document(resource(
					ResourceSnippetParameters
						.builder()
						.tag("팔로우")
						.description("팔로우 삭제 API")
						.summary("로그인된 사용자가 다른 사용자를 언팔로우한다.")
						.pathParameters(parameterWithName("userId").type(SimpleType.NUMBER).description("언팔로우할 사용자 식별자"))
						.responseFields(empty())
						.build()
				))
			);
	}

	@Test
	@DisplayName("추천_팔로우_조회_테스트_페이징")
	void 추천_팔로우_조회_테스트_페이징() throws Exception {
		// * GIVEN: 페이징 처리된 데이터 준비
		List<User> users = List.of(
			this.createUser("테스트 유저 1"),
			this.createUser("테스트 유저 2"),
			this.createUser("테스트 유저 3")
		);
		List<ProfileData.Friend> friends = users.stream()
			.map(ProfileData.Friend::fromEntity)
			.toList();

		Page<ProfileData.Friend> page = new PageImpl<>(friends, PageRequest.of(0, 10), friends.size());

		when(followService.recommendFollow(any(), any(Pageable.class))).thenReturn(page);

		// * WHEN: API 요청
		ResultActions perform = this.mockMvc.perform(get("/api/v1/follow/recommend")
			.param("page", "0")  // 페이지 번호
			.param("size", "10") // 페이지 사이즈
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer access-token")
		);

		// * THEN: 응답 검증 및 문서화
		perform.andExpect(status().isOk())
			.andDo(restDocs.document(
				resource(
					ResourceSnippetParameters.builder()
						.tag("팔로우")
						.summary("추천 팔로우 조회 API")
						.description("추천 팔로우 유저 목록을 페이징하여 조회한다.")
						.responseFields(
							fieldWithPath("success").description("API 호출 성공 여부"),
							fieldWithPath("status").description("HTTP 상태 코드"),
							fieldWithPath("message").description("응답 메시지"),
							fieldWithPath("data").description("페이징된 추천 팔로우 유저 목록"),
							fieldWithPath("data.totalPages").description("전체 페이지 수"),
							fieldWithPath("data.totalElements").description("전체 요소 수"),
							fieldWithPath("data.size").description("현재 페이지의 요소 수"),
							fieldWithPath("data.numberOfElements").description("현재 페이지의 요소 수"),
							fieldWithPath("data.number").description("현재 페이지 번호"),
							fieldWithPath("data.first").description("첫 페이지 여부"),
							fieldWithPath("data.last").description("마지막 페이지 여부"),
							fieldWithPath("data.empty").description("비어 있는 페이지 여부"),
							fieldWithPath("data.content[]").description("추천 팔로우 유저 목록"),
							fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
							fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("유저 닉네임"),
							fieldWithPath("data.content[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
							fieldWithPath("data.content[].cohort").type(JsonFieldType.NUMBER).description("기수 정보"),
							fieldWithPath("data.content[].campusSection").type(JsonFieldType.NUMBER).description("캠퍼스 반 정보"),
							fieldWithPath("data.content[].follow").type(JsonFieldType.BOOLEAN).description("유저 팔로우 여부"),
							fieldWithPath("data.content[].sameCampus").type(JsonFieldType.BOOLEAN).description("유저 동일 캠퍼스 여부"),
							fieldWithPath("data.sort.empty").description("정렬 정보가 비어 있는지 여부"),
							fieldWithPath("data.sort.sorted").description("정렬 정보가 정렬되었는지 여부"),
							fieldWithPath("data.sort.unsorted").description("정렬 정보가 정렬되지 않았는지 여부"),
							fieldWithPath("data.pageable.pageNumber").description("현재 페이지 번호"),
							fieldWithPath("data.pageable.pageSize").description("페이지 사이즈"),
							fieldWithPath("data.pageable.sort.empty").description("정렬 정보가 비어 있는지 여부"),
							fieldWithPath("data.pageable.sort.sorted").description("정렬 정보가 정렬되었는지 여부"),
							fieldWithPath("data.pageable.sort.unsorted").description("정렬 정보가 정렬되지 않았는지 여부"),
							fieldWithPath("data.pageable.offset").description("페이지 오프셋"),
							fieldWithPath("data.pageable.paged").description("페이징 정보가 존재하는지 여부"),
							fieldWithPath("data.pageable.unpaged").description("페이징 정보가 없는지 여부")
						)
						.build()
				)
			));

		verify(followService, times(1)).recommendFollow(any(), any(Pageable.class));
	}


}