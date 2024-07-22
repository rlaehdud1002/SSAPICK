package com.ssapick.server.domain.auth.service;

import com.ssapick.server.domain.auth.dto.MattermostData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "mattermost-service", url = "https://meeting.ssafy.com/api/v4")
public interface MattermostConfirmService {
    @PostMapping("/users/login")
    MattermostData.Response authenticate(@RequestBody MattermostData.Request request);
}
