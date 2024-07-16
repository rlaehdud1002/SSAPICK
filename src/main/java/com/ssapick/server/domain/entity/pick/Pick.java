package com.ssapick.server.domain.entity.pick;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;

import com.ssapick.server.core.entity.TimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Pick extends TimeEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "pick_id")
	private Long id;

	private Long fromProfileId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile toProfile;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	private Question question;

	@OneToMany(mappedBy = "pick",cascade = CascadeType.ALL)
	private ArrayList<HintOpen> hintOpens = new ArrayList<>();
}
