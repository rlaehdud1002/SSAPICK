package com.ssapick.server.domain.auth.service;

import com.ssapick.server.domain.auth.dto.MattermostData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "mattermost-service", url = "https://meeting.ssafy.com/api/v4/users")
public interface MattermostConfirmService {
    @PostMapping("/login")
    ResponseEntity<MattermostData.Response> authenticate(@RequestBody MattermostData.Request request);

    @GetMapping("/{user_id}/image")
    byte[] getProfileImage(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("user_id") String userId);
}
