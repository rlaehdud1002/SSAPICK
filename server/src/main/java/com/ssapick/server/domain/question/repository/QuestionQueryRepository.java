package com.ssapick.server.domain.question.repository;

import java.util.List;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionQueryRepository {
	List<Question> findAll();

	List<Question> findRanking(Long userId);

	List<Question> findAddedQuestionsByUser_Id(Long userId);

}
