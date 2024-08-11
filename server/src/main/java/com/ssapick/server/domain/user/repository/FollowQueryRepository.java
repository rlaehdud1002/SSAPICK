package com.ssapick.server.domain.user.repository;

import java.util.List;

import com.ssapick.server.domain.user.dto.ProfileData;

public interface FollowQueryRepository {
	List<ProfileData.Friend> findRecommendFriends(Long userId);
}
