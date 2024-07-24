package com.ssapick.server.domain.pick.dto;

import lombok.Data;

public class HintData {

	@Data
	public static class Create {
		private String name;
		private char gender; // ---
		private short chort; //---
		private String campusName;
		private short campusSection; //---
		private String mbti;
		private String major;
		private String birth;
		private String residentialArea;
		private String interest;

		public static Create of(
			String name,
			char gender,
			short chort,
			String campusName,
			short campusSection,
			String mbti,
			String major,
			String birth,
			String residentialArea,
			String interest) {

			Create create = new Create();
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
			return create;
		}
	}
}
