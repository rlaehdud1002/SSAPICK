package com.ssapick.server.domain.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * 추천 팔로우 목록 조회
     * @param user
     * @return List<ProfileData.Search>
     */
    public List<ProfileData.Search> recommendFollow(User user) {
        // 현재 사용자의 친구 목록을 가져온다.
        List<Profile> friends = followRepository.findByFollowUser(user).stream()
            .map(follow -> follow.getFollowingUser().getProfile())
            .toList();

        Map<Profile, Integer> recommendationMap = new HashMap<>();

        // 친구의 친구를 순회하며 추천 후보를 찾는다.
        friends.stream()
            .flatMap(friend -> followRepository.findByFollowUser(friend.getUser()).stream())
            .map(follow -> follow.getFollowingUser().getProfile())
            .filter(profile -> !friends.contains(profile) && !profile.equals(user.getProfile()))
            .forEach(profile -> recommendationMap.merge(profile, 1, Integer::sum));

        // 추천 후보를 중복 횟수에 따라 정렬
        List<Profile> recommendedProfiles = recommendationMap.entrySet().stream()
            .sorted(Map.Entry.<Profile, Integer>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        // ProfileData.Search로 변환
        return recommendedProfiles.stream()
            .map(ProfileData.Search::fromEntity)
            .collect(Collectors.toList());
    }
}
