package com.ssapick.server.domain.ranking.controller;

import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.question.controller.QuestionController;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.ranking.service.RankingScheduler;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("랭킹 컨트롤러 테스트")
@WebMvcTest(value = QuestionController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class RankingControllerTest extends RestDocsSupport {
    @MockBean
    private RankingScheduler rankingScheduler;

    @Test
    @DisplayName("전체 랭킹 조회")
    void getAllRankings() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
//        User user1 = User.createUser("username1", "name1", 'M', ProviderType.GOOGLE, "provider");
//        User user2 = User.createUser("username2", "name2", 'M', ProviderType.GOOGLE, "provider");
//        User user3 = User.createUser("username3", "name3", 'M', ProviderType.GOOGLE, "provider");
//
//        Campus campus1 = Campus.createCampus("광주", (short) 1, "");
//        Campus campus2 = Campus.createCampus("서울", (short) 2, "");
//        Campus campus3 = Campus.createCampus("구미", (short) 3, "");
//
//        Profile profile1 = Profile.createProfile(user1, (short) 11, campus1);
//        Profile profile2 = Profile.createProfile(user2, (short) 11, campus2);
//        Profile profile3 = Profile.createProfile(user3, (short) 12, campus3);
//
//        // TODO : 유저에 프로필 넣기
//
//        RankingData.UserRankingProfile userRankingProfile1 = new RankingData.UserRankingProfile("name1", (short) 11, campus1, "");
//        RankingData.UserRankingProfile userRankingProfile2 = new RankingData.UserRankingProfile("name2", (short) 11, campus2, "");
//        RankingData.UserRankingProfile userRankingProfile3 = new RankingData.UserRankingProfile("name3", (short) 12, campus3, "");
//
//        List<RankingData.UserCount> topUsers = List.of(
//                new RankingData.UserCount(userRankingProfile1, 10L),
//                new RankingData.UserCount(userRankingProfile2, 9L),
//                new RankingData.UserCount(userRankingProfile3, 8L)
//        );
//
//        RankingData.Response response = new RankingData.Response(
//                topUsers,
//                topUsers,
//                topUsers,
//                topUsers,
//                topUsers
//        );
//
//
//        when(rankingScheduler.getCachedRankingData()).thenReturn(response);
//
//        // * WHEN: 이걸 실행하면
//        ResultActions perform = mockMvc.perform(
//                get("/api/v1/ranking/all"));

        // * THEN: 이런 결과가 나와야 한다


    }

}