package com.ssapick.server.domain.pick.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.pick.entity.Pick;

public interface PickRepository extends JpaRepository<Pick, Long> {

	/**
	 * 보낸 사용자아이디로 Pick을 조회
	 * @param fromUserId
	 * @retrun {@link List<Pick>} Pick 리스트 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<Pick> findAllByFromUserId(Long fromUserId);

	/**
	 * 받은 사용자아이디로 Pick을 조회
	 * @param toUserId
	 * @return {@link List<Pick>} Pick 리스트 반환 (존재하지 않으면, 빈 리스트 반환)
	 */
	List<Pick> findAllByToUserId(Long toUserId);

	@Query("SELECT p FROM Pick p JOIN FETCH p.hintOpens WHERE p.id = :pickId")
	Optional<Pick> findPickWithHintsById(Long pickId);

}
