package com.ssapick.server.domain.user.service;

import java.util.function.Function;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.pick.dto.UserData;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.repository.FollowRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final ApplicationEventPublisher publisher;
	private final UserRepository userRepository;
	private final PickRepository pickRepository;
	private final FollowRepository followRepository;

	public UserData.UserInfo getUserInfo(User user) {
		User findUser = userRepository.findUserWithProfileById(user.getId())
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

		int pickReceivedCount = pickRepository.findReceiverByUserId(findUser.getId()).size();
		int followingsCount = followRepository.findByFollowingUser(findUser).size();

		return UserData.UserInfo.createUserInfo(findUser, pickReceivedCount, followingsCount);
	}

	@Transactional
	public void changePickco(Long userId, PickcoLogType type, int amount) {
		User user = findUserOrThrow(userId);
		Profile profile = user.getProfile();

		profile.changePickco(amount);

		publisher.publishEvent(new PickcoEvent(user, type, amount, profile.getPickco()));
	}

	@Transactional
	public void updateUser(User user, ProfileData.Update update) {
		user.updateUser("", "");
	}

	@Bean
	public Function<UserDetails, User> fetchUser() {
		return userDetails -> userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
			() -> new BaseException(ErrorCode.NOT_FOUND_USER));
	}

	private User findUserOrThrow(Long userId) throws IllegalArgumentException {
		return userRepository.findUserWithProfileById(userId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
	}

}
