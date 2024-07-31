package com.ssapick.server.domain.auth.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.util.MultipartFileConverter;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.S3UploadEvent;
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
	private final ApplicationEventPublisher publisher;

	@Transactional
	public void signOut(User user, String refreshToken) {
		if (authCacheRepository.existsByUsername(signOutKey(user.getUsername()))) {
			throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
		}

		authCacheRepository.save(signOutKey(user.getUsername()), refreshToken);
	}

	public JwtToken refresh(String refreshToken) {
		try {
			String username = jwtService.getUsername(refreshToken);

			if (authCacheRepository.existsByUsername(signOutKey(username))) {
				throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
			}

			return jwtService.refreshToken(refreshToken);
		} catch (Exception e) {
			throw new BaseException(ErrorCode.EXPIRED_TOKEN, e);
		}
	}

	@Transactional
	public ProfileData.InitialProfileInfo authenticate(User user, MattermostData.Request request) {
		try {
			ResponseEntity<MattermostData.Response> response = mattermostConfirmService.authenticate(request);
			MattermostData.Response body = response.getBody();

			if (user.isMattermostConfirmed()) {
				throw new BaseException(ErrorCode.ALREADY_AUTHORIZED, "이미 메타모스트 계정이 인증 완료되어 있습니다.");
			}

			if (!response.getStatusCode().is2xxSuccessful() || body == null) {
				throw new BaseException(ErrorCode.INVALID_MATTERMOST_INFO);
			}

			user.mattermostConfirm();

			String nickname = body.getNickname();
			ProfileData.InitialProfileInfo info = getInitialProfileInfo(nickname);

			String token = "Bearer " + response.getHeaders().get("Token").get(0);
			ResponseEntity<byte[]> profileImageByte = mattermostConfirmService.getProfileImage(token,
				body.getId());

			String fileName = info.getName() + ".png";
			String contentType = "image/png";

			MultipartFile profileImage = MultipartFileConverter.convertToFile(profileImageByte.getBody(), fileName,
				contentType);

			publisher.publishEvent(new S3UploadEvent(user.getProfile(), profileImage));

			return info;

		} catch (FeignException.Unauthorized e) {
			throw new BaseException(ErrorCode.NOT_FOUND_USER, e);
		}
	}

	@Transactional
	public void deleteUser(User user) {
		user.delete();
	}

	private ProfileData.InitialProfileInfo getInitialProfileInfo(String nickName) {
		Pattern pattern = Pattern.compile("^(.+?)\\[(.+?)_(.+?)");
		Matcher matcher = pattern.matcher(nickName);

		log.debug("nickName: {}", nickName);
		boolean isMatch = matcher.find();
		log.debug("isMatch: {}", isMatch);

		if (!isMatch) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		String name = matcher.group(1); // 구글은 이름이 커스텀 가능하니까 MM 이름 보내줘야할 듯
		String campusName = matcher.group(2);
		short section = Short.parseShort(matcher.group(3).split("")[0]);

		List<String> locations = List.of("서울", "대전", "구미", "광주", "부울경");

		if (!isKorean(name) || !isKorean(campusName) || locations.contains(name) || !locations.contains(campusName)
			|| section < 1 || section > 30) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		ProfileData.InitialProfileInfo initialProfileInfo = new ProfileData.InitialProfileInfo();
		initialProfileInfo.setName(name);
		initialProfileInfo.setLocation(campusName);
		initialProfileInfo.setSection(section);
		return initialProfileInfo;
	}

	private boolean isKorean(String name) {
		return name.matches("^[가-힣]*$");
	}

	private String signOutKey(String username) {
		return AuthConst.SIGN_OUT_CACHE_KEY + username;
	}

}
