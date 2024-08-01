package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Message;

import lombok.Data;

public class MessageData {
	@Data
	public static class Search {
		private Long id;
		private String senderName;
		private String receiverName;
		private char senderGender;
		private char receiverGender;
		private LocalDateTime createdAt;
		private String content;
		private String questionContent;

		public static Search fromEntity(Message message, boolean isReceived) {
			Search search = new Search();
			if (isReceived) {
				search.senderName = "익명";
			} else {
				search.senderName = message.getSender().getName();
			}
			search.id = message.getId();
			search.receiverName = message.getReceiver().getName();
			search.senderGender = message.getSender().getGender();
			search.receiverGender = message.getReceiver().getGender();
			search.createdAt = message.getCreatedAt();
			search.content = message.getContent();
			search.questionContent = message.getPick().getQuestion().getContent();
			return search;
		}
	}

	@Data
	public static class Create {
		private Long receiverId;
		private Long pickId;
		private String content;
	}
}
