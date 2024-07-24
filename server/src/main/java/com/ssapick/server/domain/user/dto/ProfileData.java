package com.ssapick.server.domain.user.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import com.ssapick.server.domain.user.entity.Profile;

import jakarta.validation.constraints.NotNull;
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
			search.nickname = profile.getUser().getName();
			search.campusName = profile.getCampus().getName();
			search.campusSection = profile.getCampus().getSection();
			search.campusDescription = profile.getCampus().getDescription();
			search.profileImage = profile.getProfileImage();
			return search;
		}
	}

	@Data
	public static class InitialProfileInfo {

		private String name;
		private String location;
		private short section;

	}

	@Data
	public static class Update {

		@URL(message = "URL 형식이 아닙니다.")
		private String profileImage;

		@NotNull(message = "닉네임은 필수 입력 값입니다.")
		private String nickname;

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
