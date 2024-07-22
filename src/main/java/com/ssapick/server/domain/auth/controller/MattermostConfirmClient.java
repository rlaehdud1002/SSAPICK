package com.ssapick.server.domain.auth.controller;

import com.ssapick.server.domain.auth.dto.MattermostConfirmRequest;
import com.ssapick.server.domain.auth.dto.MattermostConfirmDTO;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


//@FeignClient(name = "loginClient", url = "https://meeting.ssafy.com/api/v4")
public interface MattermostConfirmClient {

    @PostMapping("/users/login")
    MattermostConfirmDTO authenticate(@RequestBody MattermostConfirmRequest loginRequest);
}
