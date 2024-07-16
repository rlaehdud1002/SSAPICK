package com.ssapick.server.domain.auth.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class OAuth2Response {
    private final Map<String, Object> attributes;

    public abstract String getProvider();

    public abstract String getProviderId();

    public abstract String getEmail();

    public abstract String getName();

    public abstract String getImageUrl();
}
