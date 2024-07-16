package com.ssapick.server.domain.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.pick.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	/**
	 * 메세지 전체 조회
	 * @return {@link List<Message>} 메시지 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<Message> findAll();

	/**
	 * 유저 아이디로 받은 메시지 조회
	 * @param userId 사용자 아이디
	 * @return {@link List<Message>} 메시지 엔티티 리스트 존재하지 않으면 빈 리스트
	 */
	List<Message> findAllByfromUserId(Long userId);

	/**
	 * 유저 아이디로 보낸 메시지 조회
	 * @param userId 사용자 아이디
	 * @return {@link List<Message>} 메시지 엔티티 리스트 존재하지 않으면 빈 리스트
	 */
	List<Message> findAllBytoUserId(Long userId);
}
