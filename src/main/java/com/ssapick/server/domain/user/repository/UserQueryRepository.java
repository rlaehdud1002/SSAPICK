package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.User;

import java.util.List;

public interface UserQueryRepository {
    List<User> findFollowUserByUserId(Long userId);
}
