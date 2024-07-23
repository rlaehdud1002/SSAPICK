package com.ssapick.server.domain.auth.response;

import com.ssapick.server.domain.user.entity.ProviderType;

public interface OAuth2Response {
   ProviderType getProvider();

   String getProviderId();

   String getEmail();

   String getName();

   String getGender();

   String getImageUrl();
}
