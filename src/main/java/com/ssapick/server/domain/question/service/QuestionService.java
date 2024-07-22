package com.ssapick.server.domain.question.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionRepository questionRepository;

	/**
	 * 모든 질문 조회
	 */
	public List<QuestionData.Search> searchQeustions() {
		List<Question> all = questionRepository.findAll();
		return all.stream()
			.map(QuestionData.Search::fromEntity)
			.toList();
	}

	/**
	 * 카테고리별 질문 조회
	 * @param questionCategory
	 */
	public List<QuestionData.Search> searchQeustionsByCategory(String questionCategory) {
		List<Question> all = questionRepository.findAllByQuestionByCategory_Name(questionCategory);
		return all.stream()
			.map(QuestionData.Search::fromEntity)
			.toList();

	}

	/**
	 * 질문 ID로 질문 조회
	 * @param questionId
	 * @return
	 */
	public QuestionData.Search searchQeustionsByQuestionId(Long questionId) {
		return questionRepository.findById(questionId)
			.filter(q -> !q.isDeleted())
			.map(QuestionData.Search::fromEntity)
			.orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));
	}
}
