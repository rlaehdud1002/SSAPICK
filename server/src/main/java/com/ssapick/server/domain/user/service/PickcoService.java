package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PickcoService {
    private final PickcoLogRepository pickcoLogRepository;
    private final UserRepository userRepository;
    /**
     * 픽코 이벤트 발생 시 픽코 로그 생성
     * @param event
     */
    @EventListener
    public void createPickcoLog(PickcoEvent event) {
        User user = userRepository.findByUsername(event.getUser().getUsername()).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_USER));

        Profile profile = user.getProfile();
        if(profile.getPickco() + event.getAmount() < 0) {
            throw new BaseException(ErrorCode.SHORT_OF_PICKCO);
        }
        profile.changePickco(event.getAmount());

        pickcoLogRepository.save(PickcoLog.createPickcoLog(
                user,
                event.getType(),
                event.getAmount(),
                profile.getPickco()));
    }
}
