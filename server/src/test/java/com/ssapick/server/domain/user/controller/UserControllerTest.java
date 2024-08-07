package com.ssapick.server.domain.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("유저 컨트롤러 테스트")
@WebMvcTest(
        value = UserController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class UserControllerTest extends RestDocsSupport {
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("로그인한 유저 정보 조회에 성공하면 유저 정보를 반환한다")
    @WithMockUser(username = "test-user")
    void findCurrentUser() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = spy(User.createUser("user", "김싸피", 'M', ProviderType.GOOGLE, "exampleProviderId"));
        user.getProfile().updateProfile((short) 11, Campus.createCampus("광주", (short) 2, "전공"));
        user.getProfile().updateProfileImage("profileImage");
        when(user.getId()).thenReturn(1L);
        UserData.UserInfo userInfo = UserData.UserInfo.createUserInfo(user, 10, 100);

        when(userService.getUserInfo(any())).thenReturn(userInfo);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/user/me"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters
                                .builder()
                                .tag("유저")
                                .description("로그인한 유저 정보 조회")
                                .summary("로그인한 유저 정보를 조회한다.")
                                .responseFields(response(
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("식별자"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("아이디"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지"),
                                        fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data.cohort").type(JsonFieldType.NUMBER).description("기수"),
                                        fieldWithPath("data.campusName").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                                        fieldWithPath("data.section").type(JsonFieldType.NUMBER).description("반"),
                                        fieldWithPath("data.pickco").type(JsonFieldType.NUMBER).description("픽코"),
                                        fieldWithPath("data.pickCount").type(JsonFieldType.NUMBER).description("픽받은 수"),
                                        fieldWithPath("data.followingCount").type(JsonFieldType.NUMBER).description("팔로잉 수"),
                                        fieldWithPath("data.hints[]").type(JsonFieldType.ARRAY).description("내 힌트 목록")
                                ))
                                .build()
                )));
    }
    
    @Test
    @DisplayName("유저_정보_수정 테스트")
    @WithMockUser(username = "test-user")
    void 유저_정보_수정() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = spy(User.createUser("user", "김싸피", 'M', ProviderType.GOOGLE, "exampleProviderId"));
        user.getProfile().updateProfile((short) 11, Campus.createCampus("광주", (short) 2, "전공"));
        user.getProfile().updateProfileImage("profileImage");
        when(user.getId()).thenReturn(1L);

        UserData.Update update = new UserData.Update();
        update.setBirth("1998-06-12");
        update.setName("민준수");
        update.setGender('M');
        update.setCohort((short) 11);
        update.setCampusName("광주");
        update.setCampusSection((short) 2);
        update.setMbti("ENFJ");
        update.setMajor("소프트웨어학부");
        update.setResidentialArea("화정동");
        update.setInterest("로스트아크");

        // * WHEN: 이걸 실행하면
        MockMultipartFile updateFile = new MockMultipartFile(
                "update",
                "",
                "application/json",
                toJson(update).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes(StandardCharsets.UTF_8)
        );

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(
                multipart("/api/v1/user")
                        .file(updateFile)
                        .file(profileImage)
                        .content(toJson(update))
                        .contentType("multipart/form-data")
                        .with(request -> {
                            request.setMethod("PATCH");
                            return request;
                        })
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters
                                .builder()
                                .tag("유저")
                                .description("로그인한 유저 정보 수정 API")
                                .summary("로그인한 사용자 유저 정보 수정")
                                .requestFields(
                                        fieldWithPath("name").type(SimpleType.STRING).description("사용자 이름"),
                                        fieldWithPath("gender").type(SimpleType.STRING).description("성별"),
                                        fieldWithPath("cohort").type(SimpleType.NUMBER).description("기수"),
                                        fieldWithPath("campusName").type(SimpleType.STRING).description("캠퍼스 이름"),
                                        fieldWithPath("campusSection").type(SimpleType.NUMBER).description("캠퍼스 반"),
                                        fieldWithPath("mbti").type(SimpleType.STRING).description("MBTI"),
                                        fieldWithPath("major").type(SimpleType.STRING).description("전공"),
                                        fieldWithPath("birth").type(SimpleType.STRING).description("생년월일"),
                                        fieldWithPath("residentialArea").type(SimpleType.STRING).description("거주지역"),
                                        fieldWithPath("interest").type(SimpleType.STRING).description("관심사")
                                )
                                .responseFields(empty())
                                .build()
                )));
    }


    @Test
    @DisplayName("유저 검색을 하면 검색어를 포함하는 유저 프로필 데이터를 반환한다.")
    void searchUserProfile() {

    }

}
