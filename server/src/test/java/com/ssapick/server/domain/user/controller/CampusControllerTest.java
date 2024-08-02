package com.ssapick.server.domain.user.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.user.dto.CampusData;
import com.ssapick.server.domain.user.service.CampusService;

import java.util.List;

@DisplayName("캠퍼스 컨트롤러 테스트")
@WebMvcTest(
        value = CampusController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class CampusControllerTest extends RestDocsSupport {
    @MockBean
    private CampusService campusService;

    @Test
    @DisplayName("캠퍼스에 해당된 유저 정보를 캠퍼스 아이디로 조회")
    void 캠퍼스에_해당된_반_정보_아이디로_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        List<User> users = List.of(
                this.createUser("user1"),
                this.createUser("user2"),
                this.createUser("user3"),
                this.createUser("user4"),
                this.createUser("user5"),
                this.createUser("user6")
        );

        List<ProfileData.Search> response = users.stream().map(
                user -> ProfileData.Search.fromEntity(user.getProfile())
        ).toList();

        when(campusService.searchProfileByCampusId(any())).thenReturn(response);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/campus/{campusId}", 1));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("캠퍼스 조회 API")
                                .description("특정 캠퍼스에 해당된 유저 정보를 조회한다.")
                                .pathParameters(
                                        parameterWithName("campusId").description("조회할 캠퍼스 ID")
                                )
                                .responseFields(response(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회된 캠퍼스 반에 속한 사용자 리스트"),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("사용자 ID"),
                                        fieldWithPath("data[].nickname").type(JsonFieldType.STRING).description("사용자 닉네임"),
                                        fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("성별"),
                                        fieldWithPath("data[].campusName").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                                        fieldWithPath("data[].campusSection").type(JsonFieldType.NUMBER).description("반"),
                                        fieldWithPath("data[].campusDescription").type(JsonFieldType.STRING).description("반 설명"),
                                        fieldWithPath("data[].profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
                                ))
                                .build()
                )));

    }


    @Test
    @DisplayName("캠퍼스 이름으로 해당된 반 정보들을 조회")
    void 캠퍼스에_해당된_반_정보_이름으로_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        List<Campus> campus = List.of(
                Campus.createCampus("광주", (short) 1, "비전공"),
                Campus.createCampus("광주", (short) 2, "비전공"),
                Campus.createCampus("광주", (short) 3, "비전공"),
                Campus.createCampus("광주", (short) 4, "전공"),
                Campus.createCampus("광주", (short) 5, "전공"),
                Campus.createCampus("광주", (short) 6, "전공")
        );

        List<CampusData.SearchResponse> response = campus.stream().map(
                CampusData.SearchResponse::fromEntity
        ).toList();

        when(campusService.searchCampusByName("광주")).thenReturn(response);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/campus/search/name")
                .param("name", "광주"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("캠퍼스 조회 API")
                                .description("특정 캠퍼스에 해당된 반 정보를 캠퍼스 이름으로 조회한다.")
                                .queryParameters(
                                        parameterWithName("name").description("조회할 캠퍼스 이름")
                                )
                                .responseFields(response(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회된 캠퍼스 반 리스트"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                                        fieldWithPath("data[].section").type(JsonFieldType.NUMBER).description("반"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("반 설명")
                                ))
                                .build()
                )));
    }


    @Test
    @DisplayName("반 설명으로 으로 해당된 반 정보들을 조회")
    void 캠퍼스에_해당된_반_설명으로_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        List<Campus> campus = List.of(
                Campus.createCampus("광주", (short) 1, "비전공"),
                Campus.createCampus("광주", (short) 2, "비전공"),
                Campus.createCampus("광주", (short) 3, "비전공")
        );

        List<CampusData.SearchResponse> response = campus.stream().map(
                CampusData.SearchResponse::fromEntity
        ).toList();

        when(campusService.searchCampusByDescription("비전공")).thenReturn(response);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/campus/search/description")
                .param("description", "비전공"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("캠퍼스 반 조회 API")
                                .description("특정 캠퍼스에 해당된 반 설명으로 반 정보를 조회한다.")
                                .queryParameters(
                                        parameterWithName("description").description("조회할 반 설명")
                                )
                                .responseFields(response(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("조회된 캠퍼스 반 리스트"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                                        fieldWithPath("data[].section").type(JsonFieldType.NUMBER).description("반"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("반 설명")
                                ))
                                .build()
                )));
    }

    @Test
    @DisplayName("캠퍼스 생성 성공 테스트")
    void 캠퍼스_생성_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        CampusData.Create create = new CampusData.Create();
        create.setName("캠퍼스 이름");
        create.setDescription("전공");
        create.setSection((short) 1);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(post("/api/v1/campus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(create))
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(resource(
                                ResourceSnippetParameters.builder()
                                        .tag("유저")
                                        .summary("캠퍼스 생성 API")
                                        .description("전국 캠퍼스 정보를 생성한다.")
                                        .requestFields(
                                                fieldWithPath("name").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                                                fieldWithPath("section").type(JsonFieldType.NUMBER).description("캠퍼스 반"),
                                                fieldWithPath("description").type(JsonFieldType.STRING).description("반 전공")
                                        )
                                        .responseFields(empty())
                                        .build()
                        )
                ));
        verify(campusService).createCampus(create);
    }

}