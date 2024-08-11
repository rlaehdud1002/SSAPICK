package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserBanRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

//	@Test
//	@DisplayName("차단한_친구가_없을_때_추천_친구_목록_조회_테스트")
//	void 차단한_친구가_없을_때_추천_친구_목록_조회_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		User userA = this.createUser("userA");
//
//		User userF = this.createUser("userF");
//		User userG = this.createUser("userG");
//		User userH = this.createUser("userH");
//
//
//		// 벤한 사용자는 없도록 설정
//		when(userBanRepository.findBanUsersByFromUser(userA)).thenReturn(List.of());
//		when(followRepository.findRecommendFriends(userA.getId())).thenReturn(List.of(
//				ProfileData.Friend.fromEntity(userF),
//				ProfileData.Friend.fromEntity(userG),
//				ProfileData.Friend.fromEntity(userH)
//		));
//
//		// * WHEN: 이걸 실행하면
//
//		List<ProfileData.Friend> searches = followService.recommendFollow(userA);
//
//		// * THEN: 이런 결과가 나와야 한다
//		assertThat(searches.size()).isEqualTo(3);
//		assertThat(searches).containsExactly(
//			ProfileData.Friend.fromEntity(userF),
//			ProfileData.Friend.fromEntity(userG),
//			ProfileData.Friend.fromEntity(userH)
//		);
//	}
//
//	@Test
//	@DisplayName("차단한_친구가_있을_때_추천_친구_목록_조회_테스트")
//	void 차단한_친구가_있을_때_추천_친구_목록_조회_테스트() throws Exception {
//		// * GIVEN: 이런게 주어졌을 때
//		User userA = this.createUser("userA");
//
//		User userF = this.createUser("userF");
//		User userG = this.createUser("userG");
//		User userH = this.createUser("userH");
//
//		when(followRepository.findRecommendFriends(userA.getId())).thenReturn(List.of(
//			createFriend(userF),
//			createFriend(userG),
//			createFriend(userH)
//		));
//		when(userBanRepository.findBanUsersByFromUser(userA)).thenReturn(List.of(userF));
//
//		// * WHEN: 이걸 실행하면
//		List<ProfileData.Friend> searches = followService.recommendFollow(userA);
//
//		// * THEN: 이런 결과가 나와야 한다
//		assertThat(searches.size()).isEqualTo(2);
//		assertThat(searches).containsExactly(
//			createFriend(userG),
//			createFriend(userH)
//		);
//
//	}
//
//	private ProfileData.Friend createFriend(User user) {
//		ProfileData.Friend friend = spy(ProfileData.Friend.fromEntity(user));
//		when(friend.getUserId()).thenReturn(user.getId());
//		when(friend.getName()).thenReturn(user.getName());
//		when(friend.getProfileImage()).thenReturn(user.getProfile().getProfileImage());
//		when(friend.getCohort()).thenReturn(user.getProfile().getCohort());
//		when(friend.getCampusSection()).thenReturn(user.getProfile().getCampus().getSection());
//		return friend;
//	}
}


