package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.util.MultipartFileConverter;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.S3UploadEvent;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private final CampusRepository campusRepository;

	@Transactional
	public void signOut(User user, String refreshToken) {
		if (authCacheRepository.existsByUsername(getSignOutKey(user.getUsername()))) {
			throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
		}
		authCacheRepository.save(getSignOutKey(user.getUsername()), refreshToken);
	}

	public Boolean isUserAuthenticated(User user) {
		return userRepository.isUserAuthenticated(user.getId());
	}

	public JwtToken refresh(String refreshToken) {
		try {
			String username = jwtService.getUsername(refreshToken);

			if (authCacheRepository.existsByUsername(getSignOutKey(username))) {
				throw new BaseException(ErrorCode.EXPIRED_REFRESH_TOKEN);
			}

			return jwtService.refreshToken(refreshToken);
		} catch (Exception e) {
			throw new BaseException(ErrorCode.EXPIRED_TOKEN, e);
		}
	}

	@Transactional
	public void authenticate(User user, MattermostData.Request request) {
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
			ProfileData.InitialProfileInfo info = extractProfileInfo(body.getNickname());
			user.updateName(info.getName());

			Campus campus = getOrCreateCampus(info.getLocation(), info.getSection());
			user.getProfile().updateCampus(campus);

			userRepository.save(user);

			MultipartFile profileImage = getProfileImage(response, body.getId(), info.getName());

			publisher.publishEvent(new S3UploadEvent(user.getProfile(), profileImage));

		} catch (FeignException.Unauthorized e) {
			throw new BaseException(ErrorCode.NOT_FOUND_USER, e);
		}
	}

	private Campus getOrCreateCampus(String name, Short section) {
		return campusRepository.findByNameAndSection(name, section)
			.orElseGet(() -> campusRepository.save(Campus.createCampus(name, section, null)));
	}

	@Transactional
	public void deleteUser(User user) {
		user.delete();
	}

	private ProfileData.InitialProfileInfo extractProfileInfo(String nickName) {
		Matcher matcher = getNicknameMatcher(nickName);
		if (!matcher.find()) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}

		String name = matcher.group(1);
		String campusName = matcher.group(2);
		short section = Short.parseShort(matcher.group(3).split("")[0]);

		validateProfileInfo(name, campusName, section);

		return buildProfileInfo(name, campusName, section);
	}

	private Matcher getNicknameMatcher(String nickName) {
		Pattern pattern = Pattern.compile("^(.+?)\\[(.+?)_(.+?)");
		return pattern.matcher(nickName);
	}

	private void validateProfileInfo(String name, String campusName, short section) {
		List<String> locations = List.of("서울", "대전", "구미", "광주", "부울경");

		if (!isKorean(name) || !isKorean(campusName) || locations.contains(name) || !locations.contains(campusName)
			|| section < 1 || section > 30) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	private ProfileData.InitialProfileInfo buildProfileInfo(String name, String campusName, short section) {
		ProfileData.InitialProfileInfo initialProfileInfo = new ProfileData.InitialProfileInfo();
		initialProfileInfo.setName(name);
		initialProfileInfo.setLocation(campusName);
		initialProfileInfo.setSection(section);
		return initialProfileInfo;
	}

	private MultipartFile getProfileImage(ResponseEntity<MattermostData.Response> response, String userId,
		String name) {
		String token = "Bearer " + response.getHeaders().get("Token").get(0);
		ResponseEntity<byte[]> profileImageByte = mattermostConfirmService.getProfileImage(token, userId);

		String fileName = name + ".png";
		String contentType = "image/png";

		return MultipartFileConverter.convertToFile(profileImageByte.getBody(), fileName, contentType);
	}

	private boolean isKorean(String text) {
		return text.matches("^[가-힣]*$");
	}

	private String getSignOutKey(String username) {
		return AuthConst.SIGN_OUT_CACHE_KEY + username;
	}
}
