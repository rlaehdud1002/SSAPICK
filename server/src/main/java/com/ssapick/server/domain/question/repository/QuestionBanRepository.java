package com.ssapick.server.domain.question.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;

public interface QuestionBanRepository extends JpaRepository<QuestionBan, Long> {
	Optional<QuestionBan> findQByUser_IdAndQuestion_Id(Long userId, Long questionId);

	@Query("SELECT qb FROM QuestionBan qb WHERE qb.user.id = :userId AND qb.question.id = :questionId")
	Optional<QuestionBan> findBanByUserIdAndQuestionId(Long userId, Long questionId);


	@Query("SELECT qb.question FROM QuestionBan qb JOIN FETCH qb.question  WHERE qb.user.id = :userId")
	List<Question> findQuestionBanByUser_Id(Long userId);
}
