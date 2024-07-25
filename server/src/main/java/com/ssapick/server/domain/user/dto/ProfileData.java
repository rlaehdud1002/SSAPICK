package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.user.entity.Profile;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

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

    @Data
    public static class Update {
        @NotNull(message = "이름은 필수 입력 값입니다.")
        private String name;

        @URL(message = "URL 형식이 아닙니다.")
        private String profileImage;

        @NotNull(message = "MBTI는 필수 입력 값입니다.")
        private String mbti;

        @NotNull(message = "전공은 필수 입력 값입니다.")
        private String major;

        @NotNull(message = "생일은 필수 입력 값입니다.")
        private LocalDate birth;

        @NotNull(message = "지역은 필수 입력 값입니다.")
        private String location;

        @NotNull(message = "관심사는 필수 입력 값입니다.")
        private String interest;
    }
}
