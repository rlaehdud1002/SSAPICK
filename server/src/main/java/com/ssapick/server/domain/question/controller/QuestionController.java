package com.ssapick.server.domain.question.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.service.QuestionService;
import com.ssapick.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/question")
public class QuestionController {

	private final QuestionService questionService;

	/**
	 * 모든 질문 조회  API
	 * @return {@link List<QuestionData.Search>} 모든 질문 조회
	 */
	@GetMapping("")
	public SuccessResponse<List<QuestionData.Search>> searchQeustions() {
		List<QuestionData.Search> questions = questionService.searchQeustions();
		return SuccessResponse.of(questions);
	}

	/**
	 * 카테고리별 질문 조회 API
	 * @param questionCategory
	 * @return {@link List<QuestionData.Search>} 카테고리별 질문 조회
	 */
	@GetMapping("/category/{questionCategory_id}")
	public SuccessResponse<List<QuestionData.Search>> searchQeustionsByCategory(Long questionCategory) {
		List<QuestionData.Search> questions = questionService.searchQeustionsByCategory(questionCategory);

		return SuccessResponse.of(questions);
	}

	/**
	 * 질문 ID로 질문 조회 API
	 * @param questionId
	 * @return {@link QuestionData.Search} 질문 ID로 질문 조회
	 */
	@GetMapping("/{questionId}")
	public SuccessResponse<QuestionData.Search> searchQeustionsByQuestionId(Long questionId) {
		QuestionData.Search search = questionService.searchQeustionByQuestionId(questionId);
		return SuccessResponse.of(search);
	}

	/**
	 * 질문 추가 API
	 * @param user
	 * @param categoryId
	 * @param content
	 * @return
	 */
	@PostMapping("")
	public SuccessResponse<Void> requestAddQuestion(@CurrentUser User user, Long categoryId, String content) {
		questionService.createQuestion(user, categoryId, content);

		return SuccessResponse.empty();
	}

	/**
	 * 사용자가 질문을 벤하는 API
	 * @param user
	 * @param questionId
	 * @return
	 */
	@PostMapping("/{questionId}/ban")
	public SuccessResponse<Void> banQuestion(@CurrentUser User user, @PathVariable("questionId") Long questionId) {
		questionService.banQuestion(user, questionId);
		return SuccessResponse.empty();
	}

	// @GetMapping("/list")
	// public SuccessResponse<List<QuestionData.Search>> searchQeustionsList(@CurrentUser User user) {
	//
	// 	// ALL
	// 	List<QuestionData.Search> searches = questionService.searchQeustions();
	//
	// 	return nu
	// }
}
