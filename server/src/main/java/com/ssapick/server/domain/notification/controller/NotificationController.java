package com.ssapick.server.domain.notification.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.dto.NotificationData;
import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.notification.service.FCMService;
import com.ssapick.server.domain.notification.service.NotificationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
	private final NotificationService notificationService;
	private final FCMService fcmService;
	private final ApplicationEventPublisher publisher;

	@Authenticated
	@PostMapping("/register")
	public SuccessResponse<Void> saveToken(@CurrentUser User user, @RequestBody FCMData.Register register) {
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

	@GetMapping("/test")
	@Transactional
	public SuccessResponse<Void> success(@CurrentUser User user) {
		log.debug("run this controller");
		publisher.publishEvent(FCMData.NotificationEvent.of(
				NotificationType.PICK,
				user,
				user,
				1L,
				"누군가가 당신을 선택했어요!",
				"선택완료",
				null
		));
		return SuccessResponse.empty();
	}
}
