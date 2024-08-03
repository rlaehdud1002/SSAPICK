package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.domain.pick.entity.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

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
        LEFT JOIN FETCH ho.hint h 
        WHERE p.receiver.id = :userId
        """)
    List<Pick> findReceiverByUserId(@Param("userId") Long userId);

    /**
     * 보낸 Pick 조회
     *
     * @param userId
     * @return {@link List<Pick>} Pick 리스트 반환 (존재하지 않으면, 빈 리스트 반환)
     */
    @Query("SELECT p FROM Pick p JOIN FETCH p.sender JOIN FETCH p.question JOIN FETCH p.question.questionCategory WHERE p.sender.id = :userId")
    List<Pick> findSenderByUserId(@Param("userId") Long userId);

    /**
     * 메시지를 보냈을 때 픽의 메시지 전송 여부 true로 변경하기
     *
     * @param pickId
     */
    @Modifying
    @Query("UPDATE Pick p SET p.isMessageSend = true WHERE p.id = :pickId")
    void updateMessageSendTrue(@Param("pickId") Long pickId);


    @Query("SELECT p FROM Pick p JOIN FETCH p.hintOpens WHERE p.id = :pickId")
    Optional<Pick> findPickWithHintsById(@Param("pickId") Long pickId);

    @Query("""
            SELECT p FROM Pick p 
            JOIN FETCH p.receiver r 
            JOIN FETCH p.sender s 
            JOIN FETCH p.question q
            JOIN FETCH r.profile rp 
            JOIN FETCH s.profile sp 
            JOIN FETCH rp.campus rc 
            JOIN FETCH sp.campus sc
            """)
    List<Pick> findAllWithReceiverAndSenderAndQuestion();
}
