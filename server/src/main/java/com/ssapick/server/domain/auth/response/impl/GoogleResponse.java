package com.ssapick.server.domain.auth.response.impl;

import java.util.Map;

import com.ssapick.server.domain.auth.response.OAuth2Response;
import com.ssapick.server.domain.user.entity.ProviderType;

public class GoogleResponse implements OAuth2Response {
	private final Map<String, Object> attribute;

	public GoogleResponse(Map<String, Object> attribute) {
		this.attribute = attribute;
	}

	@Override
	public ProviderType getProvider() {
		return ProviderType.GOOGLE;
	}

	@Override
	public String getProviderId() {
		return attribute.get("sub").toString();
	}

	@Override
	public String getEmail() {
		return attribute.get("email").toString();
	}

	@Override
	public String getName() {
		return attribute.get("name").toString();
	}

	@Override
	public String getGender() {
		return "F";
	}

}
