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
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
        List<User> users = List.of(this.createUser("테스트 유저 1"), this.createUser("테스트 유저 2"), this.createUser("테스트 유저 3"));
        List<ProfileData.Search> profiles = users.stream().map(User::getProfile).map(ProfileData.Search::fromEntity).toList();

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
                                .tag("follow")
                                .description("팔로우한 유저 목록 조회 API")
                                .summary("로그인된 사용자가 팔로우한 유저 목록을 조회한다.")
                                .responseFields(response(
                                        fieldWithPath("data[]").description("팔로우한 유저 목록"),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("유저 성별"),
                                        fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                                        fieldWithPath("data[].campusName").type(JsonFieldType.STRING).description("캠퍼스 지역"),
                                        fieldWithPath("data[].campusSection").type(JsonFieldType.NUMBER).description("캠퍼스 반 정보"),
                                        fieldWithPath("data[].campusDescription").type(JsonFieldType.STRING).description("전공 관련 정보"),
                                        fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL")
                                ))
                                .build()
                )));
    }

    @Test
    @DisplayName("팔로우_생성_성공_테스트")
    void 팔로우_생성_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        Long userId = 1L;

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(post("/api/v1/follow/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer access-token")
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(resource(
                                ResourceSnippetParameters
                                        .builder()
                                        .tag("follow")
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
                                        .tag("follow")
                                        .description("팔로우 삭제 API")
                                        .summary("로그인된 사용자가 다른 사용자를 언팔로우한다.")
                                        .pathParameters(parameterWithName("userId").type(SimpleType.NUMBER).description("언팔로우할 사용자 식별자"))
                                        .responseFields(empty())
                                        .build()
                        ))
                );
    }
}