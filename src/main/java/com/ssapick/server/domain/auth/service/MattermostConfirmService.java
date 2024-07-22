package com.ssapick.server.domain.auth.service;

import com.ssapick.server.domain.auth.controller.MattermostConfirmClient;
import com.ssapick.server.domain.auth.dto.MattermostConfirmRequest;
import com.ssapick.server.domain.auth.dto.MattermostConfirmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MattermostConfirmService {

    private final MattermostConfirmClient mattermostConfirmClient;

    public MattermostConfirmDTO authenticate(MattermostConfirmRequest request) {
        return mattermostConfirmClient.authenticate(request);
    }
}
