package com.ssapick.server.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

public class MattermostData {
    @Data
    public static class Request {
        @JsonProperty("login_id")
        private String loginId;
        private String password;

        public Request(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }
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

    @Data
    @AllArgsConstructor
    public static class Authenticated {
        private boolean authenticated;
    }
}
