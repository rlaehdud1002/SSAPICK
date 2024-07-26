package com.ssapick.server.domain.auth.service;

import com.ssapick.server.core.constants.AuthConst;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.auth.dto.MattermostData;
import com.ssapick.server.domain.auth.entity.JwtToken;
import com.ssapick.server.domain.auth.repository.AuthCacheRepository;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                throw new BaseException(ErrorCode.INVALID_REFRESH_TOKEN);
            }

            return jwtService.refreshToken(refreshToken);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.EXPIRED_TOKEN, e);
        }
    }

    @Transactional
    public ProfileData.InitialProfileInfo authenticate(User user, MattermostData.Request request) {
        System.out.println(request);
        try {
            ResponseEntity<MattermostData.Response> response = mattermostConfirmService.authenticate(request);

            // * 테스트 끝나면 주석 풀것
            if (user.isMattermostConfirmed()) {
                throw new BaseException(ErrorCode.ALREADY_AUTHORIZED, "이미 메타모스트 계정이 인증 완료되어 있습니다.");
            }

            log.debug("response: {}", response);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
            }

            user.mattermostConfirm();

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

        } catch (FeignException.Unauthorized e) {
            throw new BaseException(ErrorCode.NOT_FOUND_USER, e);
        }
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

        if (!name.matches("^[가-힣]*$") || locations.contains(name)) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (!name.matches("^[가-힣]*$") || !locations.contains(campusName)) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }

        if (section < 1 || section > 30) {
            throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
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
