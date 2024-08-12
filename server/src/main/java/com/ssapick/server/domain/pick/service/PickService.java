package com.ssapick.server.domain.pick.service;

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
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ssapick.server.core.constants.PickConst.PICK_COIN;
import static com.ssapick.server.core.constants.PickConst.USER_REROLL_COIN;
import static com.ssapick.server.domain.pick.repository.PickCacheRepository.PASS_BLOCK_LIMIT;

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

	/***
	 * 받은 픽 조회하기
	 * @param user
	 * @param pageable
	 * @return
	 */
	public Page<PickData.Search> searchReceivePick(User user,Pageable pageable) {

		// pageable.

		// Step 1: Fetch paged IDs with sorting
		Page<Long> pickIdsPage = pickRepository.findPickIdsByReceiverId(user.getId(), pageable);
		List<Long> pickIds = pickIdsPage.getContent();

		if (pickIds.isEmpty()) {
			return Page.empty();
		}

		// Step 2: Fetch Picks with details using the sorted IDs
		List<Pick> picks = pickRepository.findAllByIdsWithDetails(pickIds);

		// Convert Pick entities to PickData.Search DTOs
		List<PickData.Search> pickSearchList = picks.stream()
			.map(pick -> PickData.Search.fromEntity(pick, true))
			.collect(Collectors.toList());

		// Return a Page object with the DTOs
		return new PageImpl<>(pickSearchList, pageable, pickIdsPage.getTotalElements());
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

	/**
	 * 픽 생성하기
	 * @param sender
	 * @param create
	 */
	@Transactional
	public PickData.PickCondition createPick(User sender, PickData.Create create) {
		if (pickCacheRepository.lock(sender.getId())) {
			throw new BaseException(ErrorCode.USER_PICK_LOCK);
		}

		if (pickCacheRepository.isCooltime(sender.getId())) {
			return PickData.PickCondition.builder()
				.isCooltime(true)
				.build();
		}

		Integer index = pickCacheRepository.getIndex(sender.getId());
		Integer pickCount = pickCacheRepository.getPickCount(sender.getId());
		Integer blockCount = pickCacheRepository.getBlockCount(sender.getId());
		Integer passCount = pickCacheRepository.getPassCount(sender.getId());

		Question question = questionRepository.findById(create.getQuestionId()).orElseThrow(
			() -> new BaseException(ErrorCode.NOT_FOUND_QUESTION));

		switch (create.getStatus()) {
			case PICKED -> {
				pickCacheRepository.pick(sender.getId());
				pickCount++;

				User receiver = em.getReference(User.class, create.getReceiverId());
				Pick pick = pickRepository.save(Pick.of(sender, receiver, question));
				publisher.publishEvent(
					FCMData.NotificationEvent.of(
							NotificationType.PICK,
							sender,
							receiver,
							pick.getId(),
							"누군가가 당신을 선택했어요!",
						    pickEventMessage(question.getContent()),
							null
                ));
				publisher.publishEvent(new PickcoEvent(sender, PickcoLogType.PICK, PICK_COIN));
			}
			case PASS -> {
				if (passCount + blockCount >= PASS_BLOCK_LIMIT) {
					throw new BaseException(ErrorCode.PASS_BLOCK_LIMIT);
				}
				pickCacheRepository.pass(sender.getId());
				passCount++;
				question.skip();
			}
			case BLOCK -> {
				if (passCount + blockCount >= PASS_BLOCK_LIMIT) {
					throw new BaseException(ErrorCode.PASS_BLOCK_LIMIT);
				}

				pickCacheRepository.block(sender.getId());
				blockCount++;
				question.increaseBanCount();
				questionBanRepository.save(QuestionBan.of(sender, question));
			}
		}
		index++;
		pickCacheRepository.increaseIndex(sender.getId());


		if (pickCount + blockCount >= 10) {
			pickCacheRepository.init(sender.getId());
			pickCacheRepository.setCooltime(sender.getId());
			return PickData.PickCondition.cooltime();
		}

		pickCacheRepository.unlock(sender.getId());

		return PickData.PickCondition.builder()
			.index(index)
			.pickCount(pickCount)
			.blockCount(blockCount)
			.passCount(passCount)
			.build();
	}

	/**
	 * 
	 * 현재 픽 진행 상태 조회하기
	 * @param sender
	 * @return
	 */
	public PickData.PickCondition getPickCondition(User sender) {

		if (pickCacheRepository.isEmpty(sender.getId())) {
			pickCacheRepository.init(sender.getId());
			return PickData.PickCondition.init();
		}

		if (pickCacheRepository.isCooltime(sender.getId())) {
			return PickData.PickCondition.cooltime();
		}

		return PickData.PickCondition.builder()
			.index(pickCacheRepository.getIndex(sender.getId()))
			.pickCount(pickCacheRepository.getPickCount(sender.getId()))
			.blockCount(pickCacheRepository.getBlockCount(sender.getId()))
			.passCount(pickCacheRepository.getPassCount(sender.getId()))
			.build();
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
		if (findPick.get().getId().equals(pickId)) {
			pick.updateAlarm();
		}

		findPick.get().updateAlarm();
		pick.updateAlarm();

	}

	@Transactional
	public void reRoll(User user) {
		publisher.publishEvent(new PickcoEvent(user, PickcoLogType.RE_ROLL, USER_REROLL_COIN));
	}

	public PickData.Search getPickWithAlarmOn(User user) {
		Optional<Pick> pick = pickRepository.findByReceiverIdWithAlarm(user.getId());

        return pick.map(value -> PickData.Search.fromEntity(value, true)).orElse(null);
    }

	@Scheduled(fixedRate = 1000 * 60 * 60, initialDelay = 0)
	public void sendFailNotification() {
		log.debug("전송 실패한 알람 스케쥴링 처리");
		pickRepository.findByAlarmSentIsFalse().forEach(pick -> {
			log.debug("전송 실패한 알람 재전송: {}", pick.getId());
			publisher.publishEvent(FCMData.NotificationEvent.of(
				NotificationType.PICK,
				pick.getSender(),
				pick.getReceiver(),
				pick.getId(),
					"누군가가 당신을 선택했어요!",
				pick.getQuestion().getContent(),
				null
			));
		});
	}
}
