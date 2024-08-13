package com.ssapick.server.domain.ranking.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.ranking.service.RankingScheduler;
import com.ssapick.server.domain.user.entity.Campus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("랭킹 컨트롤러 테스트")
@WebMvcTest(value = RankingController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class RankingControllerTest extends RestDocsSupport {
    @MockBean
    private RankingScheduler rankingScheduler;

    // @Test
    // @DisplayName("전체 랭킹 조회")
    // void getAllRankings() throws Exception {
    //     // * GIVEN: 이런게 주어졌을 때
    //     Campus campus1 = Campus.createCampus("광주", (short) 1, "");
    //     Campus campus2 = Campus.createCampus("서울", (short) 2, "");
    //     Campus campus3 = Campus.createCampus("구미", (short) 3, "");
    //
    //     RankingData.UserRankingProfile userRankingProfile1 = new RankingData.UserRankingProfile("name1", (short) 11, campus1, "");
    //     RankingData.UserRankingProfile userRankingProfile2 = new RankingData.UserRankingProfile("name2", (short) 11, campus2, "");
    //     RankingData.UserRankingProfile userRankingProfile3 = new RankingData.UserRankingProfile("name3", (short) 12, campus3, "");
    //
    //     List<RankingData.UserCount> topUsers = List.of(
    //             new RankingData.UserCount(userRankingProfile1, 10L),
    //             new RankingData.UserCount(userRankingProfile2, 9L),
    //             new RankingData.UserCount(userRankingProfile3, 8L)
    //     );
    //
    //
    //     List<RankingData.QuestionUserRanking> questionUserRanking = List.of(
    //             new RankingData.QuestionUserRanking(1L, "question1", 1L, "name1", "", (short) 11, "광주", (short) 1, 10L),
    //             new RankingData.QuestionUserRanking(2L, "question2", 2L, "name2", "", (short) 12, "서울", (short) 2, 9L),
    //             new RankingData.QuestionUserRanking(3L, "question3", 3L, "name3", "", (short) 13, "구미", (short) 3, 8L)
    //     );
    //
    //
    //     RankingData.Response response = new RankingData.Response(
    //             topUsers,
    //             topUsers,
    //             topUsers,
    //             topUsers,
    //             topUsers,
    //             topUsers,
    //             questionUserRanking
    //     );
    //
    //     when(rankingScheduler.getCachedRankingData()).thenReturn(response);
    //
    //     // * WHEN: 이걸 실행하면
    //     ResultActions perform = mockMvc.perform(
    //             get("/api/v1/ranking/all"));
    //
    //     // * THEN: 이런 결과가 나와야 한다
    //     perform.andExpect(status().isOk())
    //             .andDo(restDocs.document(resource(
    //                     ResourceSnippetParameters.builder()
    //                             .tag("랭킹")
    //                             .summary("모든 랭킹 조회 API")
    //                             .description("모든 랭킹을 조회한다.")
    //                             .responseFields(response(
    //                                     fieldWithPath("data.topPickReceivers[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topPickReceivers[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topPickReceivers[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topPickReceivers[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topPickReceivers[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topPickReceivers[].count").type(JsonFieldType.NUMBER).description("픽받은 수"),                                        fieldWithPath("data.topPickSenders[].user.name").type(JsonFieldType.STRING).description("이름"),
    //
    //                                     fieldWithPath("data.topPickSenders[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topPickSenders[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topPickSenders[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topPickSenders[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topPickSenders[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topPickSenders[].count").type(JsonFieldType.NUMBER).description("픽 보낸 수"),
    //
    //                                     fieldWithPath("data.topMessageReceivers[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topMessageReceivers[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topMessageReceivers[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topMessageReceivers[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topMessageReceivers[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topMessageReceivers[].count").type(JsonFieldType.NUMBER).description("메시지 받은 수"),
    //
    //                                     fieldWithPath("data.topMessageSenders[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topMessageSenders[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topMessageSenders[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topMessageSenders[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topMessageSenders[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topMessageSenders[].count").type(JsonFieldType.NUMBER).description("메시지 보낸 수"),                                        fieldWithPath("data.topMessageSenders[].user.name").type(JsonFieldType.STRING).description("이름"),
    //
    //                                     fieldWithPath("data.topSpendPickcoUsers[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topSpendPickcoUsers[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topSpendPickcoUsers[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topSpendPickcoUsers[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topSpendPickcoUsers[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topSpendPickcoUsers[].count").type(JsonFieldType.NUMBER).description("픽코 쓴 수"),
    //
    //                                     fieldWithPath("data.topReservePickcoUsers[].user.name").type(JsonFieldType.STRING).description("이름"),
    //                                     fieldWithPath("data.topReservePickcoUsers[].user.cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.topReservePickcoUsers[].user.campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.topReservePickcoUsers[].user.section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.topReservePickcoUsers[].user.profileImage").type(JsonFieldType.STRING).description("프로필이미지"),
    //                                     fieldWithPath("data.topReservePickcoUsers[].count").type(JsonFieldType.NUMBER).description("픽코 보유량"),
    //
    //                                     fieldWithPath("data.questionUserRanking[]").type(JsonFieldType.ARRAY).description("질문별 픽을 많이 받은 유저랭킹"),
    //                                     fieldWithPath("data.questionUserRanking[].questionId").type(JsonFieldType.NUMBER).description("질문 아이디"),
    //                                     fieldWithPath("data.questionUserRanking[].questionContent").type(JsonFieldType.STRING).description("질문 내용"),
    //                                     fieldWithPath("data.questionUserRanking[].userId").type(JsonFieldType.NUMBER).description("사용자 아이디"),
    //                                     fieldWithPath("data.questionUserRanking[].name").type(JsonFieldType.STRING).description("사용자 이름"),
    //                                     fieldWithPath("data.questionUserRanking[].profileImage").type(JsonFieldType.STRING).description("사용자 프로필이미지"),
    //                                     fieldWithPath("data.questionUserRanking[].cohort").type(JsonFieldType.NUMBER).description("기수"),
    //                                     fieldWithPath("data.questionUserRanking[].campusName").type(JsonFieldType.STRING).description("캠퍼스"),
    //                                     fieldWithPath("data.questionUserRanking[].section").type(JsonFieldType.NUMBER).description("반"),
    //                                     fieldWithPath("data.questionUserRanking[].count").type(JsonFieldType.NUMBER).description("픽 받은 수")
    //                             ))
    //                             .build())
    //             ));
    // }

}
