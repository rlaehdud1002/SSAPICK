package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.user.dto.ProfileData;

import lombok.Data;

public class PickData {
	public enum PickStatus {
		PICKED, PASS, BLOCK
	}

	@Data
	public static class Search {
		private Long id;
		private ProfileData.Search sender;
		private ProfileData.Search receiver;
		private QuestionData.Search question;
		private boolean isMessageSend;
		private LocalDateTime createdAt;

		@JsonProperty("openedHints")
		private List<String> openedHints = new ArrayList<>();

		public static Search fromEntity(Pick pick, boolean isReceiver) {
			Search search = new Search();
			if (isReceiver) {
				search.sender = ProfileData.Search.fromEntityAnonymous(pick.getSender().getProfile());
				pick.getHintOpens().forEach(hintOpen -> search.openedHints.add(hintOpen.getContent()));
			} else {
				search.sender = ProfileData.Search.fromEntity(pick.getSender().getProfile());
			}

			search.receiver = ProfileData.Search.fromEntity(pick.getReceiver().getProfile());

			search.id = pick.getId();
			search.question = QuestionData.Search.fromEntity(pick.getQuestion());
			search.isMessageSend = pick.isMessageSend();
			search.createdAt = pick.getCreatedAt();
			return search;
		}
	}

	@Data
	public static class Create {
		private Long receiverId;
		private Long questionId;
		private int index;
		private PickStatus status;
	}
}
