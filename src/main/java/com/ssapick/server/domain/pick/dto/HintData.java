package com.ssapick.server.domain.pick.dto;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.user.entity.User;

import lombok.Data;

public class HintData {

	@Data
	public static class Create {
		private User user;
		private String content;
		private String hintType;
		private boolean visibility;

		public Hint toEntity() {
			return Hint.builder()
				.user(user)
				.content(content)
				.hintType(HintType.valueOf(hintType))
				.visibility(visibility)
				.build();
		}
	}
}
