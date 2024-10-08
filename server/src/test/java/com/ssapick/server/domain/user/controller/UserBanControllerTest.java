package com.ssapick.server.domain.user.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.UserBanservice;

@DisplayName("유저 차단 컨트롤러 테스트")
@WebMvcTest(
	value = UserBanController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
	}
)
class UserBanControllerTest extends RestDocsSupport {
	@MockBean
	private UserBanservice userBanService;

	@Test
	@DisplayName("유저를_차단하는_테스트")
	void 유저를_차단하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을
		Long banUserId = 2L;


		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/user-ban/{userId}", banUserId)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer access-token")
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(restDocs.document(resource(
				ResourceSnippetParameters
					.builder()
					.tag("유저 차단")
					.description("유저를 차단하는 API")
					.summary("유저를 차단한다.")
					.pathParameters(parameterWithName("userId").type(SimpleType.NUMBER).description("차단할 유저의 ID"))
					.responseFields(empty())
					.build()
			))
		);
	}


	@Test
	@DisplayName("차단된_유저를_차단해제하는_테스트")
	void 차단된_유저를_차단해제하는_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		Long unbanUserId = 2L;

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(delete("/api/v1/user-ban/{userId}", unbanUserId)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer access-token")
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isNoContent())
			.andDo(restDocs.document(resource(
				ResourceSnippetParameters
					.builder()
					.tag("유저 차단")
					.description("유저 차단 해제 API")
					.summary("유저 차단을 해제한다.")
					.pathParameters(parameterWithName("userId").type(SimpleType.NUMBER).description("차단 해제할 유저의 ID"))
					.responseFields(empty())
					.build()
			))
		);
	}


	@Test
	@DisplayName("차단된_유저_목록을_조회하는_테스트")
	@WithMockUser(username = "test-user")
	void 차단된_유저_목록을_조회하는_테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
		List<User> users = List.of(this.createUser("차단된 유저 1"), this.createUser("차단된 유저 2"),
			this.createUser("차단된 유저 3"));

		List<ProfileData.Search> profiles = users.stream()
			.map(User::getProfile)
			.map(ProfileData.Search::fromEntity)
			.toList();

		when(userBanService.searchBanUsers(any())).thenReturn(profiles);

	    // * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(get("/api/v1/user-ban")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer access-token")
		);

	    // * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isOk())
			.andDo(restDocs.document(resource(
				ResourceSnippetParameters
					.builder()
					.tag("유저 차단")
					.description("차단된 유저 목록 조회 API")
					.summary("로그인된 사용자가 차단한 유저 목록을 조회한다.")
					.responseFields(response(
						fieldWithPath("data[]").description("팔로우한 유저 목록"),
						fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
						fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("유저 성별"),
						fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
						fieldWithPath("data[].campusName").type(JsonFieldType.STRING).description("캠퍼스 지역"),
						fieldWithPath("data[].campusSection").type(JsonFieldType.NUMBER).description("캠퍼스 반 정보"),
						fieldWithPath("data[].campusDescription").type(JsonFieldType.STRING).description("전공 관련 정보"),
						fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
						fieldWithPath("data[].cohort").type(JsonFieldType.NUMBER).description("기수 정보")
					))
					.build()
			)));
	}
}