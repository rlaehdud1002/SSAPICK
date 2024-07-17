package com.ssapick.server.domain.auth.service;

import com.ssapick.server.domain.auth.response.CustomOAuth2User;
import com.ssapick.server.domain.auth.response.OAuth2Response;
import com.ssapick.server.domain.auth.response.UserDTO;
import com.ssapick.server.domain.auth.response.impl.GoogleResponse;
import com.ssapick.server.domain.auth.response.impl.NaverResponse;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        System.out.println(oauth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("registrationId = " + registrationId);
        OAuth2Response oauth2Response = null;


        if (registrationId.equals(ProviderType.NAVER.name().toLowerCase())) {
            oauth2Response = new NaverResponse(oauth2User.getAttributes());
        } else if (registrationId.equals(ProviderType.GOOGLE.name().toLowerCase())) {
            oauth2Response = new NaverResponse(oauth2User.getAttributes());
        } else {
            return null;
        }

        String username = oauth2Response.getEmail();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            User signupUser = User.createUser(
                    username,
                    oauth2Response.getName(),
                    oauth2Response.getProvider(),
                    oauth2Response.getProviderId()
            );

            // TODO 프로필 이미지 추가
            userRepository.save(signupUser);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oauth2Response.getName());
            userDTO.setRole(RoleType.USER.name());

            return new CustomOAuth2User(userDTO);
        } else {
            // TODO: 유저 정보가 바뀌었을 수 있음 업데이트 로직 필요
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oauth2Response.getName());
            userDTO.setRole(user.get().getRoleType().name());

            return new CustomOAuth2User(userDTO);
        }
    }
}
