package com.ssapick.server.domain.notification.controller;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.notification.service.FCMService;
import com.ssapick.server.domain.notification.service.NotificationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final FCMService fcmService;

    @PostMapping("/register")
    public SuccessResponse<Void> saveToken(@CurrentUser User user, @RequestBody FCMData.Register register) {
        fcmService.createUserToken(user, register.getToken());
        return SuccessResponse.empty();
    }


    @GetMapping("/test")
    public SuccessResponse<Void> push() {
        fcmService.notification(FCMData.NotificationEvent.of(
                NotificationType.PICK,
                null,
                1L,
                "테스트 알림",
                "테스트 메시지",
                null
        ));
        return SuccessResponse.empty();
    }
}
