package com.ssapick.server.core.support;

import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.CampusRepository;
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

    @Mock
    private CampusRepository campusRepository;

    protected User createUser() {
        return createUser("test-user");
    }

    protected User createUser(String name) {
        User user = spy(User.createUser(name, name, 'M', ProviderType.KAKAO, "123456"));
        Campus campus = spy(createCampus());
        Profile profile = spy(Profile.createProfile(user, (short) 1, campus));
        long id = atomicLong.incrementAndGet();
        lenient().when(campus.getSection()).thenReturn((short) 1);
        lenient().when(user.getProfile()).thenReturn(profile);
        lenient().when(profile.getId()).thenReturn(id);
        lenient().when(profile.getCampus()).thenReturn(campus);
        lenient().when(profile.getCohort()).thenReturn((short) 1);
        lenient().when(profile.getProfileImage()).thenReturn("프로필 이미지");
        lenient().when(user.getId()).thenReturn(atomicLong.incrementAndGet());
        lenient().when(user.getName()).thenReturn(name);
        lenient().when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        return user;
    }

    protected Campus createCampus() {
        return Campus.createCampus("광주", (short) 1, "자바 전공");
    }
}
