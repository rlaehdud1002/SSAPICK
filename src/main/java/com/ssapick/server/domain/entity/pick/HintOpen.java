package com.ssapick.server.domain.entity.pick;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class HintOpen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hint_open_id")
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "hint_id", nullable = false,foreignKey = @ForeignKey(name = "HINT hint_id 외래키 참조"))
	private Hint hint;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "pick_id", nullable = false, updatable = false,foreignKey = @ForeignKey(name = "PICK pick_id 외래키 참조"))
	private Pick pick;
}
