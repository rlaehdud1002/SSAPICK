package com.ssapick.server.domain.question.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/question")
public class QuestionController {

	private final QuestionService questionService;

	@GetMapping("")
	public SuccessResponse<List<QuestionData.Search>> searchQeustions() {
		List<QuestionData.Search> questions = questionService.searchQeustions();
		return SuccessResponse.of(questions);
	}

	@GetMapping("/category/{questionCategory}")
	public SuccessResponse<List<QuestionData.Search>> searchQeustionsByCategory(String questionCategory) {
		List<QuestionData.Search> questions = questionService.searchQeustionsByCategory(questionCategory);

		return SuccessResponse.of(questions);
	}

	@GetMapping("/{questionId}")
	public SuccessResponse<QuestionData.Search> searchQeustionsByQuestionId(Long questionId) {
		QuestionData.Search search = questionService.searchQeustionsByQuestionId(questionId);
		return SuccessResponse.of(search);
	}
}
