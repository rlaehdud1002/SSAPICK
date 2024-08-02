package com.ssapick.server.domain.attendance.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.attendance.dto.AttendanceData;
import com.ssapick.server.domain.attendance.service.AttendanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("출석 컨트롤러 테스트")
@WebMvcTest(
        value = AttendanceController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
public class AttendanceControllerTest extends RestDocsSupport {
    @MockBean
    private AttendanceService attendanceService;

    @Test
    @DisplayName("사용자의 출석상태를 조회한다.")
    public void getUserAttendanceStatus() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        AttendanceData.Status status = AttendanceData.CreateStatus(1, true);
        when(attendanceService.getUserAttendanceStatus(any())).thenReturn(status);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/attendance")
                .contentType(MediaType.APPLICATION_JSON));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("출석상태 조회 API")
                                .description("사용자의 출석상태를 조회한다")
                                .responseFields(response(
                                        fieldWithPath("data.streak").type(JsonFieldType.NUMBER).description("연속 출석 일수"),
                                        fieldWithPath("data.todayChecked").type(JsonFieldType.BOOLEAN).description("오늘 출석 여부")
                                ))
                                .build()
                )));
    }

    @Test
    @DisplayName("출석에 성공하면 성공 응답을 반환한다")
    public void attendanceSuccess() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(post("/api/v1/attendance")
                .contentType(MediaType.APPLICATION_JSON));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("출석 생성 API")
                                .description("출석을 생성한다.")
                                .responseFields(empty())
                                .build()
                )));

        verify(attendanceService).checkIn(any());
    }


    @Test
    @DisplayName("이미 출석을 해 출석에 실패하면 실패 응답을 반환한다")
    public void attendanceFail() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        when(attendanceService.checkIn(any())).thenThrow(new BaseException(ErrorCode.ALREADY_CHECKIN_TODAY));

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(post("/api/v1/attendance")
                .contentType(MediaType.APPLICATION_JSON));
        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isBadRequest())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("유저")
                                .summary("출석 생성 API")
                                .description("출석을 생성한다.")
                                .responseFields(response(
                                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                        fieldWithPath("errors").type(JsonFieldType.NULL).description("에러 메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                                ))
                                .build()
                )));

        verify(attendanceService).checkIn(any());
    }

}
