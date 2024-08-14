package com.ssapick.server.domain.question.repository;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {
	List<Question> findQuestionsByQuestionCategory(QuestionCategory category);

	@Query("""
		SELECT q 
		FROM Question q
		JOIN FETCH q.questionCategory
		WHERE q.author = :user
		AND q.isDeleted = false
""")
	List<Question> findByAuthor(User user);

	@Query("SELECT q FROM Question q LEFT JOIN FETCH q.author LEFT JOIN fETCH q.picks WHERE q.id = :id AND q.isDeleted = false")
	Optional<Question> findById(@Param("id") Long id);
}
