package com.ssapick.server.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.ssapick.server.core.constants.AuthConst.REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("인증 컨트롤러 테스트")
@WebMvcTest(
	value = AuthController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
	}
)
@Import({JwtProperties.class})
class AuthControllerTest extends RestDocsSupport {
	@MockBean
	private AuthService authService;

	@Test
	@DisplayName("로그아웃 정상 테스트")
	void 로그아웃_정상_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";

		// * WHEN: 이걸 실행하면
		ResultActions action = this.mockMvc.perform(post("/api/v1/auth/sign-out")
			.header("Authorization", "Bearer " + accessToken)
			.cookie(new Cookie(REFRESH_TOKEN, refreshToken))
		);

		// * THEN: 이런 결과가 나와야 한다
		action.andExpect(status().isNoContent())
			.andDo(restDocs.document(resource(
					ResourceSnippetParameters.builder()
						.tag("인증")
						.summary("로그아웃 API")
						.description("로그아웃을 통해 인증 토큰과 리프레시 토큰을 삭제한다.")
						.requestHeaders(headerWithName(HttpHeaders.AUTHORIZATION).description("발급 받은 인증 토큰"))
						.responseHeaders(headerWithName(HttpHeaders.SET_COOKIE).description("리프레시 토큰 삭제를 위한 쿠키"))
						.build()
				)
			));
	}

	@Test
	@DisplayName("mattermost 인증에 성공한 유저 정보 응답")
	@WithMockUser(username = "test-user")
	void checkMattermostAuthenticate() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		// * WHEN: 이걸 실행하면
		ResultActions action = this.mockMvc.perform(get("/api/v1/auth/mattermost-confirm")
				.contentType("application/json"));

		// * THEN: 이런 결과가 나와야 한다
		action.andExpect(status().isOk())
				.andDo(restDocs.document(resource(
						ResourceSnippetParameters.builder()
								.tag("인증")
								.summary("메타모스트 인증 API")
								.description("현재 로그인된 사용자가 메타모스트 인증을 받았는지 확인한다.")
								.responseFields(response(
										fieldWithPath("data.authenticated").type(JsonFieldType.BOOLEAN).description("사용자 메타모스트 인증 여부")
								))
								.build()
				)));

		verify(authService).isUserAuthenticated(argThat(user -> user.getUsername().equals("test-user")));
	}

	@Test
	@DisplayName("mattermost 인증에 성공하면 성공한 유저 정보 응답")
	@WithMockUser(username = "test-user")
	void successMattermostAuthenticate() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		MattermostData.Request request = new MattermostData.Request("username", "password");

		// * WHEN: 이걸 실행하면
		ResultActions action = this.mockMvc.perform(post("/api/v1/auth/mattermost-confirm")
			.contentType("application/json")
			.content(toJson(request)));

		// * THEN: 이런 결과가 나와야 한다
		action.andExpect(status().isOk())
				.andDo(restDocs.document(resource(
						ResourceSnippetParameters.builder()
								.tag("인증")
								.summary("메타모스트 인증 API")
								.description("현재 로그인된 사용자의 정보를 메타모스트 정보로 수정한다.")
								.responseFields(empty())
								.build()
				)));

		verify(authService).authenticate(
				argThat(user -> user.getUsername().equals("test-user")),
				argThat(original -> original.getLoginId().equals(request.getLoginId()) && original.getPassword().equals(request.getPassword()))
		);
	}

	@Test
	@DisplayName("회원 삭제에 성공하면 성공 응답을 반환한다.")
	void successDeleteUser() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		// * WHEN: 이걸 실행하면
		ResultActions action = this.mockMvc.perform(delete("/api/v1/auth"));

		// * THEN: 이런 결과가 나와야 한다
		action.andExpect(status().isNoContent())
			.andDo(restDocs.document(resource(
				ResourceSnippetParameters.builder()
					.tag("인증")
					.summary("회원 탈퇴 API")
					.description("회원을 삭제한다.")
					.responseFields(empty())
					.build()
			)));

		verify(authService).deleteUser(any());
	}

}
