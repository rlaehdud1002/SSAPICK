package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;

import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PickData {

	@Data
	public static class Search {
		private Long id;
		private char senderGender;
		private User sender;
		private User receiver;
		private Question question;
		private boolean isMessageSend;
		private LocalDateTime createdAt;

		public static Search fromEntity(Pick pick, boolean isReceiver) {
			Search search = new Search();

			if (isReceiver){
				search.senderGender = pick.getSender().getGender();
			} else{
				search.sender = pick.getSender();
			}

			search.id = pick.getId();
			search.receiver = pick.getReceiver();
			search.question = pick.getQuestion();
			search.isMessageSend = pick.isMessageSend();
			search.createdAt = pick.getCreatedAt();
			return search;
		}
	}


	@Data
	public static class Create {
		private Long receiverId;
		private Long questionId;
	}
}
