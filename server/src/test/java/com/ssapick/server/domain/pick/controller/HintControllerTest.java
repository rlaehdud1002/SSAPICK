package com.ssapick.server.domain.pick.controller;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.pick.service.HintService;

@DisplayName("힌트 컨트롤러 테스트")
@WebMvcTest(value = HintController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
	}
)
public class HintControllerTest extends RestDocsSupport {
	@MockBean
	private HintService hintService;

	// @Test
	// @DisplayName("픽 아이디를 받아서 랜덤한 힌트를 반환한다")
	// public void getRandomHintByPickId() throws Exception {
	//     // * GIVEN: 이런게 주어졌을 때
	//     when(hintService.getRandomHintByPickId(1L)).thenReturn("힌트");
	//
	//     // * WHEN: 이걸 실행하면
	//     ResultActions perform = this.mockMvc.perform(get("/api/v1/hint/random")
	//             .contentType("application/json")
	//             .param("pickId", "1"));
	//
	//     // * THEN: 이런 결과가 나와야 한다
	//     perform.andExpect(status().isOk())
	//             .andDo(this.restDocs.document(resource(
	//                     ResourceSnippetParameters.builder()
	//                             .tag("힌트")
	//                             .summary("힌트 열기 API")
	//                             .description("자신이 받은 픽의 랜덤한 힌트를 얻는다.")
	//                             .responseFields(response(
	//                                     fieldWithPath("data").type(JsonFieldType.STRING).description("힌트 내용")
	//                             ))
	//                             .build()
	//             )));
	// }
}
