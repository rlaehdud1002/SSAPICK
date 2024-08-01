package com.ssapick.server.domain.ranking.dto;

import com.ssapick.server.domain.user.entity.Campus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class RankingData {

    @Data
    @AllArgsConstructor
    public static class Response {
        private final List<UserCount> topPickReceivers;
        private final List<UserCount> topPickSenders;
        private final List<UserCount> topMessageReceivers;
        private final List<UserCount> topMessageSenders;
        private final List<UserCount> topSpendPickcoUsers;
    }


    @Data
    @AllArgsConstructor
    public static class UserCount {
        private UserRankingProfile user;
        private Long count;
    }

    @Data
    public static class UserRankingProfile {
        private String name;
        private short cohort;
        private String campusName;
        private short section;
        private String profileImage;

        public UserRankingProfile(String name, short cohort, Campus campus, String profileImage) {
            this.name = name;
            this.cohort = cohort;
            this.campusName = campus.getName();
            this.section = campus.getSection();
            this.profileImage = profileImage;
        }

    }

}
