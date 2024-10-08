package com.ssapick.server.domain.pick.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssapick.server.domain.pick.entity.Pick;

public interface PickRepository extends JpaRepository<Pick, Long> {

	/**
	 * 받은 Pick 조회
	 *
	 * @param userId
	 * @retrun {@link List<Pick>} Pick 리스트 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	@Query("""
		SELECT p FROM Pick p 
		JOIN FETCH p.receiver 
		JOIN FETCH p.question 
		JOIN FETCH p.question.questionCategory 
		LEFT JOIN FETCH p.hintOpens ho 
		WHERE p.receiver.id = :userId
		ORDER BY p.id DESC
		""")
	List<Pick> findReceiverByUserId(@Param("userId") Long userId);

	@Query("""
		SELECT p FROM Pick p 
		JOIN FETCH p.sender
		JOIN FETCH p.receiver 
		JOIN FETCH p.receiver.alarm
		JOIN FETCH p.sender.alarm
		JOIN FETCH p.question 
		JOIN FETCH p.question.questionCategory 
		LEFT JOIN FETCH p.hintOpens ho 
		WHERE p.id IN :ids
		ORDER BY p.id DESC 
		""")
	List<Pick> findAllByIdsWithDetails(@Param("ids") List<Long> ids, Pageable pageable);

	@Query("""
		SELECT p.id  
		FROM Pick p 
		WHERE p.receiver.id = :userId""")
	List<Long> findPickIdsByReceiverId(@Param("userId") Long userId);

	/**
	 * 메시지를 보냈을 때 픽의 메시지 전송 여부 true로 변경하기
	 *
	 * @param pickId
	 */
	@Modifying
	@Query("UPDATE Pick p SET p.isMessageSend = true WHERE p.id = :pickId")
	void updateMessageSendTrue(@Param("pickId") Long pickId);

	@Query("SELECT p FROM Pick p LEFT JOIN FETCH p.hintOpens WHERE p.id = :pickId")
	Optional<Pick> findPickWithHintsById(@Param("pickId") Long pickId);

	@Query("""
		SELECT p FROM Pick p 
		JOIN FETCH p.receiver r 
		JOIN FETCH p.sender s 
		JOIN FETCH p.question q
		JOIN FETCH r.profile rp 
		JOIN FETCH s.profile sp 
		JOIN FETCH r.alarm ra
		JOIN FETCH s.alarm sa
		JOIN FETCH rp.campus rc 
		JOIN FETCH sp.campus sc
		""")
	List<Pick> findAllWithReceiverAndSenderAndQuestion();

	@Query("SELECT p FROM Pick p WHERE p.receiver.id = :receiverId AND p.alarm = true")
	Optional<Pick> findByReceiverIdWithAlarm(@Param("receiverId") Long receiverId);

	@Query("SELECT p FROM Pick p JOIN FETCH p.sender JOIN FETCH p.receiver JOIN FETCH p.question WHERE p.id = :pickId")
	Optional<Pick> findByIdWithSender(Long pickId);

	boolean existsByQuestionId(Long id);

	@Query("SELECT p FROM Pick p LEFT JOIN FETCH p.question LEFT JOIN FETCH p.sender LEFT JOIN FETCH p.receiver WHERE p.isAlarmSent = false")
	List<Pick> findByAlarmSentIsFalse();
}
