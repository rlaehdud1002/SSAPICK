package com.ssapick.server.domain.auth.service;

import java.util.concurrent.atomic.AtomicBoolean;

import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.event.PickcoEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ssapick.server.domain.auth.response.CustomOAuth2User;
import com.ssapick.server.domain.auth.response.CustomOAuthUserFactory;
import com.ssapick.server.domain.auth.response.OAuth2Response;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private final UserRepository userRepository;
	private final ApplicationEventPublisher publisher;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);
		log.debug("oauth2User: {}", oauth2User.getAttributes());

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		log.debug("registrationId: {}", registrationId);
		ProviderType providerType = ProviderType.valueOf(registrationId.toUpperCase());
		OAuth2Response oauth2Response = CustomOAuthUserFactory.parseOAuth2Response(providerType,
			oauth2User.getAttributes());

		String username = oauth2Response.getEmail();
		AtomicBoolean isNew = new AtomicBoolean(false);
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			isNew.set(true);
			return userRepository.save(User.createUser(
				username,
				oauth2Response.getName(),
				oauth2Response.getGender().charAt(0),
				oauth2Response.getProvider(),
				oauth2Response.getProviderId()
			));
		});

		if (isNew.get()) {
			log.debug("New user has been created: {}", user);
		}

		publisher.publishEvent(new PickcoEvent(user, PickcoLogType.SIGN_UP, 100));

		return new CustomOAuth2User(user);
	}
}
