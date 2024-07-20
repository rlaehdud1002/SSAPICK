package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.user.entity.User;

import lombok.Data;

public class MessageData {

	@Data
	public static class SearchSend {

		private Long fromUserId;
		private boolean fromDeleted;
		private LocalDateTime createdAt;
		private String content;

		public static SearchSend fromEntity(Message message) {
			SearchSend messages = new SearchSend();
			messages.fromUserId = message.getFromUser().getId();
			messages.fromDeleted = message.getFromUser().isDeleted();
			messages.createdAt = message.getCreatedAt();
			messages.content = message.getContent();
			return messages;
		}
	}

	@Data
	public static class SearchReceive {

		private Long toUserId;
		private boolean toDeleted;
		private LocalDateTime createdAt;
		private String content;

		public static SearchReceive fromEntity(Message message) {
			SearchReceive messages = new SearchReceive();
			messages.toUserId = message.getToUser().getId();
			messages.toDeleted = message.getToUser().isDeleted();
			messages.createdAt = message.getCreatedAt();
			messages.content = message.getContent();
			return messages;
		}
	}

	@Data
	public static class Create{
		private User fromUser;
		private User toUser;
		private Pick pick;
		private String content;
	}
}
