package com.ssapick.server.domain.auth.response.impl;

import java.util.Map;

import com.ssapick.server.domain.auth.response.OAuth2Response;
import com.ssapick.server.domain.user.entity.ProviderType;

public class KakaoResponse implements OAuth2Response {
	private final Map<String, Object> attribute;
	private final Map<String, Object> properties;
	private final Map<String, Object> kakaoAccount;

	public KakaoResponse(Map<String, Object> attributes) {
		this.attribute = attributes;
		this.properties = (Map<String, Object>)attributes.get("properties");
		this.kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.KAKAO;
	}

	@Override
	public String getProviderId() {
		return attribute.get("id").toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getName() {
		return properties.get("nickname").toString();
	}

	@Override
	public String getGender() {
		return "M";
	}

}