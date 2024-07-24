package com.ssapick.server.domain.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.QuestionRegistration;

public interface QuestionRegistrationRepository extends JpaRepository<QuestionRegistration, Long> {

}
