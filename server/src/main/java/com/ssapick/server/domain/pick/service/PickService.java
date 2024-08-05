package com.ssapick.server.domain.pick.service;

import static com.ssapick.server.domain.pick.repository.PickCacheRepository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickCacheRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;
import com.ssapick.server.domain.question.repository.QuestionBanRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickService {
	private final ApplicationEventPublisher publisher;
	private final PickRepository pickRepository;
	private final PickCacheRepository pickCacheRepository;
	private final QuestionRepository questionRepository;
	private final QuestionBanRepository questionBanRepository;
	private final EntityManager em;

	/**
	 * 받은 픽 조회하기
	 *
	 * @param user 로그인한 유저
	 * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 받은 픽 리스트
	 */
	public List<PickData.Search> searchReceivePick(User user) {
		return pickRepository.findReceiverByUserId(user.getId()).stream()
			.map((Pick pick) -> PickData.Search.fromEntity(pick, true))
			.toList();
	}

	/**
	 * 보낸 픽 조회하기
	 *
	 * @param user 로그인한 유저
	 * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 보낸 픽 리스트
	 */
	public List<PickData.Search> searchSendPick(User user) {
		return pickRepository.findSenderByUserId(user.getId()).stream()
			.map((Pick pick) -> PickData.Search.fromEntity(pick, false))
			.toList();
	}

	@Transactional
	public void createPick(User sender, PickData.Create create) {
		log.info("픽 생성 요청 - sender: {}, create: {}", sender, create);
		Integer index = pickCacheRepository.index(sender.getId());

		if (index == null) {
			pickCacheRepository.init(sender.getId());
			index = 1;
		}

		if (index == null || create.getIndex() != index) {
			throw new BaseException(ErrorCode.INVALID_PICK_INDEX);
		}

		Question question = questionRepository.findById(create.getQuestionId()).orElseThrow(() -> {
			log.error("질문이 존재하지 않습니다. questionId: {}", create.getQuestionId());
			return new BaseException(ErrorCode.NOT_FOUND_QUESTION);
		});

		switch (create.getStatus()) {
			case PICKED -> {
				User reference = em.getReference(User.class, create.getReceiverId());
				Pick pick = pickRepository.save(Pick.of(sender, reference, question));
				publisher.publishEvent(
					FCMData.NotificationEvent.of(NotificationType.PICK, reference, pick.getId(), "누군가가 당신을 선택했어요!",
						pickEventMessage(question.getContent()), null));
			}
			case PASS -> {
				question.skip();
				log.error("질문이 스킵되었습니다. questionId: {}, user: {}", question.getId(), sender);
			}
			case BLOCK -> {
				question.increaseBanCount();
				questionBanRepository.save(QuestionBan.of(sender, question));
				log.error("질문이 차단되었습니다. questionId: {}, user: {}", question.getId(), sender);
			}
		}
		pickCacheRepository.increment(sender.getId());

		if (index == LAST_INDEX) {
			pickCacheRepository.init(sender.getId());
		}

		log.info("============================================================끝");
	}

	private String pickEventMessage(String message) {
		return message;
	}

	@Transactional
	public void updatePickAlarm(User user, Long pickId) {
		Pick pick = pickRepository.findById(pickId).orElseThrow(() -> {
			throw new BaseException(ErrorCode.NOT_FOUND_PICK);
		});

		if (!pick.getReceiver().getId().equals(user.getId())) {
			throw new BaseException(ErrorCode.ACCESS_DENIED);
		}

		Optional<Pick> findPick = pickRepository.findByReceiverIdWithAlarm(user.getId());

		if (findPick.isEmpty()) {
			pick.updateAlarm();
			return;
		}

		findPick.get().updateAlarm();
		pick.updateAlarm();

	}

}
