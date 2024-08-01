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
import static org.mockito.Mockito.when;

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

		User userB = this.createUser("userB");
		User userC = this.createUser("userC");
		User userD = this.createUser("userD");

		User userF = this.createUser("userF");
		User userG = this.createUser("userG");
		User userH = this.createUser("userH");

		// 추천 친구 스코어 F : 3, G : 2, H : 1
		when(followRepository.findByFollowUser(userA)).thenReturn(List.of(
			this.createFollow(userA, userB),
			this.createFollow(userA, userC),
			this.createFollow(userA, userD)
		));

		when(followRepository.findByFollowUser(userB)).thenReturn(List.of(
			this.createFollow(userB, userF),
			this.createFollow(userB, userG),
			this.createFollow(userB, userH)
		));
		when(followRepository.findByFollowUser(userC)).thenReturn(List.of(
			this.createFollow(userC, userF),
			this.createFollow(userC, userG)));

		when(followRepository.findByFollowUser(userD)).thenReturn(List.of(
			this.createFollow(userD, userF)
		));

		// 벤한 사용자는 없도록 설정
		when(userBanRepository.findByFromUser(userA)).thenReturn(List.of());

		// * WHEN: 이걸 실행하면
		List<ProfileData.Search> searches = followService.recommendFollow(userA);

		// * THEN: 이런 결과가 나와야 한다
//		assertThat(searches.size()).isEqualTo(3);
//		assertThat(searches).containsExactly(
//			ProfileData.Search.fromEntity(userF.getProfile()),
//			ProfileData.Search.fromEntity(userG.getProfile()),
//			ProfileData.Search.fromEntity(userH.getProfile())
//		);

	}

	@Test
	@DisplayName("차단한_친구가_있을_때_추천_친구_목록_조회_테스트")
	void 차단한_친구가_있을_때_추천_친구_목록_조회_테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
		User userA = this.createUser("userA");

		User userB = this.createUser("userB");
		User userC = this.createUser("userC");
		User userD = this.createUser("userD");

		User userF = this.createUser("userF");
		User userG = this.createUser("userG");
		User userH = this.createUser("userH");

		when(followRepository.findByFollowUser(userA)).thenReturn(List.of(
			this.createFollow(userA, userB),
			this.createFollow(userA, userC),
			this.createFollow(userA, userD)
		));

		when(followRepository.findByFollowUser(userB)).thenReturn(List.of(
			this.createFollow(userB, userF),
			this.createFollow(userB, userG),
			this.createFollow(userB, userH)
		));
		when(followRepository.findByFollowUser(userC)).thenReturn(List.of(
			this.createFollow(userC, userF),
			this.createFollow(userC, userG)));

		when(followRepository.findByFollowUser(userD)).thenReturn(List.of(
			this.createFollow(userD, userF)
		));

		when(userBanRepository.findByFromUser(userA)).thenReturn(List.of(userG));

	    // * WHEN: 이걸 실행하면
		List<ProfileData.Search> searches = followService.recommendFollow(userA);

	    // * THEN: 이런 결과가 나와야 한다
		assertThat(searches.size()).isEqualTo(2);
		assertThat(searches).containsExactly(
			ProfileData.Search.fromEntity(userF.getProfile()),
			ProfileData.Search.fromEntity(userH.getProfile())
		);
	}

	private Follow createFollow(User followingUser, User followUser) {
		return Follow.follow(followingUser, followUser);
	}

	private Profile createProfile(User user) {

		return Profile.createProfile(user, (short)1, createCampus());
	}
}


