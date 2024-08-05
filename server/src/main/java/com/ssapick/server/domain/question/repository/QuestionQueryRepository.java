package com.ssapick.server.domain.question.repository;

import java.util.List;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionQueryRepository {
	List<Question> findQuestions();

	List<Question> findQRankingByUserId(Long userId);

	List<Question> findAddedQsByUserId(Long userId);

}
