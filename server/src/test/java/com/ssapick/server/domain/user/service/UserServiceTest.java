package com.ssapick.server.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.service.S3Service;
import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.user.dto.UserData;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

@DisplayName("유저 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest extends UserSupport {

	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private S3Service s3Service;

	static User user;

	@Test
	@WithMockUser
	@DisplayName("힌트_아이디가_NULL_인_유효한_데이터로_힌트_저장_테스트")
	void 힌트_아이디가_NULL_인_유효한_데이터로_힌트_저장_테스트() {
		// given
		MockMultipartFile mockMultipartFile =
			new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());
		user = this.createUser();
		UserData.Update createMockHintCreateData = UserData.Update.of(
			"이인준",
			'M',
			(short)11,
			"광주",
			(short)2,
			"INTP",
			"전공",
			"1998-08-25",
			"장덕동",
			"취미");

		CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("https://test-profile.com");

		when(s3Service.upload(mockMultipartFile)).thenReturn(completableFuture);
		when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
		
		CompletableFuture<String> result = s3Service.upload(mockMultipartFile);

		// when

		userService.updateUser(user.getId(), createMockHintCreateData, mockMultipartFile);

		// then
		List<Hint> hints = user.getHints();
		// Validate profile
		Profile profile = user.getProfile();

		assertThat(profile).isNotNull();
		assertThat(profile.getCampus()).isNotNull();
		assertThat(profile.getCampus().getName()).isEqualTo("광주");
		assertThat(profile.getCampus().getSection()).isEqualTo((short)1);
		assertThat(profile.getCohort()).isEqualTo((short)1);
		assertThat(profile.getProfileImage()).isEqualTo("https://test-profile.com");

		assertThat(hints).extracting(Hint::getContent)
			.containsExactlyInAnyOrder(
				"이인준", "M", "11", "광주", "2", "INTP", "전공", "1998-08-25", "장덕동", "취미"
			);

	}

	@Test
	@WithMockUser
	@DisplayName("힌트 ID가 널이 아닌 유효한 데이터로 힌트 업데이트 테스트")
	void updateHint() {
		// given

		MockMultipartFile mockMultipartFile =
			new MockMultipartFile("file", "test.jpg", "image/jpeg", "test image".getBytes());

		user = this.createUser();
		UserData.Update createMockHintCreateData = UserData.Update.of(
			"이인준",
			'M',
			(short)11,
			"광주",
			(short)2,
			"INTP",
			"전공",
			"1998-08-25",
			"장덕동",
			"취미"
		);

		UserData.Update createMockHintCreateData2 = UserData.Update.of(
			"이인준",
			'M',
			(short)11,
			"광주",
			(short)2,
			"INTP",
			"전공",
			"1998-08-25",
			"장덕동 ",
			"풋살"
		);

		CompletableFuture<String> completableFuture = CompletableFuture.completedFuture("https://test-profile.com");

		when(s3Service.upload(mockMultipartFile)).thenReturn(completableFuture);
		when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
		CompletableFuture<String> result = s3Service.upload(mockMultipartFile);

		// when
		userService.updateUser(user.getId(), createMockHintCreateData, mockMultipartFile);
		userService.updateUser(user.getId(), createMockHintCreateData2, mockMultipartFile);

		// then
		List<Hint> hints = user.getHints();
		Profile profile = user.getProfile();

		assertThat(profile).isNotNull();
		assertThat(profile.getCampus()).isNotNull();
		assertThat(profile.getCampus().getName()).isEqualTo("광주");
		assertThat(profile.getCampus().getSection()).isEqualTo((short)1);
		assertThat(profile.getCohort()).isEqualTo((short)1);
		assertThat(profile.getProfileImage()).isEqualTo("https://test-profile.com");

		assertThat(hints).extracting(Hint::getContent)
			.containsExactlyInAnyOrder(
				"이인준", "M", "11", "광주", "2", "INTP", "전공", "1998-08-25", "장덕동 ", "풋살"
			);

	}

	@Test
	@WithMockUser
	@DisplayName("유저 정보가 없을 때 예외 발생 테스트")
	void saveHint_withNullUser() {
		// given
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test.jpg", "image/jpeg",
			"test image".getBytes());

		user = this.createUser();
		UserData.Update hintWithNullUser = UserData.Update.of(
			null,
			'M',
			(short)11,
			"광주",
			(short)2,
			"INTP",
			"전공",
			"1998-08-25",
			"장덕동",
			"취미"
		);

		// when
		Runnable runnable = () -> userService.updateUser(null, hintWithNullUser, mockMultipartFile);

		// then
		assertThatThrownBy(runnable::run)
			.isInstanceOf(BaseException.class)
			.hasMessageContaining("사용자를 찾을 수 없습니다.");
	}

}
