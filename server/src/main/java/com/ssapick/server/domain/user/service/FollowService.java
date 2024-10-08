package com.ssapick.server.domain.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import com.ssapick.server.domain.user.repository.UserBanRepository;
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
    private final UserBanRepository userBanRepository;
    private final ProfileRepository profileRepository;
    // private final FollowQueryRepository followQueryRepository;

    /**
     * 팔로워 목록 조회
     * @param user
     * @return
     */
    public List<ProfileData.Friend> findFollowUsers(User user) {
        return userRepository.findFollowUserByUserId(user.getId());
    }

    @Transactional
    public void followUser(User user, Long followingUserId) {
        User followingUser = userRepository.findById(followingUserId).orElseThrow(
            () -> new BaseException(ErrorCode.NOT_FOLLOWED_USER));


        followRepository.findByFollowUserAndFollowingUser(user, followingUser).ifPresent(follow -> {
            throw new BaseException(ErrorCode.ALREADY_FOLLOWED_USER);
        });

        followRepository.save(Follow.follow(user, followingUser));
    }

    @Transactional
    public void unfollowUser(User user, Long followingUserId) {
        User followingUser = userRepository.findById(followingUserId).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

        Follow follow = followRepository.findByFollowUserAndFollowingUser(user, followingUser).orElseThrow(
            () -> new BaseException(ErrorCode.NOT_FOLLOWED_USER));

        followRepository.delete(follow);
    }

    /**
     * 추천 팔로우 목록 조회
     *
     * @param user
     * @return List<ProfileData.Search>
     */
    public Page<ProfileData.Friend> recommendFollow(User user, Pageable pageable) {
       return followRepository.findRecommendFriends(user.getId(), pageable);
    }
}
