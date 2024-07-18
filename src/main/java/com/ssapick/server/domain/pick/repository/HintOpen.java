package com.ssapick.server.domain.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HintOpen extends JpaRepository<HintOpen, Long> {

	/**
	 * PickId로 공개 힌트 조회
	 * @param pickId
	 * @return {@link List < com.ssapick.server.domain.pick.entity.HintOpen >} 공개 힌트 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<HintOpen> findAllByPickId(Long pickId);
}
