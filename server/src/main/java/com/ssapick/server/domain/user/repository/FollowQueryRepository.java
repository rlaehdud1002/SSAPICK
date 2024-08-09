package com.ssapick.server.domain.user.repository;

import java.util.List;

import com.ssapick.server.domain.user.entity.User;

public interface FollowQueryRepository {
	List<User> findRecommendUserIds(User user);
}
