package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickcoService {
    private final PickcoLogRepository pickcoLogRepository;


    /**
     * 픽코 이벤트 발생 시 픽코 로그 생성
     * @param event
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createPickcoLog(PickcoEvent event) {
        log.debug("신규 픽코 발생 / 사용: {}, 타입: {}, 금액: {}, 현재: {}", event.getUser().getUsername(), event.getType(), event.getAmount(), event.getCurrent());

        Profile profile = event.getUser().getProfile();
        if(profile.getPickco() + event.getAmount() < 0) {
            throw new IllegalArgumentException("픽코가 부족합니다.");
        }
        profile.changePickco(event.getAmount());

        pickcoLogRepository.save(event.toEntity());
    }
}
