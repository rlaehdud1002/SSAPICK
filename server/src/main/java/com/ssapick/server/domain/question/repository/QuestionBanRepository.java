package com.ssapick.server.domain.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;

public interface QuestionBanRepository extends JpaRepository<QuestionBan, Long> {
	@Query("SELECT qb FROM QuestionBan qb WHERE qb.user.id = :userId AND qb.question.id = :questionId")
	Optional<QuestionBan> findBanByUserIdAndQuestionId(@Param("userId") Long userId, @Param("questionId") Long questionId);

	@Query("SELECT qb.question FROM QuestionBan qb WHERE qb.user.id = :userId")
	List<Question> findQBanByUserId(@Param("userId") Long userId);
}
