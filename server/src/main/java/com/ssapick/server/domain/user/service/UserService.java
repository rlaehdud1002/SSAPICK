package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.S3UploadEvent;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private final ApplicationEventPublisher publisher;
	private final UserRepository userRepository;
	private final PickRepository pickRepository;
	private final FollowRepository followRepository;
	private final CampusRepository campusRepository;
	private final PickcoLogRepository pickcoLogRepository;

	public UserData.UserInfo getUserInfo(User user) {
		User findUser = userRepository.findUserWithProfileById(user.getId())
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

		int pickReceivedCount = pickRepository.findReceiverByUserId(findUser.getId()).size();
		int followingsCount = followRepository.findByFollowingUser(findUser).size();

		return UserData.UserInfo.createUserInfo(findUser, pickReceivedCount, followingsCount);
	}

	public UserData.IsValid idValid(User user) {
		return UserData.IsValid.of(userRepository.findById(user.getId()).orElseThrow());
	}

	@Transactional
	public void updateUser(Long userId, UserData.Update update, MultipartFile profileImage) {
		User user = findUserOrThrow(userId);

		updateUserDetails(user, update);

		Campus campus = getOrCreateCampus(update.getCampusName(), update.getCampusSection());

		Profile profile = updateOrCreateProfile(user, update, campus);
		user.updateProfile(profile);

		List<Hint> hints = createHints(update);

		user.updateHints(hints);

		userRepository.save(user);

		if (profileImage != null && !profileImage.isEmpty()) {
			publisher.publishEvent(new S3UploadEvent(profile, profileImage));
		}
	}

	private void updateUserDetails(User user, UserData.Update update) {
		user.updateName(update.getName());
		user.updateGender(update.getGender());
	}

	private Campus getOrCreateCampus(String name, Short section) {
		return campusRepository.findByNameAndSection(name, section)
			.orElseGet(() -> campusRepository.save(Campus.createCampus(name, section, null)));
	}

	private Profile updateOrCreateProfile(User user, UserData.Update update, Campus campus) {
		Profile existingProfile = user.getProfile();

		if (existingProfile != null) {
			existingProfile.updateProfile(update.getCohort(), campus);
			return existingProfile;
		} else {
			return Profile.createProfile(user, update.getCohort(), campus);
		}
	}

	private List<Hint> createHints(UserData.Update update) {
		return List.of(
			Hint.createHint(update.getName(), HintType.NAME),
			Hint.createHint(String.valueOf(update.getGender()), HintType.GENDER),
			Hint.createHint(String.valueOf(update.getCohort()), HintType.COHORT),
			Hint.createHint(update.getCampusName(), HintType.CAMPUS_NAME),
			Hint.createHint(String.valueOf(update.getCampusSection()), HintType.CAMPUS_SECTION),
			Hint.createHint(update.getMbti(), HintType.MBTI),
			Hint.createHint(update.getMajor(), HintType.MAJOR),
			Hint.createHint(update.getBirth(), HintType.AGE),
			Hint.createHint(update.getResidentialArea(), HintType.RESIDENTIAL_AREA),
			Hint.createHint(update.getInterest(), HintType.INTEREST)
		);
	}

	private User findUserOrThrow(Long userId) throws BaseException {
		return userRepository.findUserWithProfileById(userId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
	}

	public Page<ProfileData.Friend> getUserByKeyword(User user, String keyword, Pageable pageable) {
		return userRepository.searchUserByKeyword(user.getId(), keyword, pageable);
	}

	public UserData.Pickco getPickco(User user) {
		User findUser = userRepository.findUserWithProfileById(user.getId())
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

		return new UserData.Pickco(findUser.getProfile().getPickco());
	}

	public Page<UserData.PickcoLogResponse> getPickcoLogs(User user, Pageable pageable) {
		Page<PickcoLog> pickcoLogsPage = pickcoLogRepository.findAllByUserId(user.getId(), pageable);
		List<PickcoLog> pickcoLogs =pickcoLogsPage.getContent();

		List<UserData.PickcoLogResponse> pickcoLogResponse = pickcoLogs.stream()
			.map(UserData.PickcoLogResponse::fromEntity)
			.toList();

		return new PageImpl<>(pickcoLogResponse, pageable, pickcoLogsPage.getTotalElements());
	}
}
