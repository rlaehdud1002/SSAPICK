package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

	final MessageRepository messageRepository;


	public void createMessage(MessageData.Create create) {
		messageRepository.save(Message.of(create));
	}

	/**
	 * 보낸 메시지 조회하기
	 * @param userId
	 * @return
	 */
	public List<MessageData.SearchSend> searchSendMessage(Long userId) {
		List<Message> sendMessages = messageRepository.findAllByFromUserId(userId);

		List<MessageData.SearchSend> result = sendMessages.stream()
			.map(MessageData.SearchSend::fromEntity)
			.collect(Collectors.toList());

		return result;
	}

	/**
	 * 받은 메시지 조회하기
	 * @param userId
	 * @return
	 */
	public List<MessageData.SearchReceive> searchReceiveMessage(Long userId) {
		List<Message> receiveMessages = messageRepository.findAllByToUserId(userId);

		List<MessageData.SearchReceive> result = receiveMessages.stream()
			.map(MessageData.SearchReceive::fromEntity)
			.collect(Collectors.toList());

		return result;
	}

	/**
	 * 받은 메시지 삭제하기
	 * @param messageId
	 */
	@Transactional
	public void deleteFromMessage(Long messageId) {
		Message message = messageRepository.findById(messageId)
			.orElseThrow();
		message.deleteFromMessage();
	}

	/**
	 * 보낸 메시지 삭제하기
	 * @param messageId
	 */
	@Transactional
	public void deleteToMessage(Long messageId) {
		Message message = messageRepository.findById(messageId).
			orElseThrow();
		message.deleteToMessage();
	}
}
