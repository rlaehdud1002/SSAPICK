package com.ssapick.server.domain.question.repository;

import java.util.List;
import java.util.Optional;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionQueryRepository {
	List<Question> findAll();

	List<Question> findRanking(Long userId);

	List<Question> findAddedQuestionsById(Long id);
}
