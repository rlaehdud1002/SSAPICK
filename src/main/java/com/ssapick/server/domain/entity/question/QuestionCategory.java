package com.ssapick.server.domain.entity.question;

import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class QuestionCategory {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "question_category_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private CategoryDetail categoryDetail;

	private String imagePath;

	@OneToMany(mappedBy = "questionCategory")
	private ArrayList<Question> questions = new ArrayList<>();
}
