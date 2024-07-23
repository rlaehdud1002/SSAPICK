package com.ssapick.server.domain.pick.service;

import java.util.List;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(PickService.class);
	private final PickRepository pickRepository;
	private final UserRepository userRepository;
	private final QuestionRepository questionRepository;


	/**
	 * 받은 픽 조회하기
	 * @param userId
	 * @return List<PickData.Search>
	 */
	public List<PickData.Search> searchReceiver(Long userId) {
		return pickRepository.findReceiverByUserId(userId).stream()
			.map((Pick pick) -> PickData.Search.fromEntity(pick, true))
			.toList();

	}

	/**
	 * 보낸 픽 조회하기
	 * @param userId
	 * @return
	 */
	public List<PickData.Search> searchSender(Long userId) {
		return pickRepository.findSenderByUserId(userId).stream()
			.map((Pick pick) -> PickData.Search.fromEntity(pick, false))
			.toList();
	}

	/**
	 * 픽 생성하기
	 *
	 * @param sender
	 * @param request
	 */
	@Transactional
	public void createPick(User sender, PickData.Create request) {
		User receiver = userRepository.findById(request.getReceiverId())
						.orElseThrow(() -> new IllegalArgumentException("받는 사람을 찾을 수 없습니다."));

		Question question = questionRepository.findById(request.getQuestionId())
						.orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

		Pick pick = Pick.builder()
				.sender(sender)
				.receiver(receiver)
				.question(question)
				.build();

		pickRepository.save(pick);
	}
}
