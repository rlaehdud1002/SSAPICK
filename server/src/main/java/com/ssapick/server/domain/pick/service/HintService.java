package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.HintData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public void saveHint(List<HintData.Create> request) {

		if (request.stream().anyMatch(hint -> hint.getUser() == null)) {
			throw new IllegalArgumentException("유저 정보가 없습니다.");
		}

		if (request.stream().anyMatch(hint -> hint.getContent() == null)) {
			throw new IllegalArgumentException("힌트 내용이 없습니다.");
		}

		// id가 null이면 새로운 힌트 생성
		if (request.stream().allMatch(hint -> hint.getId() == null)) {
			List<Hint> hints = request.stream()
				.map(HintData.Create::toEntity)
				.collect(Collectors.toList());

			hintRepository.saveAll(hints);
			log.info("힌트 저장 완료");
		}

		// id가 null이 아니면 기존 힌트를 찾아서 업데이트
		if (request.stream().allMatch(hint -> hint.getId() != null)) {
			List<Hint> hints_haveId = request.stream()
				.map(HintData.Create::toEntity)
				.collect(Collectors.toList());

			hintRepository.saveAll(hints_haveId);
			log.info("힌트 업데이트 완료");
		}
	}
}
