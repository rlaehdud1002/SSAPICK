package com.ssapick.server.domain.question.dto;

import com.ssapick.server.domain.question.entity.Question;

import lombok.Data;

public class QuestionData {

	@Data
	public static class Search{
		private Long questionId;
		private Integer banCount;
		private String questionCategory;
		private String content;

		public static Search fromEntity(Question question) {
			Search search = new Search();
			search.questionId = question.getId();
			search.banCount = question.getBanCount();
			search.questionCategory = question.getQuestionCategory().toString();
			search.content = question.getContent();
			return search;
		}
	}
}
