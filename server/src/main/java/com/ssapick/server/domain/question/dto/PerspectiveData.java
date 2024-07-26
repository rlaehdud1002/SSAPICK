package com.ssapick.server.domain.question.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

public class PerspectiveData {

    @Data
    public static class Request {
        private Comment comment;
        private RequestedAttributes requestedAttributes;
    }

    @Data
    public static class Comment {
        private String text;
    }

    @Data
    public static class RequestedAttributes {
        private Attribute INSULT;
        private Attribute PROFANITY;
    }

    @Data
    public static class Attribute {
    }

    @Data
    public static class Response {
        private Map<String, AttributeScores> attributeScores;
        private List<String> languages;
        private List<String> detectedLanguages;
    }

    @Data
    public static class Score {
        private double value;
        private String type;
    }


    @Data
    public static class SpanScore {
        private int begin;
        private int end;
        private Score score;
    }

    @Data
    public static class AttributeScores {
        private List<SpanScore> spanScores;
        private Score summaryScore;
    }


}
