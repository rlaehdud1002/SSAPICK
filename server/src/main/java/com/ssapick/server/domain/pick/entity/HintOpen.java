package com.ssapick.server.domain.pick.entity;

import static jakarta.persistence.FetchType.*;

import com.ssapick.server.core.entity.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "hint_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_hint_open_hint_id"))
	private Hint hint;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "pick_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_hint_open_pick_id"))
	private Pick pick;

	@Column(name = "content")
	private String content;

	@Builder
	private HintOpen(Hint hint, Pick pick, String content) {
		this.hint = hint;
		this.pick = pick;
		this.content = content;
	}

	public void setPick(Pick pick) {
		if (this.pick != null) {
			this.pick.getHintOpens().remove(this);
		}
		this.pick = pick;
		pick.getHintOpens().add(this);
	}
}
