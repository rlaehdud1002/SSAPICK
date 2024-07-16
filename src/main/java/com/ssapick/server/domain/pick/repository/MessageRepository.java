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
	 * PickId로 메시지 조회
	 * @param pickId
	 * @return {@link List<Message>} 메시지 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<Message> findAllByPickId(Long pickId);

}
