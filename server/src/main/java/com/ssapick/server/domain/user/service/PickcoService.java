package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickcoService {
    private final PickcoLogRepository pickcoLogRepository;
    private final UserRepository userRepository;
    /**
     * 픽코 이벤트 발생 시 픽코 로그 생성
     * @param event
     */
    @EventListener
    @Transactional(propagation = Propagation.SUPPORTS)
    public void createPickcoLog(PickcoEvent event) {

        Profile profile = event.getUser().getProfile();
        if(profile.getPickco() + event.getAmount() < 0) {
            throw new BaseException(ErrorCode.SHORT_OF_PICKCO);
        }
        profile.changePickco(event.getAmount());

        pickcoLogRepository.save(PickcoLog.createPickcoLog(
                event.getUser(),
                event.getType(),
                event.getAmount(),
                profile.getPickco()));
    }
}
