package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Message;

import lombok.Data;

public class MessageData {
	@Data
	public static class Search {
		private Long id;
		private Long senderId;
		private String senderName;
		private String receiverName;
		private char senderGender;
		private char receiverGender;
		private LocalDateTime createdAt;
		private String content;
		private String questionContent;

		private String senderProfileImage;
		private String receiverProfileImage;
		private String senderCampus;
		private String receiverCampus;
		private short senderSection;
		private short receiverSection;


		public static Search fromEntity(Message message, boolean isReceived) {
			Search search = new Search();
			if (!isReceived) {
				search.receiverName = message.getReceiver().getName();
				search.receiverGender = message.getReceiver().getGender();
			}

			search.senderName = message.getSender().getName();
			search.senderProfileImage = message.getSender().getProfile().getProfileImage();
			search.senderCampus = message.getSender().getProfile().getCampus().getName();
			search.senderSection = message.getSender().getProfile().getCampus().getSection();

			search.receiverCampus = message.getReceiver().getProfile().getCampus().getName();
			search.receiverSection = message.getReceiver().getProfile().getCampus().getSection();

			search.id = message.getId();
			search.senderId = message.getSender().getId();

			search.senderGender = message.getSender().getGender();
			search.createdAt = message.getCreatedAt();
			search.content = message.getContent();
			search.questionContent = message.getPick().getQuestion().getContent();
			return search;
		}
	}

	@Data
	public static class Create {
		private Long pickId;
		private String content;
	}
}
