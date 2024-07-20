package com.ssapick.server.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}

