package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;

    @Transactional
    public void changePickco(Long userId, PickcoLogType type, int amount) {
        User user = findUserOrThrow(userId);
        Profile profile = user.getProfile();

        profile.changePickco(amount);

        publisher.publishEvent(new PickcoEvent(user, type, amount, profile.getPickco()));
    }

    private User findUserOrThrow(Long userId) throws IllegalArgumentException {
        return userRepository.findUserWithProfileById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
