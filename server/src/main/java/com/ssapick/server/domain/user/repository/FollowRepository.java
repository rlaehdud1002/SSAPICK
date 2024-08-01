package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowUserAndFollowingUser(User followingUser, User followUser);


    @Query("SELECT f FROM Follow f JOIN FETCH f.followingUser JOIN FETCH f.followingUser.profile WHERE f.followUser = :user")
    List<Follow> findByFollowUser(@Param("user") User user);

    @Query("SELECT f FROM Follow f JOIN FETCH f.followingUser JOIN FETCH f.followingUser.profile WHERE f.followingUser = :user")
    List<Follow> findByFollowingUser(@Param("user") User user);
}
