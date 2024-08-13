package com.ssapick.server.domain.question.dto;

import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


public class QuestionData {
    @Data
    public static class Category {
        private Long id;
        private String name;
        private String thumbnail;

        public static Category fromEntity(QuestionCategory category) {
            Category categoryData = new Category();
            categoryData.id = category.getId();
            categoryData.name = category.getName();
            categoryData.thumbnail = category.getThumbnail();
            return categoryData;
        }
    }

    @Data
    public static class Search {
        private Long id;
        private int banCount;
        private int skipCount;
        private Category category;
        private String content;

        public static Search fromEntity(Question question) {
            Search search = new Search();
            search.id = question.getId();
            search.banCount = question.getBanCount();
            search.skipCount = question.getSkipCount();
            search.category = Category.fromEntity(question.getQuestionCategory());
            search.content = question.getContent();
            return search;
        }
    }

    @Data
    public static class MyQuestion {
        private Long id;
        private Category category;
        private String content;

        public static MyQuestion fromEntity(Question question) {
            MyQuestion myQuestion = new MyQuestion();
            myQuestion.id = question.getId();
            myQuestion.category = Category.fromEntity(question.getQuestionCategory());
            myQuestion.content = question.getContent();
            return myQuestion;
        }
    }

    @Data
    public static class Create {
        @NotNull(message = "카테고리 ID는 필수입니다.")
        private Long categoryId;

        @NotNull(message = "질문 내용은 필수입니다.")
        @Size(min = 5, max = 30, message = "질문 내용은 5자 이상 30자 이하입니다.")
        private String content;
    }

}
