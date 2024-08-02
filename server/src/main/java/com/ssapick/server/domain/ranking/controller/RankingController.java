package com.ssapick.server.domain.ranking.controller;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.ranking.service.RankingScheduler;
import com.ssapick.server.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/ranking")
public class RankingController {

    private final RankingScheduler rankingScheduler;

    @GetMapping("/all")
    public SuccessResponse<RankingData.Response> getAllRanking() {
        RankingData.Response response = rankingScheduler.getCachedRankingData();
        return SuccessResponse.of(response);
    }
}

