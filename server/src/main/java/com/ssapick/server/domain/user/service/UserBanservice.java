package com.ssapick.server.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.entity.UserBan;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserBanRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserBanservice {

	private final UserBanRepository userBanRepository;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;

	/**
	 * 사용자 차단
	 *
	 * @param user   로그인한 사용자
	 * @param userId 차단할 사용자 ID
	 */
	@Transactional
	public void banUser(User user, Long userId) {
		User findUser = userRepository.findById(userId).orElseThrow(

			() -> new BaseException(ErrorCode.NOT_FOUND_USER)
		);

		userBanRepository.findBanByFromUserAndToUser(user, findUser).ifPresent(
			(userBan) -> {
				throw new BaseException(ErrorCode.ALREADY_BAN_USER);
			}
		);

		// 만약 팔로우 되어있으면 팔로우 취소
		followRepository.findByFollowUserAndFollowingUser(user, findUser).ifPresent(
			follow -> {
				followRepository.delete(follow);
			}
		);

		findUser.increaseBanCount();
		userBanRepository.save(UserBan.of(user, findUser));
	}

	/**
	 * 사용자 차단 목록 조회
	 *
	 * @param user 로그인한 사용자
	 * @return 차단한 사용자 목록
	 */
	public List<ProfileData.Search> searchBanUsers(User user) {
		return userBanRepository.findBanUsersByFromUser(user).stream()
			.map(User::getProfile)
			.map(ProfileData.Search::fromEntity)
			.toList();

	}

	/**
	 * 사용자 차단 해제
	 *
	 * @param user   로그인한 사용자
	 * @param userId 차단 해제할 사용자 ID
	 */
	@Transactional
	public void unbanUser(User user, Long userId) {
		User findUser = userRepository.findById(userId).orElseThrow(
			() -> new BaseException(ErrorCode.NOT_FOUND_USER)
		);

		UserBan userBan = userBanRepository.findBanByFromUserAndToUser(user, findUser).orElseThrow(
			() -> new BaseException(ErrorCode.NOT_FOUND_USER_BAN)
		);

		findUser.decreaseBanCount();

		userBanRepository.delete(userBan);
	}
}