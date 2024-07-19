package com.ssapick.server.test;

import com.ssapick.server.core.support.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.awaitility.Awaitility.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTest extends RestDocsSupport {

    @MockBean
    private MockMvc mockMvc;

    @Test
    @DisplayName("예제_테스트")
    void 예제_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        given();

        // * WHEN: 이걸 실행하면
        ResultActions action = this.mockMvc.perform(get("/api/test").accept(MediaType.APPLICATION_JSON));

        // * THEN: 이런 결과가 나와야 한다
        action.andExpect(status().isOk())
            .andDo(restDocs.document(
                responseFields(
                        response(fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"))
                )
            )
        );
    }
}
