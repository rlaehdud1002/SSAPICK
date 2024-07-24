package com.ssapick.server.domain.pick.entity;

import static jakarta.persistence.FetchType.*;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
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
	private HintType hintType;

	@Column(nullable = false)
	private boolean visibility = false;

	public static Hint createHint(User user, String content, HintType hintType) {
		Hint hint = new Hint();
		hint.user = user;
		hint.content = content;
		hint.hintType = hintType;
		return hint;
	}
}
