package com.ssapick.server.domain.user.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
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

	@Transactional
	public void changePickco(Long userId, PickcoLogType type, int amount) {
		User user = findUserOrThrow(userId);
		Profile profile = user.getProfile();

		profile.changePickco(amount);

		publisher.publishEvent(new PickcoEvent(user, type, amount, profile.getPickco()));
	}

	@Transactional
	public void updateUser(Long user_id, UserData.Update update, MultipartFile profileImage) {

		User user = userRepository.findById(user_id).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_USER));

		if (user == null) {
			throw new BaseException(ErrorCode.NOT_FOUND_USER);
		}

		user.updateName(update.getName());
		user.updateGender(update.getGender());

		// 이미지 업로드
		CompletableFuture<String> future = s3Service.upload(profileImage);
		String profileImageUrl = future.join();
		//

		Profile profile = user.getProfile();
		if (profile != null) {
			Campus campus = profile.getCampus();
			if (campus != null) {
				campus = Campus.createCampus(update.getCampusName(), update.getCampusSection(),
					null);
			} else {
				campus = Campus.createCampus(update.getCampusName(), update.getCampusSection(), null);
			}
			profile = Profile.createProfile(user, update.getCohort(), campus, profileImageUrl);
		} else {
			Campus campus = Campus.createCampus(update.getCampusName(), update.getCampusSection(), null);
			profile = Profile.createProfile(user, update.getCohort(), campus, profileImageUrl);
		}

		user.updateProfile(profile);

		List<Hint> hints = List.of(
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

		user.updateHints(hints);

		userRepository.save(user);
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
