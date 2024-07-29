package com.ssapick.server.domain.question.service;

import com.ssapick.server.domain.question.dto.PerspectiveData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentAnalyzeService {
    @Value("${spring.comment-analyzer.api-key}")
    private String apiKey;

    private final CommentAnalyzer commentAnalyzer;

    public boolean isCommentOffensive(String content) {
        try {
            // Feign 클라이언트를 통해 API 호출
            PerspectiveData.Response response = commentAnalyzer.analyzeComment("Bearer " + apiKey, new PerspectiveData.Request());

            // 응답에서 점수 추출
            double insultScore = response.getAttributeScores()
                    .get("INSULT")
                    .getSummaryScore()
                    .getValue();

            double profanityScore = response.getAttributeScores()
                    .get("PROFANITY")
                    .getSummaryScore()
                    .getValue();

            // 공격성 여부 판단 (예: 0.7 이상이면 공격적이라고 판단)
            double overallScore = (insultScore + profanityScore) / 2;
            return overallScore > 0.7;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
