package com.ssapick.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserQueryRepository {
    /**
     * 사용자 이름으로 사용자 조회
     * @param username 사용자 이름
     * @return {@link User} 사용자 엔티티 (존재하지 않으면, {@link Optional#empty()} 반환)
     */
    Optional<User> findByUsername(String username);

    /**
     * 사용자 ID로 사용자 조회 (프로필 정보 포함)
     * @param userId 사용자 ID
     * @return {@link User} 프로필 데이터가 포함된 사용자 엔티티 (존재하지 않으면, {@link Optional#empty()} 반환)
     */
    @Query("SELECT u FROM User u JOIN FETCH u.profile p JOIN FETCH p.campus JOIN FETCH u.hints WHERE u.id = :userId")
    Optional<User> findUserWithProfileById(@Param("userId") Long userId);

}
