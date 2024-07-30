package com.ssapick.server.core.support;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.ssapick.server.core.service.S3Service;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import com.ssapick.server.domain.user.service.UserService;

@Import(UserService.class)
public abstract class AuthenticatedSupport {
	@MockBean
	private UserRepository userRepository;

	@MockBean
	private HintRepository hintRepository;

	@MockBean
	private S3Service s3Service;

	private AtomicLong atomicLong = new AtomicLong(1);

	protected User createUser() {
		User user = spy(User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456"));
		Profile profile = Profile.createProfile(user, (short)1, createCampus(), "https://test-profile.com");
		when(user.getProfile()).thenReturn(profile);
		when(user.getId()).thenReturn(atomicLong.incrementAndGet());
		return user;
	}

	protected User createUser(String name) {
		User user = spy(User.createUser(name, name, 'M', ProviderType.KAKAO, "123456"));
		Profile profile = Profile.createProfile(user, (short)1, createCampus(), "https://test-profile.com");
		when(user.getProfile()).thenReturn(profile);
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
