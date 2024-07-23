package com.ssapick.server.domain.pick.dto;

import com.ssapick.server.domain.pick.entity.Hint;
import com.ssapick.server.domain.pick.entity.HintType;
import com.ssapick.server.domain.user.entity.User;

import lombok.Builder;
import lombok.Data;

public class HintData {

	@Data
	public static class Create {
		private Long id;
		private User user;
		private String content;
		private HintType hintType;
		private boolean visibility;

		public Hint toEntity() {
			return Hint.builder()
				.id(id)
				.user(user)
				.content(content)
				.hintType(hintType)
				.visibility(visibility)
				.build();
		}

		@Builder
		public Create(Long id ,User user, String content, HintType hintType, boolean visibility) {
			this.id = id;
			this.user = user;
			this.content = content;
			this.hintType = hintType;
			this.visibility = visibility;
		}
	}
}
