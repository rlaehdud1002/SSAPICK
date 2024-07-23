package com.ssapick.server.domain.pick.service;

import java.util.List;

import com.ssapick.server.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

	final MessageRepository messageRepository;
	final PickRepository pickRepository;


	/**
	 * 보낸 메시지 조회하기
	 * @param user
	 * @return
	 */
	public List<MessageData.Search> searchSendMessage(User user) {
		return messageRepository.findSentMessageByUserId(user.getId()).stream()
				.map((message) -> MessageData.Search.fromEntity(message, false))
				.toList();
	}

	/**
	 * 받은 메시지 조회하기
	 * @param user
	 * @return
	 */
	public List<MessageData.Search> searchReceiveMessage(User user) {
		return messageRepository.findReceivedMessageByUserId(user.getId()).stream()
				.map((message) -> MessageData.Search.fromEntity(message, true))
				.toList();
	}

	/**
	 * 메시지 생성하기
	 * @param create
	 */
	@Transactional
	public void createMessage(User user, MessageData.Create create) {
		Pick pick = pickRepository.findById(create.getPickId())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 픽입니다."));

		if (pick.isMessageSend()) {
			throw new IllegalArgumentException("하나의 Pick에 대해서는 하나의 메시지만 보낼 수 있습니다.");
		}

		messageRepository.save(Message.createMessage(user, user, null, ""));
		pickRepository.updateMessageSendTrue(create.getPickId());
	}

	/**
	 * 받은 메시지 삭제하기
	 * @param messageId
	 */
	@Transactional
	public void deleteFromMessage(Long messageId) {
		Message message = messageRepository.findById(messageId).orElseThrow();
		message.deleteMessageOfSender();
	}

	/**
	 * 보낸 메시지 삭제하기
	 * @param messageId
	 */
	@Transactional
	public void deleteToMessage(Long messageId) {
		Message message = messageRepository.findById(messageId).orElseThrow();
		message.deleteMessageOfReceiver();
	}
}
