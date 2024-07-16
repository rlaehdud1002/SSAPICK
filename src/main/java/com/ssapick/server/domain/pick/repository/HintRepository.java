package com.ssapick.server.domain.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.pick.entity.Hint;

public interface HintRepository extends JpaRepository<Hint, Long> {

	/**
	 * UserId로 힌트 조회 (힌트 타입으로 정렬)
	 * @param userId
	 * @return {@link List<Hint>} 힌트 리스트 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<Hint> findAllByUserIdOrderByHintType(Long userId);


}
