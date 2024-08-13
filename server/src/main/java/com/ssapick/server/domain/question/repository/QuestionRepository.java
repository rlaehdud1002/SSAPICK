package com.ssapick.server.domain.question.repository;

import java.util.List;
import java.util.Optional;

import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {
	List<Question> findQuestionsByQuestionCategory(QuestionCategory category);

	List<Question> findByAuthor(User user);

	@Query("SELECT q FROM Question q JOIN FETCH q.author WHERE q.id = :id AND q.isDeleted = false")
	Optional<Question> findById(@Param("id") Long id);
}
