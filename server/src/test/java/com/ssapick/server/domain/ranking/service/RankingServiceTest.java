package com.ssapick.server.domain.ranking.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.repository.PickRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("랭킹 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class RankingServiceTest extends UserSupport {

    @InjectMocks
    private RankingService rankingService;

    @Mock
    private PickRepository pickRepository;

    @Test
    @DisplayName("픽을 많이 받은 사람 TOP3 조회")
    void getAllRanking() {
        // given


    }

}