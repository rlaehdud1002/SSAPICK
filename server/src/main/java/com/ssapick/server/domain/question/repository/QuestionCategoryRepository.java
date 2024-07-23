package com.ssapick.server.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.QuestionCategory;

public interface QuestionCategoryRepository extends JpaRepository<QuestionCategory, Long> {

}
