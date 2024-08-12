package com.ssapick.server.domain.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.service.FCMService;
import com.ssapick.server.domain.notification.service.NotificationService;
import com.ssapick.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
	private final NotificationService notificationService;
	private final FCMService fcmService;

	@Authenticated
	@PostMapping("/register")
	public SuccessResponse<Void> saveToken(@CurrentUser User user, @RequestBody FCMData.Register register) {
		log.debug("user: {}, register: {}", user, register);
		fcmService.createUserToken(FCMData.FCMRegister.of(user, register.getToken()));
		return SuccessResponse.empty();
	}

	@Authenticated
	@GetMapping("")
	public SuccessResponse<Page<NotificationData.Search>> list(
		@CurrentUser User user,
		Pageable pageable
	) {
		return SuccessResponse.of(notificationService.list(user.getId(), pageable));
	}

	@Authenticated
	@PostMapping("")
	public SuccessResponse<Void> readAll(@CurrentUser User user) {
		notificationService.readAll(user.getId());
		return SuccessResponse.empty();
	}

}
