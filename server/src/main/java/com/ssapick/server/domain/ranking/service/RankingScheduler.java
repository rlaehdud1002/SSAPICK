package com.ssapick.server.domain.ranking.service;

import com.ssapick.server.domain.ranking.dto.RankingData;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class RankingScheduler {
    private final RankingService rankingService;

    private RankingData.Response cachedRankingData;

    @PostConstruct
    public void init() {
        updateRankingData();
    }

    @Scheduled(cron = "0 */5 * * * *")  // TODO 5분마다 실행하도록 변경
    public void updateRankingData() {
        cachedRankingData = rankingService.getAllRanking();
    }

}
