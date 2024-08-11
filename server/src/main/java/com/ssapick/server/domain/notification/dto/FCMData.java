package com.ssapick.server.domain.notification.dto;

import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

public class FCMData {
    @Data
    @AllArgsConstructor(staticName = "of")
    public static class NotificationEvent {
        private NotificationType type;
        private User user;
        private Long notificationId;
        private String title;
        private String message;
        private String thumbnail;
    }

    @Data
    public static class Register {
        private String token;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class FCMRegister {
        private User user;
        private String token;
    }
}
