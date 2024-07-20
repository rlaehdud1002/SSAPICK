package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintOpenRepository;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {

	private final HintRepository hintRepository;
	private final HintOpenRepository hintOpenRepository;
	private final PickRepository pickRepository;

	/**
	 * pickId로 열린 힌트 리스트 조회 후 2개 미만 이라면 힌트를 반환
	 * @param pickId 조회할 픽 아이디
	 * @return {@link Hint } 랜덤한 힌트
	 */
	@Transactional
	public Hint getRandomHintByPickId(Long pickId) {

		Pick pick = pickRepository.findById(pickId).orElseThrow();

		List<HintOpen> hintOpens = pick.getHintOpens();

		if (hintOpens.isEmpty()) {

			List<Hint> hints = hintRepository.findAllByUserId(pick.getFromUser().getId())
				.stream()
				.toList();

			Hint hint = hints.get(new Random().nextInt(hints.size()));

			HintOpen hintOpen = HintOpen.builder()
				.hint(hint)
				.pick(pick)
				.build();

			pick.getHintOpens().add(hintOpen);
			return hint;

		}

		if (hintOpens.size() == 1) {

			List<Hint> hints = hintRepository.findAllByUserId(pick.getFromUser().getId())
				.stream()
				.filter(hint -> !hint.getId().equals(hintOpens.get(0).getHint().getId()))
				.toList();

			Hint hint = hints.get(new Random().nextInt(hints.size()));

			HintOpen hintOpen = HintOpen.builder()
				.hint(hint)
				.pick(pick)
				.build();

			pick.getHintOpens().add(hintOpen);
			return hint;
		}

		return null;

	}

	/**
	 * 힌트 저장
	 * @param UserId 사용자 아이디
	//  * @param HintType 힌트 타입
	//  * @param content 힌트 내용
	//  * @return {@link Hint} 저장될 힌트
	//  */
	// @Transactional
	// public Hint saveHint(Long userId, String hintType, String content) {
	//
	// 	Hint hint = Hint.builder()
	// 		.user(userId)
	// 		.hintType(hintType)
	// 		.content(content)
	// 		.build();
	//
	// 	return hintRepository.save(hint);
	// }
}
