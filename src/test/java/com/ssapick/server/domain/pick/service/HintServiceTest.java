package com.ssapick.server.domain.pick.service;

import com.ssapick.server.domain.pick.dto.HintData;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintOpen;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.HintRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Logger;

@ExtendWith(MockitoExtension.class)
class HintServiceTest {
	@InjectMocks
	private HintService hintService;

	@Mock
	private HintRepository hintRepository;

	@Mock
	private PickRepository pickRepository;

	static User user;
	private Logger log;

	@BeforeEach
	void setUp() {
		// 데이터 초기화
		user = userCreate(1L, "test-user");
		
		lenient().when(hintRepository.findAllByUserId(1L))
			.thenReturn(List.of(
				hintCreate(1L, "장덕동1", user),
				hintCreate(2L, "장덕동2", user),
				hintCreate(3L, "장덕동3", user),
				hintCreate(4L, "장덕동4", user),
				hintCreate(5L, "장덕동5", user),
				hintCreate(6L, "장덕동6", user)
		));
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 0 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickId_withOpenHints0() {
		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(pickCreate(userCreate(1L, "test")))
		);

		// when
		Hint findHint = hintService.getRandomHintByPickId(1L);

		// then
		assertThat(findHint.getId()).isBetween(1L, 6L);

	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 1 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickId_withOpenHints1() {

		User mockUser = userCreate(1L, "test");
		Pick mockPick = pickCreate(mockUser);
		Hint mockHint = hintCreate(1L, "장덕동1", mockUser);
		HintOpen mockHintOpen = hintOpencreate(mockHint, mockPick);

		mockPick.getHintOpens().add(mockHintOpen);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when
		Hint findHint = hintService.getRandomHintByPickId(1L);

		// then
		assertThat(findHint.getId()).isNotEqualTo(1L);
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 오픈이 2 개이상 일때 에러 던지는 테스트")
	void getRandomHintByPickId_withOpenHints2() {
		// given
		Pick mockPick = pickCreate(user);
		Hint mockHint1 = hintCreate(1L, "장덕동1", user);
		Hint mockHint2 = hintCreate(2L, "장덕동2", user);
		HintOpen mockHintOpen1 = hintOpencreate(mockHint1, mockPick);
		HintOpen mockHintOpen2 = hintOpencreate(mockHint2, mockPick);

		mockPick.getHintOpens().add(mockHintOpen1);
		mockPick.getHintOpens().add(mockHintOpen2);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when & then
		assertThatThrownBy(() -> hintService.getRandomHintByPickId(1L))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("힌트는 2개까지만 열 수 있습니다.");
	}

	@Test
	@WithMockUser
	@DisplayName("hintId가 null인 유효한 데이터로 힌트 저장 테스트")
	void saveHint() {
		// given
		List<HintData.Create> validHints = List.of(
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true)
		);

		// when
		hintService.saveHint(validHints);

		// then
		List<Hint> expectedHints = validHints.stream()
			.map(HintData.Create::toEntity)
			.collect(Collectors.toList());

		ArgumentCaptor<List<Hint>> captor = ArgumentCaptor.forClass(List.class);

		verify(hintRepository).saveAll(captor.capture());

		List<Hint> capturedHints = captor.getValue();

		assertThat(capturedHints.get(0).getUser().getId()).isEqualTo(expectedHints.get(0).getUser().getId());
		assertThat(capturedHints.get(0).getContent()).isEqualTo(expectedHints.get(0).getContent());
	}

	@Test
	@WithMockUser
	@DisplayName("hintId가 null이 아닌 유효한 데이터로 힌트 업데이트 테스트")
	void updateHint() {
		// given
		List<HintData.Create> validHints = List.of(
			new HintData.Create(1L, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true)
		);

		// when
		hintService.saveHint(validHints);

		// then
		List<Hint> expectedHints = validHints.stream()
			.map(HintData.Create::toEntity)
			.collect(Collectors.toList());

		ArgumentCaptor<List<Hint>> captor = ArgumentCaptor.forClass(List.class);

		verify(hintRepository).saveAll(captor.capture());

		List<Hint> capturedHints = captor.getValue();

		log.debug("capturedHints: {}", capturedHints);
		log.debug("expectedHints: {}", expectedHints);

		assertThat(capturedHints.get(0).getUser().getId()).isEqualTo(expectedHints.get(0).getUser().getId());
		assertThat(capturedHints.get(0).getContent()).isEqualTo(expectedHints.get(0).getContent());

	}

	@Test
	@WithMockUser
	@DisplayName("유저 정보가 없을 때 예외 발생 테스트")
	void saveHint_withNullUser() {
		// given
		List<HintData.Create> hintsWithNullUser = List.of(
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, null, "힌트 내용", HintType.RESIDENTIAL_AREA, true)
		);

		// when & then
		assertThatThrownBy(() -> hintService.saveHint(hintsWithNullUser))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("유저 정보가 없습니다.");
	}

	@Test
	@WithMockUser
	@DisplayName("힌트 내용이 없을 때 예외 발생 테스트")
	void saveHint_withNullContent() {
		// given
		List<HintData.Create> hintsWithNullContent = List.of(
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, "힌트 내용", HintType.RESIDENTIAL_AREA, true),
			new HintData.Create(null, user, null, HintType.RESIDENTIAL_AREA, true)
		);

		// when & then
		assertThatThrownBy(() -> hintService.saveHint(hintsWithNullContent))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("힌트 내용이 없습니다.");
	}

	@Test
	@WithMockUser
	@DisplayName("pickId로 hintOpens 조회 테스트")
	void getHintOpensByPickIdTest() {
		// given
		User mockUser = userCreate(1L, "test");
		Pick mockPick = pickCreate(mockUser);
		Hint mockHint = hintCreate(1L, "장덕동1", mockUser);
		HintOpen mockHintOpen = hintOpencreate(mockHint, mockPick);

		mockPick.getHintOpens().add(mockHintOpen);

		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(mockPick));

		// when
		List<HintOpen> hintOpens = hintService.getHintOpensByPickId(1L);

		// then
		assertThat(hintOpens).hasSize(1);
		assertThat(hintOpens.get(0).getHint().getId()).isEqualTo(1L);
	}

	@Test
	@WithMockUser
	@DisplayName("userId로 hint 조회 테스트")
	void getHintsByUserIdTest() {
		// given

		// when
		List<Hint> hints = hintService.getHintsByUserId(1L);

		// then
		assertThat(hints).hasSize(6);
	}

	private HintOpen hintOpencreate(Hint mockHint, Pick mockPick) {
		return HintOpen.builder().hint(mockHint).pick(mockPick).build();
	}

	private Pick pickCreate(User mockUser) {
		return Pick.builder().fromUser(mockUser).build();
	}

	private User userCreate(Long id, String username) {
		return User.builder()
			.id(id)
			.username(username)
			.name("이름")
			.email("이메일")
			.providerType(ProviderType.GOOGLE)
			.roleType(RoleType.USER)
			.providerId("프로바이더 아이디")
			.isEmailVerified(true)
			.isLocked(false)
			.build();
	}

	private Hint hintCreate(Long id, String content, User user) {
		return Hint.builder()
			.id(id)
			.content(content)
			.user(user)
			.hintType(HintType.RESIDENTIAL_AREA)
			.visibility(false)
			.build();
	}

}
