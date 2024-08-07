package com.ssapick.server.domain.pick.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickCacheRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.repository.QuestionBanRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import jakarta.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("싸픽 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class PickServiceTest extends UserSupport {
	@InjectMocks
	private PickService pickService;

	@Mock
	private PickRepository pickRepository;

	@Mock
	private PickCacheRepository pickCacheRepository;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private QuestionBanRepository questionBanRepository;

	@Mock
	private EntityManager em;

	@Mock
	private ApplicationEventPublisher publisher;

	@Test
	@DisplayName("로그인한 사용자가 받은 픽 조회 테스트")
	void 로그인한_사용자가_받은_픽_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User receiver = this.createUser("받는 사람");
		User sender = this.createUser("보낸 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);
		List<Pick> picks = Stream.of(1, 2, 3).map(i -> {
			Pick pick = spy(this.createPick(sender, receiver, question));
			when(pick.getQuestion()).thenReturn(question);
			when(pick.getId()).thenReturn(Long.valueOf(i));
			return pick;
		}).toList();
		when(pickRepository.findReceiverByUserId(receiver.getId())).thenReturn(picks);

		// * WHEN: 이걸 실행하면
		List<PickData.Search> searches = pickService.searchReceivePick(receiver);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches.stream().map(PickData.Search::getId)).contains(1L, 2L, 3L);
		assertThat(searches.stream().map(PickData.Search::getSender)).map(ProfileData.Search::getNickname)
			.containsExactly(null, null, null);
		assertThat(searches.stream().map(PickData.Search::getSender)).map(ProfileData.Search::getProfileImage)
			.containsExactly(null, null, null);
	}

	@Test
	@DisplayName("로그인한 사용자가 보낸 픽 조회 테스트")
	void 로그인한_사용자가_보낸_픽_조회_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User receiver = this.createUser("받는 사람");
		User sender = this.createUser("보낸 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);
		List<Pick> picks = Stream.of(1, 2, 3).map(i -> {
			Pick pick = spy(this.createPick(sender, receiver, question));
			when(pick.getQuestion()).thenReturn(question);
			when(pick.getId()).thenReturn(Long.valueOf(i));
			return pick;
		}).toList();
		when(pickRepository.findSenderByUserId(sender.getId())).thenReturn(picks);

		// * WHEN: 이걸 실행하면
		List<PickData.Search> searches = pickService.searchSendPick(sender);

		// * THEN: 이런 결과가 나와야 한다
		assertThat(searches).hasSize(3);
		assertThat(searches.stream().map(PickData.Search::getId)).contains(1L, 2L, 3L);
		assertThat(searches.stream().map(PickData.Search::getReceiver)).map(ProfileData.Search::getNickname)
			.containsExactly(receiver.getName(), receiver.getName(), receiver.getName());
		assertThat(searches.stream().map(PickData.Search::getReceiver)).map(ProfileData.Search::getProfileImage)
			.containsExactly(receiver.getProfile().getProfileImage(), receiver.getProfile().getProfileImage(),
				receiver.getProfile().getProfileImage());
	}

	@Test
	@DisplayName("픽 정상 생성 테스트")
	void 픽_정상_생성_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setReceiverId(receiver.getId());
		create.setQuestionId(question.getId());
		create.setStatus(PickData.PickStatus.PICKED);
		create.setIndex(1);

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(questionRepository.findById(question.getId())).thenReturn(java.util.Optional.of(question));
		when(em.getReference(User.class, receiver.getId())).thenReturn(receiver);
		Pick spy = spy(Pick.of(sender, receiver, question));
		when(pickRepository.save(any())).thenReturn(spy);

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getPickCount(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getBlockCount(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getPassCount(sender.getId())).thenReturn(1);

		// * WHEN: 이걸 실행하면
		PickData.PickCondition pickCondition = pickService.createPick(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		verify(pickRepository).save(argThat(pick -> {
			assertThat(pick.getSender()).isEqualTo(sender);
			assertThat(pick.getReceiver()).isEqualTo(receiver);
			assertThat(pick.getQuestion()).isEqualTo(question);
			return true;
		}));
		verify(pickCacheRepository).pick(sender.getId());
		Assertions.assertThat(pickCondition).isEqualTo(PickData.PickCondition.builder()
			.index(2)
			.pickCount(2)
			.blockCount(1)
			.passCount(1)
			.isCooltime(false)
			.build());

	}

	@Test
	@DisplayName("픽 정상 패스 테스트")
	void 픽_정상_패스_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setReceiverId(receiver.getId());
		create.setQuestionId(question.getId());
		create.setStatus(PickData.PickStatus.PASS);
		create.setIndex(1);

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(questionRepository.findById(question.getId())).thenReturn(java.util.Optional.of(question));

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getPickCount(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getBlockCount(sender.getId())).thenReturn(1);
		when(pickCacheRepository.getPassCount(sender.getId())).thenReturn(1);


		// * WHEN: 이걸 실행하면
		pickService.createPick(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		verify(question).skip();
		verify(pickCacheRepository).pass(sender.getId());
		Assertions.assertThat(pickService.createPick(sender, create)).isEqualTo(PickData.PickCondition.builder()
			.index(2)
			.pickCount(1)
			.blockCount(1)
			.passCount(2)
			.isCooltime(false)
			.build());
	}

	@Test
	@DisplayName("픽_정상_차단_테스트")
	void 픽_정상_차단_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setReceiverId(receiver.getId());
		create.setQuestionId(question.getId());
		create.setStatus(PickData.PickStatus.BLOCK);
		create.setIndex(1);

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(questionRepository.findById(question.getId())).thenReturn(java.util.Optional.of(question));

		// * WHEN: 이걸 실행하면
		pickService.createPick(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		verify(question).increaseBanCount();
		verify(questionBanRepository).save(argThat(questionBan -> {
			assertThat(questionBan.getUser()).isEqualTo(sender);
			assertThat(questionBan.getQuestion()).isEqualTo(question);
			return true;
		}));
	}

	// @Test
	// @DisplayName("픽 인덱스 존재 X 테스트")
	// void 픽_인덱스_존재_X_테스트() throws Exception {
	// 	// * GIVEN: 이런게 주어졌을 때
	// 	int correctId = 1;
	// 	int incorrectId = 2;
	// 	User sender = this.createUser("보낸 사람");
	// 	User receiver = this.createUser("받는 사람");
	// 	Question question = spy(this.createQuestion());
	// 	when(question.getId()).thenReturn(1L);
	//
	// 	PickData.Create create = new PickData.Create();
	// 	create.setReceiverId(receiver.getId());
	// 	create.setQuestionId(question.getId());
	// 	create.setStatus(PickData.PickStatus.PICKED);
	// 	create.setIndex(correctId);
	//
	// 	when(pickCacheRepository.getIndex(sender.getId())).thenReturn(incorrectId);
	//
	// 	// * WHEN: 이걸 실행하면
	// 	Runnable runnable = () -> pickService.createPick(sender, create);
	//
	// 	// * THEN: 이런 결과가 나와야 한다
	// 	assertThatThrownBy(runnable::run)
	// 		.isInstanceOf(BaseException.class)
	// 		.hasMessage(ErrorCode.INVALID_PICK_INDEX.getMessage());
	// }

	@Test
	@DisplayName("질문이 없는 경우 테스트")
	void 질문이_없는_경우_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());
		when(question.getId()).thenReturn(1L);

		PickData.Create create = new PickData.Create();
		create.setReceiverId(receiver.getId());
		create.setQuestionId(question.getId());
		create.setStatus(PickData.PickStatus.PICKED);
		create.setIndex(1);

		when(pickCacheRepository.getIndex(sender.getId())).thenReturn(1);
		when(questionRepository.findById(question.getId())).thenReturn(java.util.Optional.empty());

		// * WHEN: 이걸 실행하면
		Runnable runnable = () -> pickService.createPick(sender, create);

		// * THEN: 이런 결과가 나와야 한다
		assertThatThrownBy(runnable::run)
			.isInstanceOf(BaseException.class)
			.hasMessage(ErrorCode.NOT_FOUND_QUESTION.getMessage());
	}

	@Test
	@DisplayName("픽 알림이 없을 떄 알림을 설정하면 alarm이 true가 되어야 한다")
	void 픽_알람이_없을_때_알림설정_설정_테스트() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());

		Pick pick = spy(Pick.of(sender, receiver, question));
		when(pick.getId()).thenReturn(1L);
		when(pickRepository.findById(pick.getId())).thenReturn(java.util.Optional.of(pick));

		// * WHEN: 이걸 실행하면
		pickService.updatePickAlarm(receiver, pick.getId());

		// * THEN: 이런 결과가 나와야 한다
		verify(pick).updateAlarm();
		assertThat(pick.isAlarm()).isTrue();

	}

	@Test
	@DisplayName("픽 알림의 알림을 해제하면 alarm이 false가 되어야 한다")
	void 픽_알림의_알림을_해제하면_alarm이_false가_되어야_한다() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());

		Pick pick = spy(Pick.of(sender, receiver, question));
		pick.updateAlarm();
		when(pick.getId()).thenReturn(1L);
		when(pickRepository.findById(pick.getId())).thenReturn(java.util.Optional.of(pick));
		when(pickRepository.findByReceiverIdWithAlarm(receiver.getId())).thenReturn(java.util.Optional.empty());

		// * WHEN: 이걸 실행하면
		pickService.updatePickAlarm(receiver, pick.getId());

		// * THEN: 이런 결과가 나와야 한다
		verify(pick, times(2)).updateAlarm();
		assertThat(pick.isAlarm()).isFalse();
	}

	@Test
	@DisplayName("픽한 알림이 있는데 다른 알림을 픽하려는 경우 기존 알림을 취소하고 새로운 알림을 설정해야 한다")
	void 픽한_알림이_있는데_다른_알림을_픽하려는_경우_기존_알림을_취소하고_새로운_알림을_설정해야_한다() throws Exception {
		// * GIVEN: 이런게 주어졌을 때
		User sender = this.createUser("보낸 사람");
		User receiver = this.createUser("받는 사람");
		Question question = spy(this.createQuestion());

		Pick pick = spy(Pick.of(sender, receiver, question));
		Pick findPick = spy(Pick.of(sender, receiver, question));
		findPick.updateAlarm();

		when(pick.getId()).thenReturn(1L);
		when(findPick.getId()).thenReturn(2L);
		when(pickRepository.findById(pick.getId())).thenReturn(java.util.Optional.of(pick));
		when(pickRepository.findByReceiverIdWithAlarm(receiver.getId())).thenReturn(java.util.Optional.of(findPick));

		// * WHEN: 이걸 실행하면
		pickService.updatePickAlarm(receiver, pick.getId());

		// * THEN: 이런 결과가 나와야 한다
		verify(pick).updateAlarm();
		assertThat(pick.isAlarm()).isTrue();
		verify(findPick, times(2)).updateAlarm();
		assertThat(findPick.isAlarm()).isFalse();
	}


	@Test
	@DisplayName("픽의 선택지 리롤을 하면 코인 이벤트가 발생한다.")
	void 픽의_선택지_리롤을_하면_코인_이벤트가_발생한다() {
		pickService.reRoll(createUser("user"));

		verify(publisher).publishEvent(any(PickcoEvent.class));
	}

	private Pick createPick(User sender, User receiver, Question question) {
		return Pick.of(sender, receiver, question);
	}

	private Question createQuestion() {
		QuestionCategory category = QuestionCategory.create("TEST_CATEGORY", "테스트 카테고리");
		return Question.createQuestion(category, "테스트 질문", createUser("author"));
	}
}