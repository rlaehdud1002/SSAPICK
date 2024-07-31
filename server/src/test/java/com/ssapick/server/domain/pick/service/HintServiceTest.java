package com.ssapick.server.domain.pick.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.core.support.HintServiceTestSupport;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;

@DisplayName("힌트 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class HintServiceTest extends HintServiceTestSupport {
	private static final Logger log = LoggerFactory.getLogger(HintServiceTest.class);
	static User user;
	@InjectMocks
	private HintService hintService;
	@Mock
	private HintRepository hintRepository;
	@Mock
	private PickRepository pickRepository;
	@Mock
	private ApplicationEventPublisher publisher;

	@BeforeEach
	void setUp() {
		// 데이터 초기화
		user = this.createUser();

		lenient().when(hintRepository.findAllByUserId(2L))
			.thenReturn(List.of(
				this.createMockHint(1L, user, "이인준"),
				this.createMockHint(2L, user, "최재원"),
				this.createMockHint(3L, user, "황성민"),
				this.createMockHint(4L, user, "김도영"),
				this.createMockHint(5L, user, "이호영"),
				this.createMockHint(6L, user, "민준수")
			));
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 0 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickId_withOpenHints0() {

		Pick mockPick = this.createMockPick(user);

		log.info("mockPick: {}", mockPick.getId());

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick)
		);

		// when
		String findHint = hintService.getRandomHintByPickId(1L);
		log.info("findHint: {}", findHint);
		// then
		// 첫글자가 X로 시작하는지 확인
		assertThat(findHint.charAt(0)).isEqualTo('X');

	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 1 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickId_withOpenHints1() {

		// given
		Campus campus = Campus.createCampus("광주", (short)2, null);
		Profile profile = Profile.createProfile(user, (short)11, campus);

		Pick mockPick = this.createMockPick(user);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick)
		);

		Hint mockHint = this.createMockHint(1L, user, "장덕동");
		HintOpen mockHintOpen = this.createMockHintOpen(mockHint, mockPick);

		mockPick.getHintOpens().add(mockHintOpen);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when
		String findHint = hintService.getRandomHintByPickId(1L);

		// then
		assertThat(findHint).isNotEqualTo("장덕동");
		verify(publisher).publishEvent(any(PickcoEvent.class));
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 2 개이상 일때 에러 던지는 테스트")
	void getRandomHintByPickId_withOpenHints2() {
		// given
		Pick mockPick = createMockPick(user);
		Hint mockHint1 = this.createMockHint(1L, user, "장덕동");
		Hint mockHint2 = this.createMockHint(1L, user, "장덕동");
		HintOpen mockHintOpen1 = this.createMockHintOpen(mockHint1, mockPick);
		HintOpen mockHintOpen2 = this.createMockHintOpen(mockHint2, mockPick);

		mockPick.getHintOpens().add(mockHintOpen1);
		mockPick.getHintOpens().add(mockHintOpen2);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when
		Runnable runnable = () -> hintService.getRandomHintByPickId(1L);

		// then
		assertThatThrownBy(runnable::run)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("힌트는 2개까지만 열 수 있습니다.");
	}

	@Test
	@WithMockUser
	@DisplayName("pickId로 hintOpens 조회 테스트")
	void getHintOpensByPickIdTest() {
		// given
		Pick mockPick = createMockPick(user);
		Hint mockHint = createMockHint(7L, user, "장덕동1");
		HintOpen mockHintOpen = createMockHintOpen(mockHint, mockPick);

		mockPick.getHintOpens().add(mockHintOpen);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when
		List<HintOpen> hintOpens = hintService.getHintOpensByPickId(1L);

		// then
		assertThat(hintOpens).hasSize(1);
		assertThat(hintOpens.get(0).getHint().getId()).isEqualTo(7L);
	}

	@Test
	@WithMockUser
	@DisplayName("userId로 hint 조회 테스트")
	void getHintsByUserIdTest() {
		// given

		// when
		List<Hint> hints = hintService.getHintsByUserId(2L);

		// then
		assertThat(hints).hasSize(6);
	}

}
