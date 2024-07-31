package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

public class UserData {

    @Data
    public static class UserInfo {
        private String name;
        private String profileImage;
        private char gender;
        private short chort;

        private String campusName;
        private short section;

        private int pickco;
        private int pickCount;
        private int followingCount;

        private List<HintData> hints;

        public static UserInfo createUserInfo(User user, int pickCount, int followingCount) {
            Profile profile = user.getProfile();
            profile.getProfileImage();

            profile.getCampus().getSection();

            UserInfo userInfo = new UserInfo();
            userInfo.name = user.getName();
            userInfo.profileImage = profile.getProfileImage();
            userInfo.gender = user.getGender();
            userInfo.chort = profile.getCohort();
            userInfo.campusName = profile.getCampus().getName();
            userInfo.section = profile.getCampus().getSection();

            userInfo.hints = HintData.of(user.getHints());

            userInfo.pickCount = pickCount;
            userInfo.pickco = profile.getPickco();
            userInfo.followingCount = followingCount;

            return userInfo;
        }

    }

    @Data
    public static class HintData {
        private HintType hintType;
        private String content;

        public static List<HintData> of(List<Hint> hints) {
            return hints.stream()
                    .map(hint -> {
                        HintData hintData = new HintData();
                        hintData.hintType = hint.getHintType();
                        hintData.content = hint.getContent();
                        return hintData;
                    })
                    .toList();
        }
    }


    @Data
    public static class Update {

		@NotNull(message = "이름은 필수 입력 값입니다.")
		private String name;
		@NotNull(message = "성별은 필수 입력 값입니다.")
		private char gender;
		@NotNull(message = "학번은 필수 입력 값입니다.")
		private short cohort;
		@NotNull(message = "캠퍼스 이름은 필수 입력 값입니다.")
		private String campusName;
		@NotNull(message = "캠퍼스 반은 필수 입력 값입니다.")
		private short campusSection;
		@NotNull(message = "MBTI는 필수 입력 값입니다.")
		private String mbti;
		@NotNull(message = "전공은 필수 입력 값입니다.")
		private String major;
		@NotNull(message = "생년월일은 필수 입력 값입니다.")
		private String birth;
		@NotNull(message = "거주지역은 필수 입력 값입니다.")
		private String residentialArea;
		@NotNull(message = "관심사는 필수 입력 값입니다.")
		private String interest;

		public static Update of(
			String name,
			char gender,
			short cohort,
			String campusName,
			short campusSection,
			String mbti,
			String major,
			String birth,
			String residentialArea,
			String interest) {

			Update create = new Update();
			create.name = name;
			create.gender = gender;
			create.cohort = cohort;
			create.campusName = campusName;
			create.campusSection = campusSection;
			create.mbti = mbti;
			create.major = major;
			create.birth = birth;
			create.residentialArea = residentialArea;
			create.interest = interest;
			return create;
		}
	}
}
