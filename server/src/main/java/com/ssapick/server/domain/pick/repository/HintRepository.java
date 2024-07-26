package com.ssapick.server.domain.pick.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;

public interface HintRepository extends JpaRepository<Hint, Long> {

	/**
	 *  사용자의 아이디로 힌트 조회
	 * @param userId 사용자 아이디
	 * @return {@link List<Hint>} 힌트 엔티티 리스트 존재하지 않으면 빈 리스트
	 */
	List<Hint> findAllByUserId(Long userId);

	Hint findByUserId(Long userId);

	/**
	 * 사용자의 아이디와 힌트 타입으로 힌트 조회
	 * @param userId
	 * @param hintType
	 * @return {@link Hint} 힌트 엔티티 존재하지 않으면 null
	 */
	Optional<Hint> findByUserIdAndHintType(Long userId, HintType hintType);
}
