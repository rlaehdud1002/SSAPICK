package com.ssapick.server.domain.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.PickData;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    void searchUserProfile() throws Exception {
        String keyword = "김싸";

        List<User> users = List.of(
                createUser("김싸일"),
                createUser("김싸이"),
                createUser("김싸삼"),
                createUser("김싸사")
        );

        List<UserData.Search> searches = users.stream().map(UserData.Search::fromEntity).toList();


        Pageable pageable = PageRequest.of(0, 10);
        Page<UserData.Search> pickPage = new PageImpl<>(searches, pageable, searches.size());

        when(userService.getUserByKeyword(any(), eq(keyword), eq(pageable))).thenReturn(pickPage);

        ResultActions perform = this.mockMvc.perform(get("/api/v1/user/search")
                .param("q", keyword)
                .param("page", "0")
                .param("size", "10"));

        perform.andExpect(status().isOk())
                .andDo(this.restDocs.document(
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("유저")
                                        .summary("친구로 추가할 사용자 목록 조회 API")
                                        .description("친구목록에 추가할 사용자를 검색합니다.")
                                        .queryParameters(
                                                parameterWithName("q").description("검색어").optional(),
                                                parameterWithName("page").description("페이지 번호").optional(),
                                                parameterWithName("size").description("페이지 크기").optional()
                                        )
                                        .responseFields(
                                                fieldWithPath("success").description("성공 여부"),
                                                fieldWithPath("status").description("HTTP 상태 코드"),
                                                fieldWithPath("message").description("응답 메시지"),
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
                                                fieldWithPath("data.pageable.unpaged").description("비페이지 여부")
                                                        .type(JsonFieldType.BOOLEAN)
                                                        .optional(),
                                                fieldWithPath("data.pageable.paged").description("페이지 여부")
                                                        .type(JsonFieldType.BOOLEAN)
                                                        .optional(),
                                                fieldWithPath("data.numberOfElements").description("페이지 내 요소 수")
                                                        .type(JsonFieldType.NUMBER)
                                                        .optional(),
                                                fieldWithPath("data.content[]").description("유저 데이터 목록").type(JsonFieldType.ARRAY).optional(),
                                                fieldWithPath("data.content[].name").description("이름").type(JsonFieldType.STRING).optional(),
                                                fieldWithPath("data.content[].cohort").description("기수").type(JsonFieldType.NUMBER).optional(),
                                                fieldWithPath("data.content[].campusSection").description("반").type(JsonFieldType.NUMBER).optional(),
                                                fieldWithPath("data.content[].profileImage").description("프로필 이미지").type(JsonFieldType.STRING).optional(),
                                                fieldWithPath("data.pageable.pageNumber").description("페이지번호").type(JsonFieldType.NUMBER).optional(),
                                                fieldWithPath("data.pageable.pageSize").description("페이지 사이즈").type(JsonFieldType.NUMBER).optional(),
                                                fieldWithPath("data.pageable.sort").description("정렬").type(JsonFieldType.OBJECT).optional(),
                                                fieldWithPath("data.pageable.sort.empty").description("정렬").type(JsonFieldType.BOOLEAN).optional(),
                                                fieldWithPath("data.pageable.sort.sorted").description("정렬").type(JsonFieldType.BOOLEAN).optional(),
                                                fieldWithPath("data.pageable.sort.unsorted").description("정렬").type(JsonFieldType.BOOLEAN).optional(),
                                                fieldWithPath("data.pageable.offset").description("오프셋").type(JsonFieldType.NUMBER).optional(),
                                                fieldWithPath("data.empty").description("현재 페이지가 비어 있는지 여부").type(JsonFieldType.BOOLEAN).optional()
                                        )
                                        .build()
                        )));
    }

    public User createUser(String username) {
        User user = spy(User.createUser("user", username, 'M', ProviderType.GOOGLE, "exampleProviderId"));
        user.getProfile().updateCampus(Campus.createCampus("광주", (short) 2, "전공"));
        return user;
    }

}
