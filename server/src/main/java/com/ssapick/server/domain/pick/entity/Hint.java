package com.ssapick.server.domain.pick.entity;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Hint extends TimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hint_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_hint_user_id"))
	private User user;

	@Column(nullable = false)
	private String content;

	@Column(name = "hint_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private HintType hintType;

	public static Hint createHint(String content, HintType hintType) {
		Hint hint = new Hint();
		hint.content = content;
		hint.hintType = hintType;
		return hint;
	}

	public void setTestId(Long id) {
		this.id = id;
	}

	public void updateUser(User user) {
		this.user = user;
	}
}
