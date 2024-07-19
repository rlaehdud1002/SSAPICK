package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.user.entity.Profile;
import lombok.Data;

public class ProfileData {
    @Data
    public static class Search {
        private Long userId;
        private String nickname;
        private String campusName;
        private short campusSection;
        private String campusDescription;
        private String profileImage;

        public static Search fromEntity(Profile profile) {
            Search search = new Search();
            search.userId = profile.getUser().getId();
            // TODO: 유저에 유저 이름에 대한 정보가 존재하지 않음. 수정 필요함.
            search.nickname = profile.getUser().getUsername();
            search.campusName = profile.getCampus().getName();
            search.campusSection = profile.getCampus().getSection();
            search.campusDescription = profile.getCampus().getDescription();
            search.profileImage = profile.getProfileImage();
            return search;
        }
    }
}
