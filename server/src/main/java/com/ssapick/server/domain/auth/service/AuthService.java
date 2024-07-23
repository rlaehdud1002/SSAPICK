package com.ssapick.server.domain.auth.service;

import java.util.Arrays;
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
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.ProfileRepository;
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
	private final ProfileRepository profileRepository;

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
	public void authenticate(User user, MattermostData.Request request) {
		System.out.println(request);
		try {
			ResponseEntity<MattermostData.Response> response = mattermostConfirmService.authenticate(request);

			if (user.isMattermostConfirmed()) {
				throw new IllegalArgumentException("이미 Mattermost 계정이 인증 완료되어 있습니다.");
			}

			if (response.getStatusCode().is2xxSuccessful()) {
				user.mattermostConfirm();
				userRepository.save(user);
			}

			// ================== 가입시 입력한 mm 정보로 유저 정보 가져오기 ==================
			String nickName = response.getBody().getNickname();

			log.debug("nickname: {}", nickName);

			String name = null; // 이름
			String campus = null; // 지역
			String section = null; // 반

			Pattern pattern = Pattern.compile("^(.+?)\\[(.+?)_(.+?)_");
			Matcher matcher = pattern.matcher(nickName);
			if (matcher.find()) {
				name = matcher.group(1);
				campus = matcher.group(2);
				section = matcher.group(3).split("")[0];
			}

			List<String> validCampuses = Arrays.asList("서울", "광주", "부울경", "구미", "대전");

			if (!name.matches("^[가-힣]*$") || validCampuses.contains(name)) {
				throw new IllegalArgumentException("이름 정보가 올바르지 않습니다.");
			}

			if (!validCampuses.contains(campus)) {
				throw new IllegalArgumentException("캠퍼스 정보가 올바르지 않습니다.");
			}

			if (!section.matches("^[0-9]*$")) {
				throw new IllegalArgumentException("반 정보가 올바르지 않습니다.");
			}

			Campus setCampus = Campus.createCampus(campus, Short.parseShort(section), null);

			ProfileData.Update profileData = new ProfileData.Update();

			log.debug("user: {}", user);
			log.debug("campus: {}", setCampus);
			// log.debug("profile: {}", profile);

			// ===============================================

			String token = "Bearer " + response.getHeaders().get("token").get(0);
			String userId = response.getBody().getId();

			byte[] profileImage = mattermostConfirmService.getProfileImage(token, userId);

			// TODO: S3에 이미지 업로드

		} catch (FeignException.Unauthorized exception) {
			throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
		}
	}

	private String signOutKey(String username) {
		return AuthConst.SIGN_OUT_CACHE_KEY + username;
	}
}
