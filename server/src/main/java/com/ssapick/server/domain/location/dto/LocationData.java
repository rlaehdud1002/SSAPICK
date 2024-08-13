package com.ssapick.server.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class LocationData {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Geo {
        private double latitude;
        private double longitude;
    }

    @Data
    public static class PickRequest {
        private String username;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String username;
        private Geo geo;
    }

    @Data
    public static class GeoLocation {
        private String username;
        private String profileImage;
        private Position position;
        private double distance;
        private boolean isAlarm;
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private int count;
        private List<GeoLocation> locations;
    }

    @Data
    @AllArgsConstructor(staticName = "of")
    public static class Position {
        private double x;
        private double y;
    }
}
