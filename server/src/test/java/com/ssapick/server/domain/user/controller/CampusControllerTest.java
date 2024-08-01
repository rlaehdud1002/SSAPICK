package com.ssapick.server.domain.user.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	@DisplayName("캠퍼스 생성 성공 테스트")
	void 캠퍼스_생성_성공_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		CampusData.Create create = new CampusData.Create();
		create.setName("캠퍼스 이름");
		create.setDescription("전공");
		create.setSection((short)1);

		// * WHEN: 이걸 실행하면
		ResultActions perform = this.mockMvc.perform(post("/api/v1/campus")
			.contentType(MediaType.APPLICATION_JSON)
			.content(toJson(create))
		);

		// * THEN: 이런 결과가 나와야 한다
		perform.andExpect(status().isCreated())
			.andDo(restDocs.document(resource(
					ResourceSnippetParameters.builder()
						.tag("campus")
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

	@Test
	@DisplayName("캠퍼스 생성 실패 테스트")
	void 캠퍼스_생성_실패_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때

		// * WHEN: 이걸 실행하면

		// * THEN: 이런 결과가 나와야 한다

	}
}