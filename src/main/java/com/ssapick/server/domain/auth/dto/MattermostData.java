package com.ssapick.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

public class MattermostData {
    @Getter
    @ToString
    public static class Request {
        @JsonProperty("login_id")
        private String loginId;
        private String password;
    }

    @Data
    public static class Response {
        private String id;
        private String username;
        private String email;
        private String nickname;
        private String first_name;
        private String last_name;
        private String locale;
    }
}
