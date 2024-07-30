package com.ssapick.server.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.service.AuthService;
import com.ssapick.server.domain.user.dto.ProfileData;
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
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.ssapick.server.core.constants.AuthConst.REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
                                        .tag("auth")
                                        .summary("로그아웃 API")
                                        .description("로그아웃을 통해 인증 토큰과 리프레시 토큰을 삭제한다.")
                                        .requestHeaders(headerWithName(HttpHeaders.AUTHORIZATION).description("발급 받은 인증 토큰"))
                                        .responseHeaders(headerWithName(HttpHeaders.SET_COOKIE).description("리프레시 토큰 삭제를 위한 쿠키"))
                                        .build()
                        )
                ));
    }

    @Test
    @DisplayName("mattermost 인증에 성공하면 성공한 유저 정보 응답")
    void successMattermostAuthenticate() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        MattermostData.Request request = new MattermostData.Request("username", "password");
        when(authService.authenticate(any(), any())).thenReturn(new ProfileData.InitialProfileInfo("name", "location", (short) 1));

        // * WHEN: 이걸 실행하면
        ResultActions action = this.mockMvc.perform(get("/api/v1/auth/mattermost-confirm")
                .contentType("application/json")
                .content(toJson(request)));

        // * THEN: 이런 결과가 나와야 한다
        action.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("mattermostAuthenticate")
                                .summary("mattermost 인증 API")
                                .description("mattermost 인증에 성공하면 성공한 유저 정보 응답")
                                .responseFields(response(
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("인증된 사용자 이름"),
                                        fieldWithPath("data.location").type(JsonFieldType.STRING).description("인증된 사용자 지역"),
                                        fieldWithPath("data.section").type(JsonFieldType.NUMBER).description("인증된 사용자 반")
                                ))
                                .build()
                )));

        verify(authService).authenticate(any(), any());
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
                                .tag("deleteUser")
                                .summary("회원 탈퇴 API")
                                .description("회원을 삭제한다.")
                                .responseFields(empty())
                                .build()
                )));

        verify(authService).deleteUser(any());
    }




}
