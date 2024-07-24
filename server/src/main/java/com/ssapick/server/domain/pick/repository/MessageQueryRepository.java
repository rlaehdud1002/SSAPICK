package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.domain.pick.entity.Message;

import java.util.List;

public interface MessageQueryRepository {
    List<Message> findReceivedMessageByUserId(Long userId);

    List<Message> findSentMessageByUserId(Long userId);
}