package com.ssapick.server.domain.pick.service;

import java.util.List;

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


	/**
	 * 보낸 메시지 조회하기
	 * @param userId
	 * @return
	 */
	public List<MessageData.Search> searchSendMessage(Long userId) {
		return messageRepository.findAllBySender_IdAndIsSenderDeletedFalse(userId).stream()
			.map((Message message) -> MessageData.Search.fromEntity(message, false))
			.toList();
	}

	/**
	 * 받은 메시지 조회하기
	 * @param userId
	 * @return
	 */
	public List<MessageData.Search> searchReceiveMessage(Long userId) {
		return messageRepository.findAllByReceiver_IdAndIsReceiverDeletedFalse(userId).stream()
			.map((Message message) -> MessageData.Search.fromEntity(message, true))
			.toList();
	}

	/**
	 * 메시지 생성하기
	 * @param create
	 */
	public void createMessage(MessageData.Create create) {
		messageRepository.save(Message.of(create));
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
