package com.ssapick.server.domain.entity.question;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.core.entity.TimeBaseEntity;
import com.ssapick.server.domain.entity.member.Profile;

import jakarta.persistence.CascadeType;
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
public class QuestionRequest extends TimeBaseEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_request_id")
	private Long id;

	@ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "question_category_id", nullable = false, foreignKey = @ForeignKey(name = "QUESTION_CATEGORY question_category_id 외래키 참조"))
	private QuestionCategory questionCategory;

	private String content;

	@ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile requestBy;

}
