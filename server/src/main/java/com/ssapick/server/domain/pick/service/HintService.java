package com.ssapick.server.domain.pick.service;

import static com.ssapick.server.core.constants.PickConst.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.event.PickcoEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {
	private final HintRepository hintRepository;
	private final PickRepository pickRepository;

	private final ApplicationEventPublisher publisher;

	@Transactional
	public String getRandomHintByPickId(Long pickId) {

		Pick pick = pickRepository.findPickWithHintsById(pickId).orElseThrow(
			() -> new IllegalArgumentException("Pick이 존재하지 않습니다.")
		);


		if (pick.getHintOpens().size() >= 2) {
			throw new IllegalArgumentException("힌트는 2개까지만 열 수 있습니다.");
		}

		List<Hint> hints = hintRepository.findAllByUserId(pick.getSender().getId());

		hints.removeIf(hint -> hint.getHintType().equals(HintType.GENDER));
		hints.removeIf(hint -> hint.getHintType().equals(HintType.CAMPUS_NAME));
		hints.removeIf(hint -> hint.getHintType().equals(HintType.CAMPUS_SECTION));
		hints.removeIf(hint -> hint.getHintType().equals(HintType.COHORT));

		List<Long> availableHints = getAvailableHintIds(pick, hints);

		Long openHintId = selectRandomHintId(availableHints);

		Hint openHint = findHintById(hints, openHintId);

		String hintContent = openHint.getContent();

		switch (openHint.getHintType()) {
			case NAME -> {
				hintContent = "초성 : " + processNameHint(hintContent);
			}
			case AGE -> {
				int birthYear = Integer.parseInt(hintContent.split("-")[0]);
				int age = LocalDate.now().getYear() - birthYear + 1;
				hintContent = "나이 : " + age + "세";
			}
			case RESIDENTIAL_AREA -> {
				hintContent = "거주지 : " + hintContent;
			}
			case MAJOR -> {
				hintContent = "전공 : " + hintContent;
			}
			case MBTI -> {
				hintContent = "MBTI : " + hintContent;
			}
			case INTEREST -> {
				hintContent = "관심사 : " + hintContent;
			}
		}

		addHintOpenToPick(pick, hintContent, openHint.getHintType());

		publisher.publishEvent(new PickcoEvent(
			pick.getReceiver(), PickcoLogType.HINT_OPEN, HINT_OPEN_COIN));

		return hintContent;
	}

	public List<Long> getAvailableHintIds(Pick pick, List<Hint> hints) {
		List<HintType> openedHintTypes = pick.getHintOpens().stream()
			.map(HintOpen::getHintType)
			.toList();

		return hints.stream()
			.filter(hint -> !openedHintTypes.contains(hint.getHintType()))
			.map(Hint::getId)
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

	public void addHintOpenToPick(Pick pick, String content, HintType hintType) {
		HintOpen hintOpen = HintOpen.builder()
			.pick(pick)
			.content(content)
			.hintType(hintType)
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

	private String processNameHint(String name) {
		StringBuilder result = new StringBuilder();
		Random random = new Random();

		result.append("X");

		List<Character> initials = name.substring(1).chars()
			.mapToObj(c -> (char)c)
			.map(this::getInitialConsonant)
			.toList();

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
