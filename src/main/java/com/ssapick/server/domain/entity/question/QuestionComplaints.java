package com.ssapick.server.domain.entity.question;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.domain.entity.member.Profile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class QuestionComplaints {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_complaints_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "question_id", nullable = false, updatable = false,foreignKey = @ForeignKey(name = "QUESTION question_id 외래키 참조"))
	private Question question;

	@Column(nullable = false)
	private String complaint_reason;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false,foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile complaintBy;
}
