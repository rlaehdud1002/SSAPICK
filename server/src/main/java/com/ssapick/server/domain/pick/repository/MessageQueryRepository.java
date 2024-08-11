package com.ssapick.server.domain.pick.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.pick.entity.Message;

public interface MessageQueryRepository {

	// @Query("""
	// 	SELECT m FROM Message m
	// 	JOIN FETCH m.receiver r
	// 	JOIN FETCH m.sender s
	// 	JOIN FETCH r.profile rp
	// 	JOIN FETCH s.profile sp
	// 	JOIN FETCH r.alarm ra
	// 	JOIN FETCH s.alarm sa
	// 	JOIN FETCH rp.campus rc
	// 	JOIN FETCH sp.campus sc
	// 	WHERE r.id = :userId
	// 	ORDER BY m.id DESC
	// 	""")
	Page<Message> findReceivedMessageByUserId(Long userId, Pageable pageable);

	// @Query("""
	// 	SELECT m FROM Message m
	// 	JOIN FETCH m.receiver r
	// 	JOIN FETCH m.sender s
	// 	JOIN FETCH r.profile rp
	// 	JOIN FETCH s.profile sp
	// 	JOIN FETCH r.alarm ra
	// 	JOIN FETCH s.alarm sa
	// 	JOIN FETCH rp.campus rc
	// 	JOIN FETCH sp.campus sc
	// 	WHERE s.id = :userId
	// 	ORDER BY m.id DESC
	// 	""")
	Page<Message> findSentMessageByUserId(Long userId, Pageable pageable);
}