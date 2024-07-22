package com.ssapick.server.domain.pick.service;

import com.ssapick.server.domain.pick.dto.HintData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {
	private final HintRepository hintRepository;
	private final PickRepository pickRepository;

	public Hint getRandomHintByPickId(Long pickId) {
		Pick pick = pickRepository.findPickWithHintsById(pickId).orElseThrow(
			() -> new IllegalArgumentException("Pick이 존재하지 않습니다.")
		);

		if (pick.getHintOpens().size() >= 2) {
			throw new IllegalArgumentException("힌트는 2개까지만 열 수 있습니다.");
		}

		List<Hint> hints = hintRepository.findAllByUserId(pick.getSender().getId());
		List<Long> availableHints = getAvailableHintIds(pick, hints);

		log.debug("availableHints: {}", availableHints);

		Long openHintId = selectRandomHintId(availableHints);
		Hint openHint = findHintById(hints, openHintId);

		addHintOpenToPick(pick, openHint);

		return openHint;
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
	public void saveHint(HintData.Create request) {
		hintRepository.save(request.toEntity());
	}
}
