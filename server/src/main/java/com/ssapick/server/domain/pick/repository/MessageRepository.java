package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.domain.pick.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageQueryRepository {

    /**
     * 메세지 전체 조회
     *
     * @return {@link List<Message>} 메시지 반환 (존재하지 않으면, 빈 리스트 반환)
     */
    List<Message> findAll();

    @Query("""
            SELECT m FROM Message m 
            JOIN FETCH m.receiver r 
            JOIN FETCH m.sender s 
            JOIN FETCH r.profile rp 
            JOIN FETCH s.profile sp 
            JOIN FETCH r.alarm ra
            JOIN FETCH s.alarm sa
            JOIN FETCH rp.campus rc 
            JOIN FETCH sp.campus sc
            """)
    List<Message> findAllWithReceiverAndSender();

}
