package com.ssapick.server.core.support;

import com.ssapick.server.domain.auth.service.CustomUserService;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Import(CustomUserService.class)
public abstract class AuthenticatedSupport {
	@MockBean
	protected UserRepository userRepository;

	@MockBean
	protected HintRepository hintRepository;

	@MockBean
	protected CampusRepository campusRepository;

	@MockBean
	protected FollowRepository followRepository;

	@MockBean
	protected PickRepository pickRepository;

	private AtomicLong atomicLong = new AtomicLong(1);

	protected User createUser() {
		User user = spy(User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456"));
		Profile profile = spy(Profile.createProfile(user, (short)1, createCampus()));
		when(user.getProfile()).thenReturn(profile);
		when(user.getProfile().getProfileImage()).thenReturn("테스트 프로필 이미지 URL");
		when(user.getId()).thenReturn(atomicLong.incrementAndGet());
		return user;
	}

	protected User createUser(String name) {
		User user = spy(User.createUser(name, name, 'M', ProviderType.KAKAO, "123456"));
		Profile profile = spy(Profile.createProfile(user, (short)1, createCampus()));
		when(user.getProfile()).thenReturn(profile);
		when(user.getProfile().getProfileImage()).thenReturn("테스트 프로필 이미지 URL");
		when(user.getId()).thenReturn(atomicLong.incrementAndGet());
		return user;
	}

	protected Campus createCampus() {
		return Campus.createCampus("광주", (short)1, "자바 전공");
	}

	@BeforeEach
	public void setUp() {
		User user = createUser("test-user");
		atomicLong = new AtomicLong(1);
		when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
	}
}
