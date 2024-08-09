package com.ssapick.server.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LocationData {
    @Data
    @AllArgsConstructor
    public static class Geo {
        private double latitude;
        private double longitude;
    }

    @Data
    @AllArgsConstructor
    public static class Request {
        private String profileImage;
        private Geo geo;
    }

    @Data
    public static class Response {
        private String username;
        private String profileImage;
        private Position position;
        private double distance;
        private boolean isAlarm;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class Position {
        private double x;
        private double y;
    }
}
