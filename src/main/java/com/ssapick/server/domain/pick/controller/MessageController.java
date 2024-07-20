package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/message")
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/receive")
	public SuccessResponse<List<MessageData.SearchReceive>> searchSendMessage(Long userId) {
		List<MessageData.SearchReceive> result = messageService.searchReceiveMessage(userId);
		return SuccessResponse.of(result);
	}

	@GetMapping("/send")
	public SuccessResponse<List<MessageData.SearchSend>> searchReceiveMessage(Long userId) {

		List<MessageData.SearchSend> result = messageService.searchSendMessage(userId);
		return SuccessResponse.of(result);
	}

	@PostMapping()
	public SuccessResponse<Void> createMessage(MessageData.Create create) {
		messageService.createMessage(create);
		return SuccessResponse.empty();
	}

	@DeleteMapping("/from")
	public SuccessResponse<Void> deleteFromMessage(Long messageId) {
		messageService.deleteFromMessage(messageId);
		return SuccessResponse.empty();
	}

	@DeleteMapping("/to")
	public SuccessResponse<Void> deleteToMessage(Long messageId) {
		messageService.deleteToMessage(messageId);
		return SuccessResponse.empty();
	}
}
