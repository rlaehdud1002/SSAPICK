package com.ssapick.server.core.support;

import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.mockito.Mock;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;

public abstract class UserSupport {
    private final AtomicLong atomicLong = new AtomicLong(1);
    @Mock
    private UserRepository userRepository;

    protected User createUser() {
        User user = spy(User.createUser("test", "테스트 유저", 'M', ProviderType.KAKAO, "123456"));
        Profile profile = Profile.createProfile(user, (short) 1, createCampus(), "https://test-profile.com");
        lenient().when(user.getProfile()).thenReturn(profile);
        lenient().when(user.getId()).thenReturn(atomicLong.incrementAndGet());
        return user;
    }

    protected User createUser(String name) {
        User user = spy(User.createUser(name, name, 'M', ProviderType.KAKAO, "123456"));
        Profile profile = spy(Profile.createProfile(user, (short) 1, createCampus(), "https://test-profile.com"));
        long id = atomicLong.incrementAndGet();
        lenient().when(user.getProfile()).thenReturn(profile);
        lenient().when(profile.getId()).thenReturn(id);
        lenient().when(user.getId()).thenReturn(atomicLong.incrementAndGet());
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        return user;
    }

    protected Campus createCampus() {
        return Campus.createCampus("광주", (short) 1, "자바 전공");
    }
}
