package com.ssapick.server.domain.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {
	List<Question> findQuestionsByQuestionCategory(QuestionCategory category);
}
