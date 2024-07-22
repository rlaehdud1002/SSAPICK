package com.ssapick.server.domain.pick.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickService {

	private final PickRepository pickRepository;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;


	/**
	 * 받은 픽 조회하기
	 * @param userId
	 * @return List<PickData.Search>
	 */
	public List<PickData.Recevied> searchReceived(Long userId) {
		return pickRepository.findAllByToUserId(userId)
			.stream().map(PickData.Recevied::fromEntity).toList();
		}
	/**
	 * 보낸 픽 조회하기
	 * @param userId
	 * @return
	 */
	public List<PickData.Sent> searchSent(Long userId) {
		return pickRepository.findAllByFromUserId(userId)
			.stream().map(PickData.Sent::fromEntity).toList();
	}

	public void createPick(PickData.Create create) {
		pickRepository.save(Pick.of(create));
	}
}
