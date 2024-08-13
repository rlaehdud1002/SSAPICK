package com.ssapick.server.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.entity.Notification;
import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.notification.repository.NotificationRepository;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Objects;

import static com.ssapick.server.core.exception.ErrorCode.FCM_TOKEN_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCMService {
    private final EntityManager em;
    private final NotificationRepository notificationRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notification(FCMData.NotificationEvent notificationEvent) {
        // * 만약, 받는 사람이 나를 차단했다면 알람을 전송하지 않는다.
        if (notificationEvent.getReceiver().getBannedUser().stream()
                .anyMatch(banUser -> banUser.getToUser().getId().equals(notificationEvent.getSender().getId()))) {
            log.info("receiver: {} has banned sender: {}", notificationEvent.getReceiver(), notificationEvent.getSender());
            return;
        }

        Message message = Message.builder()
                .setToken(getUserToken(notificationEvent.getReceiver()))
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(notificationEvent.getTitle(), notificationEvent.getMessage(), notificationEvent.getThumbnail()))
                        .build()
                ).build();
        try {
            String fcmId = null;
            if (isAlarmAvailable(notificationEvent.getReceiver(), notificationEvent.getType())) {
                fcmId = FirebaseMessaging.getInstance().sendAsync(message).get();
            }

            notificationRepository.save(Notification.createNotification(
                    notificationEvent.getReceiver(),
                    notificationEvent.getType(),
                    notificationEvent.getNotificationId(),
                    notificationEvent.getTitle(),
                    notificationEvent.getMessage(),
                    fcmId
            ));

            updateSendState(notificationEvent.getType(), notificationEvent.getNotificationId());
        } catch (Exception e) {
//            throw new BaseException(NOTIFICATION_SEND_FAIL, e);
        }
    }

    private boolean isAlarmAvailable(User user, NotificationType type) {
        switch (type) {
            case MESSAGE -> {
                return user.getAlarm().isMessageAlarm();
            }
            case PICK -> {
                return user.getAlarm().isPickAlarm();
            }
            case ADD_QUESTION -> {
                return user.getAlarm().isAddQuestionAlarm();
            }
            case NEARBY -> {
                return user.getAlarm().isNearbyAlarm();
            }
            default -> {
                return false;
            }
        }
    }

    private void updateSendState(NotificationType referenceType, Long referenceId) {
        switch (referenceType) {
            case MESSAGE -> {
                com.ssapick.server.domain.pick.entity.Message message = em.find(com.ssapick.server.domain.pick.entity.Message.class, referenceId);
                message.sendAlarm();
            }
            case PICK -> {
                Pick pick = em.find(Pick.class, referenceId);
                pick.sendAlarm();
            }
            case ADD_QUESTION -> {
                Question question = em.find(Question.class, referenceId);
                question.sendAlarm();
            }
        }
    }

    @Transactional
    public void createUserToken(FCMData.FCMRegister register) {
        log.info("event listener register: {}", register);
        register.getUser().getProfile().updateFcmToken(register.getToken());
    }

    private String getUserToken(User user) {
        String token = user.getProfile().getFcmToken();
        if (Objects.isNull(token)) {
            throw new BaseException(FCM_TOKEN_NOT_FOUND);
        }
        return token;
    }
}
