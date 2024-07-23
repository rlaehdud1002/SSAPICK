package com.ssapick.server.core.support;

import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;

public abstract class AuthenticatedSupport {
    protected User createUser() {
        return User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456");
    }
}
