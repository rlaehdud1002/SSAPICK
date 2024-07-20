package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Pick;

import lombok.Data;

public class PickData {

	@Data
	public static class Search {

		private Long id;
		private char gender;
		private LocalDateTime createdAt;
		private String fromUsername;
		private String questionContent;
		private boolean isMessageSent;


		public static Search fromEntity(Pick pick) {
			Search search = new Search();
			search.id = pick.getId();
			search.createdAt = pick.getCreatedAt();
			search.fromUsername = pick.getFromUser().getUsername();
			search.gender = pick.getFromUser().getGender();
			search.questionContent = pick.getQuestion().getContent();
			search.isMessageSent = pick.isMessageSent();
			return search;
		}
	}

	@Data
	public static class Create{
		private Long fromUserId;
		private Long toUserId;
		private Long questionId;
		private boolean isAlarmSent;
		private boolean isMessageSent;
	}
}
