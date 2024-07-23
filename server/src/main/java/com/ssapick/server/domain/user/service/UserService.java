package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

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

    @Transactional
    public void updateUser(User user, ProfileData.Update update) {
        user.updateUser(update.getName(), update.getName());
    }

    @Bean
    public Function<UserDetails, User> fetchUser() {
        return userDetails -> userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
    }

    private User findUserOrThrow(Long userId) throws IllegalArgumentException {
        return userRepository.findUserWithProfileById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
