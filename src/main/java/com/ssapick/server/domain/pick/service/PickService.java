package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.User;
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
	public List<PickData.Search> searchToPickInfos(Long userId) {
		return  pickRepository.findAllByFromUserId(userId).
			stream().map(PickData.Search::fromEntity).collect(Collectors.toList());
	}

	/**
	 * 보낸 픽 조회하기
	 * @param userId
	 * @return
	 */
	public List<PickData.Search> searchFromPickInfos(Long userId) {
		return pickRepository.findAllByToUserId(userId).
			stream().map(PickData.Search::fromEntity).collect(Collectors.toList());
	}

	public void createPick(PickData.Create create) {

		User fromUser = userRepository.findById(create.getFromUserId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		User toUser = userRepository.findById(create.getToUserId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		Question question = questionRepository.findById(create.getQuestionId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));

		pickRepository.save(Pick.of(create, fromUser, toUser, question));
	}
}
