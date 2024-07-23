package com.ssapick.server.core.support;

import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;

public abstract class AuthenticatedSupport {
	protected User createUser() {
		return User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456");
	}

	protected MattermostData.Request createMattermostRequest() {
		return new MattermostData.Request("test", "123456");
	}

	protected MattermostData.Response createMattermostResponseOne() {
		MattermostData.Response response = new MattermostData.Response();
		response.setNickname("이인준[광주_2반]");
		return response;
	}

	protected MattermostData.Response createMattermostResponseTwo() {
		MattermostData.Response response = new MattermostData.Response();
		response.setNickname("이인준[광주_2반_C211]");
		return response;
	}
}
