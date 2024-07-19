package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.HintData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintOpenRepository;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.dto.CampusData;
import com.ssapick.server.domain.user.repository.CampusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HintService {

	private final HintRepository hintRepository;
	private final HintOpenRepository hintOpenRepository;
	private final PickRepository pickRepository;

	/**
	 * 픽 아이디로 오픈된 힌트 조회
	 * @param pickId 조회할 픽 아이디
	 * @return {@link HintData.Id } 조회된 힌트 리스트
	 */
	public Hint getHintByPickId(Long pickId) {

		Pick pick = pickRepository.findById(pickId).get();

		List<HintOpen> hintOpens = pick.getHintOpens();

		if (hintOpens == null) {

			List<Hint> hints = hintRepository.findAllByUserId(pick.getFromUser().getId())
				.stream()
				.filter(Hint::isVisibility).toList();

			return hints.get(new Random().nextInt(hints.size()));
		}

		if (hintOpens.size() ==1){

			List<Hint> hints = hintRepository.findAllByUserId(pick.getFromUser().getId())
				.stream()
				.filter(Hint::isVisibility)
				.filter(hint -> !hint.getId().equals(hintOpens.get(0).getHint().getId()))
				.toList();

			return hints.get(new Random().nextInt(hints.size()));
		}

		// return hintOpenRepository.findAllByPickId(pickId).stream()
		// 	.map(HintData.Id::fromEntity)
		// 	.toList();
		return null;

	}

}
