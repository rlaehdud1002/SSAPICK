package com.ssapick.server.domain.entity.pick;

import static jakarta.persistence.FetchType.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Hint {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hint_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile profile;

	@Column(nullable = false)
	private String hintDetail;

	@Column(nullable = false)
	private HintType hintType;

	@Column(nullable = false)
	private boolean visiblility;
}
