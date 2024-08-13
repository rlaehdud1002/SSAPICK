package com.ssapick.server.domain.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssapick.server.domain.user.dto.ProfileData;

public interface FollowQueryRepository {
	Page<ProfileData.Friend> findRecommendFriends(Long userId, Pageable pageable);
}
