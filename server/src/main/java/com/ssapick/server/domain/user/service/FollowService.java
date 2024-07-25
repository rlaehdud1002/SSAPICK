package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public List<ProfileData.Search> findFollowUsers(User user) {
        log.info("userRepository.findFollowUserByUserId(user.getId()) : {}", userRepository.findFollowUserByUserId(user.getId()));
        return userRepository.findFollowUserByUserId(user.getId()).stream().map(User::getProfile).map(ProfileData.Search::fromEntity).toList();
    }

    @Transactional
    public void followUser(User user, Long followUserId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );

        followRepository.findByFollowingUserAndFollowUser(user, followUser).ifPresent(follow -> {
            throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
        });

        followRepository.save(Follow.follow(user, followUser));
    }

    @Transactional
    public void unfollowUser(User user, Long followUserId) {
        User followUser = userRepository.findById(followUserId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFollowingUserAndFollowUser(user, followUser).orElseThrow(
                () -> new IllegalArgumentException("팔로우한 사용자가 아닙니다.")
        );

        followRepository.delete(follow);
    }
}
