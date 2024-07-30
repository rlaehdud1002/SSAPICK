package com.ssapick.server.domain.pick.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class UserData {

	@Data
	public static class Update {

		@NotNull(message = "이름은 필수 입력 값입니다.")
		private String name;
		@NotNull(message = "성별은 필수 입력 값입니다.")
		private char gender;
		@NotNull(message = "학번은 필수 입력 값입니다.")
		private short chort;
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

		private String profileImage;

		public static Update of(
			String name,
			char gender,
			short chort,
			String campusName,
			short campusSection,
			String mbti,
			String major,
			String birth,
			String residentialArea,
			String interest,
			String profileImage) {

			Update create = new Update();
			create.name = name;
			create.gender = gender;
			create.chort = chort;
			create.campusName = campusName;
			create.campusSection = campusSection;
			create.mbti = mbti;
			create.major = major;
			create.birth = birth;
			create.residentialArea = residentialArea;
			create.interest = interest;
			create.profileImage = profileImage;
			return create;
		}
	}
}
