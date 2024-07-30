package com.ssapick.server.domain.user.service;

import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.service.S3Service;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final ApplicationEventPublisher publisher;
	private final UserRepository userRepository;
	private final S3Service s3Service;
	private final CampusRepository campusRepository;

	@Transactional
	public void changePickco(Long userId, PickcoLogType type, int amount) {
		User user = findUserOrThrow(userId);
		Profile profile = user.getProfile();

		profile.changePickco(amount);

		publisher.publishEvent(new PickcoEvent(user, type, amount, profile.getPickco()));
	}

	@Transactional
	public void updateUser(Long userId, UserData.Update update, MultipartFile profileImage) {
		User user = findUserOrThrow(userId);

		updateUserDetails(user, update);

		Campus campus = getOrCreateCampus(update.getCampusName(), update.getCampusSection());

		String profileImageUrl = handleProfileImage(user, profileImage);

		Profile profile = updateOrCreateProfile(user, update, campus, profileImageUrl);
		user.updateProfile(profile);

		List<Hint> hints = createHints(update);
		user.updateHints(hints);

		userRepository.save(user);
	}

	private void updateUserDetails(User user, UserData.Update update) {
		user.updateName(update.getName());
		user.updateGender(update.getGender());
	}

	private Campus getOrCreateCampus(String name, Short section) {
		return campusRepository.findByNameAndSection(name, section)
			.orElseGet(() -> campusRepository.save(Campus.createCampus(name, section, null)));
	}

	private String handleProfileImage(User user, MultipartFile profileImage) {
		String existingProfileImageUrl = user.getProfile() != null ? user.getProfile().getProfileImage() : "NO_IMG";

		if (!"NO_IMG".equals(existingProfileImageUrl)) {
			s3Service.deleteImageFromS3(existingProfileImageUrl);
		}

		if (profileImage == null) {
			return "NO_IMG";
		}

		return s3Service.upload(profileImage).join();
	}

	private Profile updateOrCreateProfile(User user, UserData.Update update, Campus campus, String profileImageUrl) {
		Profile existingProfile = user.getProfile();

		if (existingProfile != null) {
			existingProfile.updateProfile(update.getCohort(), campus, profileImageUrl);
			return existingProfile;
		} else {
			return Profile.createProfile(user, update.getCohort(), campus, profileImageUrl);
		}
	}

	private List<Hint> createHints(UserData.Update update) {
		return List.of(
			Hint.createHint(update.getName(), HintType.NAME),
			Hint.createHint(String.valueOf(update.getGender()), HintType.GENDER),
			Hint.createHint(String.valueOf(update.getCohort()), HintType.CHORT),
			Hint.createHint(update.getCampusName(), HintType.CAMPUS_NAME),
			Hint.createHint(String.valueOf(update.getCampusSection()), HintType.CAMPUS_SECTION),
			Hint.createHint(update.getMbti(), HintType.MBTI),
			Hint.createHint(update.getMajor(), HintType.MAJOR),
			Hint.createHint(update.getBirth(), HintType.AGE),
			Hint.createHint(update.getResidentialArea(), HintType.RESIDENTIAL_AREA),
			Hint.createHint(update.getInterest(), HintType.INTEREST)
		);
	}

	@Bean
	public Function<UserDetails, User> fetchUser() {
		return userDetails -> userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
			() -> new BaseException(ErrorCode.NOT_FOUND_USER)
		);
	}

	private User findUserOrThrow(Long userId) throws IllegalArgumentException {
		return userRepository.findUserWithProfileById(userId)
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));
	}
}
