package com.ssapick.server.domain.auth.dto;

import lombok.Getter;

@Getter
public class MattermostConfirmRequest {
    private String login_id;
    private String password;
}
