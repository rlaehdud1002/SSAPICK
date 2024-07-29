package com.ssapick.server.core.support;

import static org.mockito.Mockito.*;

import java.util.concurrent.atomic.AtomicLong;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;

public abstract class HintServiceTestSupport {

	private AtomicLong atomicLong = new AtomicLong(1);

	protected User createUser() {
		User user = spy(User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456"));
		Profile profile = Profile.createProfile(user, (short)1, createMockCampus(), "https://test-profile.com");
		lenient().when(user.getProfile()).thenReturn(profile);
		lenient().when(user.getId()).thenReturn(atomicLong.incrementAndGet());
		return user;
	}

	protected Campus createMockCampus() {
		return Campus.createCampus("광주", (short)1, "자바 전공");
	}

	protected Hint createMockHint(Long id, User user, String content) {
		Hint hint = Hint.createHint(content, HintType.NAME);
		hint.setTestId(id);
		return hint;
	}

	protected Pick createMockPick(User sender) {
		return Pick.builder()
			.sender(sender)
			.build();
	}

	protected HintOpen createMockHintOpen(Hint mockHint, Pick mockPick) {
		return HintOpen.builder().hint(mockHint).pick(mockPick).build();
	}

	protected Profile createMockProfile(User user, Campus campus) {
		Profile profile = Profile.createProfile(user, (short)11, campus, "image");
		profile.setTestId(1L);
		return profile;
	}
}
