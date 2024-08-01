package com.ssapick.server.core.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

public class PerspectiveData {

    @Data
    public static class Request {
        private Comment comment;
        private RequestedAttributes requestedAttributes;
    }

    @Data
    @AllArgsConstructor
    public static class Comment {
        private String text;
    }

    @Data
    public static class RequestedAttributes {
        private Object INSULT;
        private Object PROFANITY;
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
