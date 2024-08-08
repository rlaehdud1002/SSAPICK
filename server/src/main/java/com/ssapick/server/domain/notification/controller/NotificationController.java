package com.ssapick.server.domain.notification.controller;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.service.FCMService;
import com.ssapick.server.domain.notification.service.NotificationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/list")
	public SuccessResponse<List<NotificationData.Search>> list(@CurrentUser User user) {
		return SuccessResponse.of(notificationService.list(user.getId()));
	}
}
