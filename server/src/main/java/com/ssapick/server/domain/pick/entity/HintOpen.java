package com.ssapick.server.domain.pick.entity;

import static jakarta.persistence.FetchType.*;

import com.ssapick.server.core.entity.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HintOpen extends TimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hint_open_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "pick_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_hint_open_pick_id"))
	private Pick pick;

	@Column(name = "content")
	private String content;

	@Column(name = "hint_type")
	@Enumerated(EnumType.STRING)
	private HintType hintType;

	@Builder
	private HintOpen(Pick pick, String content, HintType hintType) {
		this.pick = pick;
		this.content = content;
		this.hintType = hintType;
	}

	public void setPick(Pick pick) {
		if (this.pick != null) {
			this.pick.getHintOpens().remove(this);
		}
		this.pick = pick;
		pick.getHintOpens().add(this);
	}
}
