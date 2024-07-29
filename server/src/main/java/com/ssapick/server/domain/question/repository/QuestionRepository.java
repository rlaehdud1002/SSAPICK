package com.ssapick.server.domain.question.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {
	List<Question> findQuestionsByCategory_Id(Long categoryId);
}
