package com.ssapick.server.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingUserAndFollowUser(User followingUser, User followUser);


    @Query("SELECT f FROM Follow f JOIN FETCH f.followingUser JOIN FETCH f.followingUser.profile WHERE f.followUser = :user")
    List<Follow> findByFollowUser(User user);
}
