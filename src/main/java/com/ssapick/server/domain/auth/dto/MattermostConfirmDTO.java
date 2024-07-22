package com.ssapick.server.domain.auth.dto;

import lombok.Data;

@Data
public class MattermostConfirmDTO {
    private String id;
    private String username;
    private String email;
    private String nickname;
    private String first_name;
    private String last_name;
    private String locale;
}
