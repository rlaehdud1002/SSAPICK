package com.ssapick.server.domain.auth.controller;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.auth.dto.MattermostConfirmRequest;
import com.ssapick.server.domain.auth.service.MattermostConfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MattermostAuthenticationController {

    private final MattermostConfirmService mattermostConfirmService;

    @PostMapping("mattermost-confirm")
    public SuccessResponse<Void> authenticate(
            @RequestBody MattermostConfirmRequest request
    ) {
        mattermostConfirmService.authenticate(request);

        return SuccessResponse.empty();
    }
}
