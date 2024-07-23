package com.ssapick.server.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.core.support.RestDocsSupport;
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
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .cookie(new Cookie("refreshToken", refreshToken))
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
              )
        );
    }


}