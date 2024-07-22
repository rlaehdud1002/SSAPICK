package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.user.entity.User;

import lombok.Data;

public class MessageData {

	@Data
	public static class Search {

		private String senderName;
		private String receiverName;
		private LocalDateTime createdAt;
		private String content;
		private String questionContent;

		public static Search fromEntity(Message message, boolean isReceived) {
			Search search = new Search();

			if (isReceived) {
				search.receiverName = message.getSender().getName();
			}

			search.senderName = message.getReceiver().getName();
			search.createdAt = message.getCreatedAt();
			search.content = message.getContent();
			search.questionContent = message.getPick().getQuestion().getContent();
			return search;
		}
	}

	@Data
	public static class Create{
		private User sender;
		private User receiver;
		private Pick pick;
		private String content;

	}



}
