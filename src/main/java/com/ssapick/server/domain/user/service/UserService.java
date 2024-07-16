package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public void changePickco(Long userId, PickcoLogType type, int amount) {
        User user = findUserOrThrow(userId);

        int remain = user.getProfile().getPickco();


    }

    private User findUserOrThrow(Long userId) throws IllegalArgumentException {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
