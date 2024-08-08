package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.domain.pick.entity.Message;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageQueryRepository {
    Page<Message> findReceivedMessageByUserId(Long userId, Pageable pageable);

    Page<Message> findSentMessageByUserId(Long userId, Pageable pageable);
}