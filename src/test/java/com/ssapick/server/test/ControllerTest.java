package com.ssapick.server.test;

import com.ssapick.server.core.BaseControllerTest;
import com.ssapick.server.domain.TestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.awaitility.Awaitility.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("예제_테스트")
    void 예제_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
//        given()

        // * WHEN: 이걸 실행하면
        ResultActions action = this.mockMvc.perform(get("/api/test").accept(MediaType.APPLICATION_JSON));

        // * THEN: 이런 결과가 나와야 한다
        action.andExpect(status().isOk())
            .andDo(document("get-test"));
    }
}
