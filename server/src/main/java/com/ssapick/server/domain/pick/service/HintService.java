package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.UserData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.ProfileRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {
	private final HintRepository hintRepository;
	private final PickRepository pickRepository;
	private final UserRepository userRepository;
	private final CampusRepository campusRepository;
	private final ProfileRepository profileRepository;
	private final ApplicationEventPublisher publisher;

	public String getRandomHintByPickId(Long pickId) {
		Pick pick = pickRepository.findPickWithHintsById(pickId).orElseThrow(
			() -> new IllegalArgumentException("Pick이 존재하지 않습니다.")
		);

		if (pick.getHintOpens().size() >= 2) {
			throw new IllegalArgumentException("힌트는 2개까지만 열 수 있습니다.");
		}

		List<Hint> hints = hintRepository.findAllByUserId(pick.getSender().getId());
		List<Long> availableHints = getAvailableHintIds(pick, hints);

		Long openHintId = selectRandomHintId(availableHints);
		Hint openHint = findHintById(hints, openHintId);

		addHintOpenToPick(pick, openHint);

		publisher.publishEvent(new PickcoEvent(
			pick.getSender(),
			PickcoLogType.HINT_OPEN,
			-1,
			pick.getSender().getProfile().getPickco()
		));

		String hintContent = openHint.getContent();

		if (openHint.getHintType().equals(HintType.NAME)) {
			log.info("이름 힌트를 열었습니다.");
			hintContent = processNameHint(hintContent);
			log.info("Processed hintContent: {}", hintContent);
		}

		return hintContent;
	}

	public List<Long> getAvailableHintIds(Pick pick, List<Hint> hints) {
		List<Long> openedHintIds = pick.getHintOpens().stream()
			.map(HintOpen::getHint)
			.map(Hint::getId)
			.toList();

		return hints.stream()
			.map(Hint::getId)
			.filter(hintId -> !openedHintIds.contains(hintId))
			.collect(Collectors.toList());
	}

	public Long selectRandomHintId(List<Long> availableHints) {
		return availableHints.get(new Random().nextInt(availableHints.size()));
	}

	public Hint findHintById(List<Hint> hints, Long hintId) {
		return hints.stream()
			.filter(hint -> hint.getId().equals(hintId))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("힌트를 찾을 수 없습니다."));
	}

	public void addHintOpenToPick(Pick pick, Hint openHint) {
		HintOpen hintOpen = HintOpen.builder()
			.hint(openHint)
			.pick(pick)
			.build();
		pick.getHintOpens().add(hintOpen);
	}

	public List<Hint> getHintsByUserId(Long userId) {
		return hintRepository.findAllByUserId(userId);
	}

	public List<HintOpen> getHintOpensByPickId(Long pickId) {
		Pick pick = pickRepository.findPickWithHintsById(pickId).orElseThrow(
			() -> new IllegalArgumentException("Pick이 존재하지 않습니다.")
		);

		return pick.getHintOpens();
	}

	@Transactional
	public void saveHint(User user, UserData.Create request) {

		log.info("힌트 저장 요청: \n {}", request + "\n" + user);

		if (user == null) {
			throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
		}

		if (request == null ||
			request.getName() == null ||
			request.getChort() == 0 ||
			request.getCampusName() == null ||
			request.getCampusSection() == 0 || // 기본값 체크
			request.getMbti() == null ||
			request.getMajor() == null ||
			request.getBirth() == null ||
			request.getResidentialArea() == null ||
			request.getInterest() == null) {
			throw new IllegalArgumentException("힌트 데이터의 필드 중 null인 값이 있습니다.");
		}

		user.updateName(request.getName());
		user.updateGender(request.getGender());

		Profile profile = user.getProfile();
		if (profile != null) {
			Campus campus = profile.getCampus();
			if (campus != null) {
				campus = Campus.createCampus(request.getCampusName(), request.getCampusSection(),
					campus.getDescription());
			} else {
				campus = Campus.createCampus(request.getCampusName(), request.getCampusSection(), null);
			}
			profile = Profile.createProfile(user, request.getChort(), campus, request.getProfileImage());
		} else {
			Campus campus = Campus.createCampus(request.getCampusName(), request.getCampusSection(), null);
			profile = Profile.createProfile(user, request.getChort(), campus, null);
		}

		campusRepository.save(profile.getCampus());

		profileRepository.save(profile);

		user.updateProfile(profile);
		userRepository.save(user);

		log.info("Campus : {}", String.valueOf(user.getProfile().getCampus()));
		log.info("Profile : {}", String.valueOf(user.getProfile()));

		List<Hint> hints = List.of(
			Hint.createHint(user, request.getName(), HintType.NAME),
			Hint.createHint(user, String.valueOf(request.getGender()), HintType.GENDER),
			Hint.createHint(user, String.valueOf(request.getChort()), HintType.CHORT),
			Hint.createHint(user, request.getCampusName(), HintType.CAMPUS_NAME),
			Hint.createHint(user, String.valueOf(request.getCampusSection()), HintType.CAMPUS_SECTION),
			Hint.createHint(user, request.getMbti(), HintType.MBTI),
			Hint.createHint(user, request.getMajor(), HintType.MAJOR),
			Hint.createHint(user, request.getBirth(), HintType.AGE),
			Hint.createHint(user, request.getResidentialArea(), HintType.RESIDENTIAL_AREA),
			Hint.createHint(user, request.getInterest(), HintType.INTEREST)
		);

		for (Hint hint : hints) {
			log.info("유저 아이디: {}", user.getId());
			log.info("힌트 타입: {}", hint.getHintType());
			Optional<Hint> existingHintOptional = hintRepository.findByUserIdAndHintType(user.getId(),
				hint.getHintType());
			log.info("기존 힌트 조회 결과: {}", existingHintOptional);
			if (existingHintOptional.isPresent()) {
				Hint existingHint = existingHintOptional.get();
				if (!existingHint.getContent().equals(hint.getContent())) {
					existingHint.updateContent(hint.getContent());
					hintRepository.save(existingHint);
					log.info("바뀐 힌트 내용 : {}", hint.getContent());
					log.info("힌트 업데이트 완료");
				}
			} else {
				hintRepository.save(hint);
				log.info("힌트 저장 완료");
			}
		}
	}

	private String processNameHint(String name) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();

		result.append("X");

		List<Character> initials = name.substring(1).chars()
			.mapToObj(c -> (char)c)
			.map(this::getInitialConsonant)
			.collect(Collectors.toList());

		int revealIndex = random.nextInt(initials.size());

		for (int i = 0; i < initials.size(); i++) {
			if (i == revealIndex) {
				result.append(initials.get(i));
			} else {
				result.append("X");
			}
		}

		return result.toString();
	}

	private boolean isHangul(char c) {
		return c >= 0xAC00 && c <= 0xD7A3;
	}

	private char getInitialConsonant(char c) {
		if (isHangul(c)) {
			final char[] initials = {'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ',
				'ㅌ', 'ㅍ', 'ㅎ'};
			int index = (c - 0xAC00) / (21 * 28);
			return initials[index];
		}
		return 'X';
	}

}
