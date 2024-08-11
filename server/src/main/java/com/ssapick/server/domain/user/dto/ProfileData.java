package com.ssapick.server.domain.user.dto;

import java.time.LocalDate;

import com.ssapick.server.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import com.ssapick.server.domain.user.entity.Profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ProfileData {
	@Data
	public static class Search {
		private Long userId;
		private String nickname;
		private char gender;
		private String campusName;
		private short campusSection;
		private String campusDescription;
		private String profileImage;
		private short cohort;



		public static Search fromEntity(Profile profile) {
			Search search = new Search();
			search.userId = profile.getUser().getId();
			search.gender = profile.getUser().getGender();
			search.nickname = profile.getUser().getName();
			search.campusName = profile.getCampus().getName();
			search.campusSection = profile.getCampus().getSection();
			search.campusDescription = profile.getCampus().getDescription();
			search.profileImage = profile.getProfileImage();
			search.cohort = profile.getCohort();
			return search;
		}

		public static Search fromEntityAnonymous(Profile profile) {
			Search search = new Search();
			search.gender = profile.getUser().getGender();
			search.campusName = profile.getCampus().getName();
			search.campusSection = profile.getCampus().getSection();
			search.campusDescription = profile.getCampus().getDescription();
			search.cohort = profile.getCohort();
			return search;
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class InitialProfileInfo {

		private String name;
		private String location;
		private short section;

	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Friend {
		private Long userId;
		private String name;
		private String profileImage;
		private short cohort;
		private short campusSection;
		private boolean follow;
		private boolean sameCampus;

		public static Friend fromEntity(User user) {
			Friend friend = new Friend();
			friend.userId = user.getId();
			friend.name = user.getName();
			friend.profileImage = user.getProfile().getProfileImage();
			friend.cohort = user.getProfile().getCohort();
			friend.campusSection = user.getProfile().getCampus().getSection();
			return friend;
		}
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
