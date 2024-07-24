package com.ssapick.server.domain.question.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "question_category")
public class QuestionCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_category_id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "questionCategory")
	private List<Question> questions = new ArrayList<>();

	private String thumbnail;

	public static QuestionCategory create(String name, String thumbnail) {
		QuestionCategory questionCategory = new QuestionCategory();
		questionCategory.name = name;
		questionCategory.thumbnail = thumbnail;
		return questionCategory;
	}
}
