package com.ssapick.server.domain.user.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;

@DisplayName("유저 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends UserSupport {

	@InjectMocks
	private UserService userService;

	@Mock
	private ApplicationEventPublisher publisher;

	@Mock
	private PickRepository pickRepository;

	@Mock
	private FollowRepository followRepository;

	@Test
	@DisplayName("유저 정보 조회")
	void getUserInfo() {
		// Given
		User me = this.createUser("me");
		User other = this.createUser("other");

		Question question = mock(Question.class);
		List<Pick> picks = List.of(
			Pick.of(other, me, question),
			Pick.of(other, me, question)
		);

		List<Follow> followings = List.of(
			Follow.follow(other, me)
		);

		lenient().when(userRepository.findUserWithProfileById(anyLong())).thenReturn(Optional.of(me));
		lenient().when(pickRepository.findReceiverByUserId(me.getId())).thenReturn(picks);
		lenient().when(followRepository.findByFollowingUser(me)).thenReturn(followings);

		// When
		UserData.UserInfo userInfo = userService.getUserInfo(me);

		//         Then
		Assertions.assertThat(userInfo.getName()).isEqualTo(me.getName());
		Assertions.assertThat(userInfo.getPickCount()).isEqualTo(picks.size());
		Assertions.assertThat(userInfo.getFollowingCount()).isEqualTo(followings.size());

	}

}