package com.ssapick.server.core.support;

import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.properties.JwtProperties;
import com.ssapick.server.domain.auth.service.JWTService;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.boot.test.mock.mockito.MockBean;

public abstract class AuthenticatedSupport {
    @MockBean
    protected JWTService jwtService;

    @MockBean
    protected JWTFilter jwtFilter;

    @MockBean
    protected JwtProperties properties;

    protected User createUser() {
        return User.createUser("test", "테스트 유저", ProviderType.KAKAO, "123456");
    }
}
