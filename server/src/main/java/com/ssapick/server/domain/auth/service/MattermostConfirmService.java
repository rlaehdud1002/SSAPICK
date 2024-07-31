package com.ssapick.server.domain.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ssapick.server.domain.auth.dto.MattermostData;

@FeignClient(name = "mattermost-service", url = "https://meeting.ssafy.com/api/v4/users")
public interface MattermostConfirmService {
	@PostMapping("/login")
	ResponseEntity<MattermostData.Response> authenticate(@RequestBody MattermostData.Request request);

	@GetMapping("/{user_id}/image")
	ResponseEntity<byte[]> getProfileImage(@RequestHeader("Authorization") String authorizationHeader,
		@PathVariable("user_id") String userId);
}
