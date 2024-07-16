package com.ssapick.server.domain.auth.response.impl;

import com.ssapick.server.domain.auth.response.OAuth2Response;
import com.ssapick.server.domain.user.entity.ProviderType;

import java.util.Map;

public class NaverResponse extends OAuth2Response {
    public NaverResponse(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getProvider() {
        return ProviderType.NAVER.name();
    }

    @Override
    public String getProviderId() {
        return super.getAttributes().get("id").toString();
    }

    @Override
    public String getEmail() {
        return super.getAttributes().get("email").toString();
    }

    @Override
    public String getName() {
        return super.getAttributes().get("name").toString();
    }

    @Override
    public String getImageUrl() {
        return super.getAttributes().get("profile_image").toString();
    }
}
