package com.ssapick.server.domain.user.controller;

import com.ssapick.server.core.response.BaseResponse;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.user.dto.CampusData;
import com.ssapick.server.domain.user.service.CampusService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CampusController.class)
class CampusControllerTest extends RestDocsSupport {
    @MockBean
    private CampusService campusService;

    @Test
    @DisplayName("캠퍼스 생성 성공 테스트")
    void 캠퍼스_생성_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        CampusData.Create create = new CampusData.Create();
        create.setName("캠퍼스 이름");
        create.setSection((short) 1);
        create.setDescription("전공");

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(post("/api/v1/campus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(create))
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(
                    requestFields(
                            fieldWithPath("name").type(JsonFieldType.STRING).description("캠퍼스 이름"),
                            fieldWithPath("section").type(JsonFieldType.NUMBER).description("캠퍼스 반"),
                            fieldWithPath("description").type(JsonFieldType.STRING).description("반 전공")
                    ),
                    responseFields(empty())
        ));
    }

    @Test
    @DisplayName("캠퍼스_생성_실패_테스트")
    void 캠퍼스_생성_실패_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때


        // * WHEN: 이걸 실행하면

        // * THEN: 이런 결과가 나와야 한다

    }
}