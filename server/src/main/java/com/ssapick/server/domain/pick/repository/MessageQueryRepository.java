package com.ssapick.server.domain.pick.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.pick.entity.Message;

public interface MessageQueryRepository {

	Page<Message> findReceivedMessageByUserId(Long userId, Pageable pageable);

	Page<Message> findSentMessageByUserId(Long userId, Pageable pageable);
}