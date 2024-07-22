package com.ssapick.server.domain.question.repository;

import java.util.List;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionRepositoryCustom {
	List<Question> findQuestionsByCategory_Name(String questionCategory);

	List<Question> findAll();


}
