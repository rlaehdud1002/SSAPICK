package com.ssapick.server.domain.auth.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
	private final JWTService jwtService;
	private final AuthCacheRepository authCacheRepository;
	private final MattermostConfirmService mattermostConfirmService;
	private final UserRepository userRepository;

	@Transactional
	public void signOut(User user, String refreshToken) {
		if (authCacheRepository.existsByUsername(signOutKey(user.getUsername()))) {
			throw new IllegalArgumentException("이미 로그아웃된 사용자입니다.");
		}

		authCacheRepository.save(signOutKey(user.getUsername()), refreshToken);
	}

	public JwtToken refresh(String refreshToken) {
		try {
			String username = jwtService.getUsername(refreshToken);

			if (authCacheRepository.existsByUsername(signOutKey(username))) {
				throw new IllegalArgumentException("로그아웃된 사용자입니다.");
			}

			return jwtService.refreshToken(refreshToken);
		} catch (Exception e) {
			throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
		}
	}

	@Transactional
	public ProfileData.InitialProfileInfo authenticate(User user, MattermostData.Request request) {
		System.out.println(request);
		try {
			ResponseEntity<MattermostData.Response> response = mattermostConfirmService.authenticate(request);

			// * 테스트 끝나면 주석 풀것
			// if (user.isMattermostConfirmed()) {
			// 	throw new IllegalArgumentException("이미 Mattermost 계정이 인증 완료되어 있습니다.");
			// }

			log.debug("response: {}", response);

			if (!response.getStatusCode().is2xxSuccessful()) {
				throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
			}

			user.mattermostConfirm();
			userRepository.save(user);

			String nickName = response.getBody().getNickname();
			ProfileData.InitialProfileInfo initialProfileInfo = getInitialProfileInfo(nickName);

			// String token = "Bearer " + response.getHeaders().get("token").get(0);
			// String userId = response.getBody().getId();
			//
			// byte[] profileImage = mattermostConfirmService.getProfileImage(token, userId);

			// TODO: S3에 이미지 업로드

			// String birthDay = null;
			// String birthYear = null;
			//
			// if (user.getProviderType().equals(ProviderType.NAVER)) {
			// 	birthDay =
			// 	birthYear =
			// }

			return initialProfileInfo;

		} catch (FeignException.Unauthorized exception) {
			throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
		}
	}

	private ProfileData.InitialProfileInfo getInitialProfileInfo(String nickName) {
		Pattern pattern = Pattern.compile("^(.+?)\\[(.+?)_(.+?)");
		Matcher matcher = pattern.matcher(nickName);

		log.info("nickName: {}", nickName);
		boolean isMatch = matcher.find();
		log.info("isMatch: {}", isMatch);

		if (!isMatch) {
			throw new IllegalArgumentException("형식이 올바르지 않습니다.");
		}

		String name = matcher.group(1); // 구글은 이름이 커스텀 가능하니까 MM 이름 보내줘야할 듯
		String campusName = matcher.group(2);
		short section = Short.parseShort(matcher.group(3).split("")[0]);

		List<String> locations = List.of("서울", "대전", "구미", "광주", "부울경");

		if (!name.matches("^[가-힣]*$") || locations.contains(name)) {
			throw new IllegalArgumentException("형식이 올바르지 않습니다.");
		}

		if (!name.matches("^[가-힣]*$") || !locations.contains(campusName)) {
			throw new IllegalArgumentException("형식이 올바르지 않습니다.");
		}

		if (section < 1 || section > 30) {
			throw new IllegalArgumentException("형식이 올바르지 않습니다.");
		}

		ProfileData.InitialProfileInfo initialProfileInfo = new ProfileData.InitialProfileInfo();
		initialProfileInfo.setName(name);
		initialProfileInfo.setLocation(campusName);
		initialProfileInfo.setSection(section);
		return initialProfileInfo;
	}

	private String signOutKey(String username) {
		return AuthConst.SIGN_OUT_CACHE_KEY + username;
	}
}
