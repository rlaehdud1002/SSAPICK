package com.ssapick.server.domain.pick.service;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Repeat;

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
import com.ssapick.server.domain.user.repository.UserRepository;

@WebMvcTest(HintService.class)
@AutoConfigureMockMvc
class HintServiceTest {

	@Autowired
	private HintService hintService;

	@MockBean
	private HintRepository hintRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PickRepository pickRepository;

	@BeforeEach
	void setUp() {
		// 데이터 초기화
		User user = userCreate(1L, "test-user");
		when(hintRepository.findAllByUserId(1L))
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
	void getRandomHintByPickIdTest0() {
		when(pickRepository.findPickWithHintsById(1L)).thenReturn(
			Optional.of(pickCreate(userCreate(1L, "test")))
		);

		// when
		Hint findHint = hintService.getRandomHintByPickId(1L);

		// then
		Assertions.assertThat(findHint.getId()).isNotNull();

	}

	@Test
	@Repeat(10)
	@WithMockUser
	@DisplayName("힌트 오픈이 1 개일때 힌트를 랜덤으로 가져오는 테스트")
	void getRandomHintByPickIdTest1() {

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
		Assertions.assertThat(findHint.getId()).isNotEqualTo(1L);

	}

	@Test
	@WithMockUser
	@DisplayName("힌트 저장 테스트")
	void saveHintTest() {
		// given
		User mockUser = userCreate(1L, "test");
		String hintContent = "장덕동1";
		HintType hintType = HintType.RESIDENTIAL_AREA;
		boolean visibility = false;

		HintData.Create hintCreateRequest = new HintData.Create();
		hintCreateRequest.setUser(mockUser);
		hintCreateRequest.setContent(hintContent);
		hintCreateRequest.setHintType(hintType.name());
		hintCreateRequest.setVisibility(visibility);

		Hint expectedHint = Hint.builder()
			.user(mockUser)
			.content(hintContent)
			.hintType(hintType)
			.visibility(visibility)
			.build();

		when(hintRepository.save(any(Hint.class))).thenReturn(expectedHint);

		// when
		hintService.saveHint(hintCreateRequest);

		// then
		ArgumentCaptor<Hint> hintArgumentCaptor = ArgumentCaptor.forClass(Hint.class);
		verify(hintRepository).save(hintArgumentCaptor.capture());
		Hint savedHint = hintArgumentCaptor.getValue();

		Assertions.assertThat(savedHint.getUser()).isEqualTo(mockUser);
		Assertions.assertThat(savedHint.getContent()).isEqualTo(hintContent);
		Assertions.assertThat(savedHint.getHintType()).isEqualTo(hintType);
		Assertions.assertThat(savedHint.isVisibility()).isEqualTo(visibility);
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
		Assertions.assertThat(hintOpens).hasSize(1);
		Assertions.assertThat(hintOpens.get(0).getHint().getId()).isEqualTo(1L);
	}

	@Test
	@WithMockUser
	@DisplayName("userId로 hint 조회 테스트")
	void getHintsByUserIdTest() {
		// given
		when(hintRepository.findAllByUserId(1L)).thenReturn(List.of(
			hintCreate(1L, "장덕동1", userCreate(1L, "test-user")),
			hintCreate(2L, "장덕동2", userCreate(1L, "test-user")),
			hintCreate(3L, "장덕동3", userCreate(1L, "test-user")),
			hintCreate(4L, "장덕동4", userCreate(1L, "test-user")),
			hintCreate(5L, "장덕동5", userCreate(1L, "test-user")),
			hintCreate(6L, "장덕동6", userCreate(1L, "test-user"))
		));

		// when
		List<Hint> hints = hintService.getHintsByUserId(1L);

		// then
		Assertions.assertThat(hints).hasSize(6);
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
