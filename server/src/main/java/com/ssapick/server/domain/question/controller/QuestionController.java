package com.ssapick.server.domain.question.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @param questionCategory_id
	 * @return {@link List<QuestionData.Search>} 카테고리별 질문 조회
	 */
	@GetMapping("/category/{questionCategory_id}")
	public SuccessResponse<List<QuestionData.Search>> searchQeustionsByCategory(@PathVariable Long questionCategory_id) {
		List<QuestionData.Search> questions = questionService.searchQeustionsByCategory(questionCategory_id);

		return SuccessResponse.of(questions);
	}

	/**
	 * 질문 ID로 질문 조회 API
	 * @param questionId
	 * @return {@link QuestionData.Search} 질문 ID로 질문 조회
	 */
	@GetMapping("/{questionId}")
	public SuccessResponse<QuestionData.Search> searchQeustionsByQuestionId(@PathVariable Long questionId) {
		QuestionData.Search search = questionService.searchQeustionByQuestionId(questionId);
		return SuccessResponse.of(search);
	}

	/**
	 * 질문 생성 요청 API
	 * @param user
	 * @param addRequest
	 * @return
	 */
	@PostMapping("/add")
	public SuccessResponse<Void> requestAddQuestion(@CurrentUser User user, @RequestBody QuestionData.AddRequest addRequest) {
		questionService.createQuestion(user, addRequest);


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

	/**
	 * 사용자에게 질문을 뿌려주는 API (벤된 질문 제외)
	 * @param user
	 * @return
	 */
	@GetMapping("/list")
	public SuccessResponse<List<QuestionData.Search>> searchQeustionsList(@CurrentUser User user) {

		Set<QuestionData.Search> searcheSet = new HashSet<>(questionService.searchQeustions());

		Set<QuestionData.Search> banSet = new HashSet<>(questionService.searchBanQuestions(user.getId()));

		searcheSet.removeAll(banSet);

		return SuccessResponse.of(List.copyOf(searcheSet));
	}

	/**
	 * 내가 지목받은 질문 수 별로 랭킹 조회 API
	 * @param user
	 * @return
	 */
	@GetMapping("/rank")
	public SuccessResponse<List<QuestionData.Search>> searchQeustionsRank(@CurrentUser User user) {
		List<QuestionData.Search> questions = questionService.searchQeustionsRank(user.getId());
		return SuccessResponse.of(questions);
	}
}
