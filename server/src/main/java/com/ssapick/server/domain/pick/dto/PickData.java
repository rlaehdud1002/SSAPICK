package com.ssapick.server.domain.pick.dto;

import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.dto.ProfileData;
import lombok.Data;

import java.time.LocalDateTime;

public class PickData {
    public enum PickStatus {
        PICKED, PASS, BLOCK
    }

    @Data
    public static class Search {
        private Long id;
        private ProfileData.Search sender;
        private ProfileData.Search receiver;
        private SimpleQuestion question;
        private boolean isMessageSend;
        private LocalDateTime createdAt;

        public static Search fromEntity(Pick pick, boolean isReceiver) {
            Search search = new Search();
            if (isReceiver) {
                search.sender = ProfileData.Search.fromEntityAnonymous(pick.getSender().getProfile());
                search.receiver = ProfileData.Search.fromEntity(pick.getReceiver().getProfile());
            } else {
                search.sender = ProfileData.Search.fromEntity(pick.getSender().getProfile());
                search.receiver = ProfileData.Search.fromEntity(pick.getReceiver().getProfile());
            }
            search.id = pick.getId();
            search.question = SimpleQuestion.fromEntity(pick.getQuestion());
            search.isMessageSend = pick.isMessageSend();
            search.createdAt = pick.getCreatedAt();
            return search;
        }
    }

    @Data
    public static class SimpleQuestion {
        private Long id;
        private String content;
        private String categoryName;
        private String categoryThumbnail;
        private LocalDateTime createdAt;

        public static SimpleQuestion fromEntity(Question question) {
            SimpleQuestion simpleQuestion = new SimpleQuestion();
            simpleQuestion.id = question.getId();
            simpleQuestion.content = question.getContent();
            simpleQuestion.categoryName = question.getQuestionCategory().getName();
            simpleQuestion.categoryThumbnail = question.getQuestionCategory().getThumbnail();
            simpleQuestion.createdAt = question.getCreatedAt();
            return simpleQuestion;
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
