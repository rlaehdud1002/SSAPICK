package com.ssapick.server.core.support;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;

public abstract class HintServiceTestSupport {

	protected User createMockUser() {
		User user = User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456");
		user.setTestId(1L);
		return user;
	}

	protected Hint createMockHint(Long id, User user, String content) {
		Hint hint = Hint.createHint(user, content, HintType.NAME);
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

	protected Campus createMockCampus() {
		Campus campus = Campus.createCampus("광주", (short)2, null);
		campus.setTestId(1L);
		return campus;
	}

	protected Profile createMockProfile(User user, Campus campus) {
		Profile profile = Profile.createProfile(user, (short)11, campus, "image");
		profile.setTestId(1L);
		return profile;
	}
}
