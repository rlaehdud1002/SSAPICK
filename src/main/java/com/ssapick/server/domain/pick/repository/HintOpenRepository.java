package com.ssapick.server.domain.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.pick.dto.HintData;
import com.ssapick.server.domain.pick.entity.HintOpen;

public interface HintOpenRepository extends JpaRepository<HintOpen, Long> {

	/**
	 * PICK ID로 힌트 오픈 조회
	 * @param pickId PICK ID
	 * @return {@link List<HintOpen>} 힌트 오픈 엔티티
	 */
	List<HintOpen> findAllByPickId(Long pickId);



}
