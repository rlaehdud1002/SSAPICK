package com.ssapick.server.domain.pick.repository;

import java.util.List;

import com.ssapick.server.domain.pick.entity.Message;

public interface MessageRepositoryCustom {
	List<Message> findReceivedMessageByUserId(Long userId);
	List<Message> findSentMessageByUserId(Long userId);
}