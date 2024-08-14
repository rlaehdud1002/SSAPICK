package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserBanRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("팔로우 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class FollowServiceTest extends UserSupport {

	@InjectMocks
	private FollowService followService;

	@Mock
	private  UserRepository userRepository;

	@Mock
	private FollowRepository followRepository;

	@Mock
	private UserBanRepository userBanRepository;

	@Test
	@DisplayName("차단한_친구가_없을_때_추천_친구_목록_조회_테스트")
	void 차단한_친구가_없을_때_추천_친구_목록_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User userA = this.createUser("userA");

		User userF = this.createUser("userF");
		User userG = this.createUser("userG");
		User userH = this.createUser("userH");

		PageImpl<ProfileData.Friend> friends = new PageImpl<>(List.of(
			ProfileData.Friend.fromEntity(userF),
			ProfileData.Friend.fromEntity(userG),
			ProfileData.Friend.fromEntity(userH)
		), PageRequest.of(0, 10), 3);

		when(followRepository.findRecommendFriends(userA.getId(), PageRequest.of(0, 10))).thenReturn(friends);

		// * WHEN: 이걸 실행하면

		Page<ProfileData.Friend> searches = followService.recommendFollow(userA, PageRequest.of(0, 10));

		// * THEN: 이런 결과가 나와야 한다
		Assertions.assertThat(searches.getContent()).hasSize(3);
	}

	private ProfileData.Friend createFriend(User user) {
		ProfileData.Friend friend = spy(ProfileData.Friend.fromEntity(user));
		when(friend.getUserId()).thenReturn(user.getId());
		when(friend.getName()).thenReturn(user.getName());
		when(friend.getProfileImage()).thenReturn(user.getProfile().getProfileImage());
		when(friend.getCohort()).thenReturn(user.getProfile().getCohort());
		when(friend.getCampusSection()).thenReturn(user.getProfile().getCampus().getSection());
		when(friend.isFollow()).thenReturn(false);
		when(friend.isSameCampus()).thenReturn(true);
		return friend;
	}
}


