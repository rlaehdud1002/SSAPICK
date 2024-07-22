package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;

import lombok.Data;

public class PickData {

	@Data
	public static class Recevied {
		private Long id;
		private char gender;
		private User sender;
		private Question question;
		private boolean isMessageSend;
		private LocalDateTime createdAt;

		public static Recevied fromEntity(Pick pick) {
			Recevied search = new Recevied();
			search.id = pick.getId();
			search.gender = pick.getSender().getGender();
			search.sender = pick.getSender();
			search.question = pick.getQuestion();
			search.isMessageSend = pick.isMessageSend();
			search.createdAt = pick.getCreatedAt();
			return search;
		}
	}

	@Data
	public static class Sent {
		private Long id;
		private User receiver;
		private Question question;
		private LocalDateTime createdAt;

		public static Sent fromEntity(Pick pick) {
			Sent search = new Sent();
			search.id = pick.getId();
			search.receiver = pick.getReceiver();
			search.question = pick.getQuestion();
			search.createdAt = pick.getCreatedAt();
			return search;
		}
	}

	@Data
	public static class Create {
		private User receiver;
		private User sender;
		private Question question;
	}
}
