package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 사용자 이름으로 사용자 조회
     * @param username 사용자 이름
     * @return {@link User} 사용자 엔티티 (존재하지 않으면, {@link Optional#empty()} 반환)
     */
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN Profile p ON p.user WHERE u.id = :userId")
    Optional<User> findProfileById(Long userId);
}
