package com.ssapick.server.domain.user.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.PickcoLogType;
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
import java.time.LocalDateTime;
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
    @DisplayName("로그인한 유저의 픽코를 반환한다")
    @WithMockUser(username = "test-user")
    void findCurrentUserPickco() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = spy(User.createUser("user", "김싸피", 'M', ProviderType.GOOGLE, "exampleProviderId"));
        user.getProfile().updateProfile((short) 11, Campus.createCampus("광주", (short) 2, "전공"));
        user.getProfile().updateProfileImage("profileImage");
        when(user.getId()).thenReturn(1L);
        UserData.Pickco pickco = new UserData.Pickco(100);
        when(userService.getPickco(any())).thenReturn(pickco);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/user/pickco"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters
                                .builder()
                                .tag("유저")
                                .description("로그인한 유저의 픽코 조회 API")
                                .summary("로그인한 유저 정보를 조회한다.")
                                .responseFields(response(
                                        fieldWithPath("data.pickco").type(JsonFieldType.NUMBER).description("식별자")
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
                this.createUser("김싸일"),
                this.createUser("김싸이"),
                this.createUser("김싸삼"),
                this.createUser("김싸사")
        );

        List<ProfileData.Friend> searches = users.stream().map(ProfileData.Friend::fromEntity).toList();


        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfileData.Friend> pickPage = new PageImpl<>(searches, pageable, searches.size());

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
                                                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("유저 닉네임"),
                                                fieldWithPath("data.content[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
                                                fieldWithPath("data.content[].cohort").type(JsonFieldType.NUMBER).description("기수 정보"),
                                                fieldWithPath("data.content[].campusSection").type(JsonFieldType.NUMBER).description("캠퍼스 반 정보"),
                                                fieldWithPath("data.content[].follow").type(JsonFieldType.BOOLEAN).description("유저 팔로우 여부"),
                                                fieldWithPath("data.content[].sameCampus").type(JsonFieldType.BOOLEAN).description("유저 동일 캠퍼스 여부"),
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

    @Test
    @DisplayName("로그인한 유저의 픽코 로그를 반환한다.")
    void getPickcoLogs() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser("user");

        List<PickcoLog> pickcoLogs = List.of(
                createPickcoLog(user, PickcoLogType.SIGN_UP, 300, 300),
                createPickcoLog(user, PickcoLogType.PICK, 30, 330),
                createPickcoLog(user, PickcoLogType.HINT_OPEN, -100, 230),
                createPickcoLog(user, PickcoLogType.MESSAGE, -20, 210)
        );

        List<UserData.PickcoLogResponse> pickcoLogResponses = pickcoLogs.stream()
                .map(UserData.PickcoLogResponse::fromEntity)
                .toList();

        when(userService.getPickcoLogs(any(), any())).thenReturn(new PageImpl<>(pickcoLogResponses, PageRequest.of(0, 10), pickcoLogResponses.size()));

        ResultActions perform = this.mockMvc.perform(get("/api/v1/user/pickco-log")
                .param("page", "0")
                .param("size", "10"));

        perform.andExpect(status().isOk())
                .andDo(this.restDocs.document(
                        resource(
                                ResourceSnippetParameters.builder()
                                        .tag("유저")
                                        .summary("픽코 로그 조회 API")
                                        .description("로그인한 사용자의 픽코 로그를 조회한다.")
                                        .queryParameters(
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

                                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("픽코 로그 아이디"),
                                                fieldWithPath("data.content[].pickcoLogType").type(JsonFieldType.STRING).description("픽코 로그 타입"),
                                                fieldWithPath("data.content[].change").type(JsonFieldType.NUMBER).description("사용량/지급량"),
                                                fieldWithPath("data.content[].remain").type(JsonFieldType.NUMBER).description("남은 픽코"),
                                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("픽코 로그 생성일"),
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

    private PickcoLog createPickcoLog(User user, PickcoLogType pickcoLogType, int change, int remain) {
        PickcoLog pickcoLog = spy(PickcoLog.createPickcoLog(user, pickcoLogType, change, remain));
        when(pickcoLog.getId()).thenReturn(1L);
        when(pickcoLog.getCreatedAt()).thenReturn(LocalDateTime.now());
        return pickcoLog;
    }

}
