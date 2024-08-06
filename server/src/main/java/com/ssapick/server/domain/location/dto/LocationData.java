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
    @AllArgsConstructor(staticName = "of")
    public static class Request {
        private Long userId;
        private Geo geo;
    }
}
