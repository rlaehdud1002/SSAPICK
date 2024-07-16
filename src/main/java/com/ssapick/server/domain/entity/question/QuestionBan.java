package com.ssapick.server.domain.entity.question;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.core.entity.TimeBaseEntity;
import com.ssapick.server.domain.entity.member.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class QuestionBan extends TimeBaseEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_ban_id")
	private Long id;


	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile profile;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "question_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "QUESTION question_id 외래키 참조"))
	private Question question;
}
