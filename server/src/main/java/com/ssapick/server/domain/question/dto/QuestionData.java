package com.ssapick.server.domain.question.dto;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class QuestionData {

	@Data
	public static class Search{
		private Long questionId;
		private Integer banCount;
		private Long questionCategoyId;
		private String questionCategoryName;
		private Long authorId;
		private String author;
		private String content;

		public static Search fromEntity(Question question) {
			Search search = new Search();
			search.questionId = question.getId();
			search.banCount = question.getBanCount();
			search.questionCategoyId = question.getQuestionCategory().getId();
			search.questionCategoryName = question.getQuestionCategory().getName();
			search.authorId = question.getAuthor().getId();
			search.author = question.getAuthor().getName();
			search.content = question.getContent();
			return search;
		}
	}
}
