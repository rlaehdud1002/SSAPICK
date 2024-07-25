package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingUserAndFollowUser(User followingUser, User followUser);
}
