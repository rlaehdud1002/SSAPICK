package com.ssapick.server.domain.question.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.service.QuestionService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/questions")
public class QuestionController {
    private final QuestionService questionService;

    /**
     * 모든 질문 조회 API
     * 질문 데이터베이스에 존재하는 모든 질문을 조회한다.
     *
     * @return {@link List<QuestionData.Search>} 모든 질문 조회
     */
    @GetMapping("")
    public SuccessResponse<List<QuestionData.Search>> searchQuestions() {
        List<QuestionData.Search> questions = questionService.searchQuestions();
        return SuccessResponse.of(questions);
    }

    /**
     * 질문 ID로 질문 조회 API
     * 입력한 질문 ID에 해당하는 질문을 조회한다.
     *
     * @param questionId 질문 ID
     * @return {@link QuestionData.Search} 질문 ID로 질문 조회
     */
    @GetMapping("/{questionId}")
    public SuccessResponse<QuestionData.Search> searchQuestionById(@PathVariable("questionId") Long questionId) {
        return SuccessResponse.of(questionService.searchQuestionByQuestionId(questionId));
    }

    /**
     * 카테고리별 질문 조회 API
     * 입력한 카테고리 ID에 해당하는 질문을 조회한다.
     *
     * @param categoryId 카테고리 ID
     * @return {@link List<QuestionData.Search>} 카테고리별 질문 조회
     */
    @GetMapping("/category/{categoryId}")
    public SuccessResponse<List<QuestionData.Search>> searchQuestionsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return SuccessResponse.of(questionService.searchQuestionsByCategory(categoryId));
    }

    /**
     * 질문 생성 요청 API
     * 사용자가 자신이 원하는 질문을 생성하는 API
     *
     * @param user   로그인한 사용자
     * @param create {@link com.ssapick.server.domain.question.dto.QuestionData.Create} 질문 생성 요청 데이터
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> requestAddQuestion(
            @CurrentUser User user,
            @Validated @RequestBody QuestionData.Create create,
            Errors errors
    ) {
        questionService.createQuestion(user, create);
        return SuccessResponse.empty();
    }

    /**
     * 사용자에게 질문을 뿌려주는 API (벤된 질문 제외)
     *
     * @param user 로그인한 사용자
     * @return {@link List<QuestionData.Search>} 사용자에게 알맞은 질문 제공
     */
    @GetMapping("/pick")
    public SuccessResponse<List<QuestionData.Search>> searchQuestions(@CurrentUser User user) {
        return SuccessResponse.of(List.copyOf(questionService.searchQeustionList(user)));
    }

    /**
     * 내가 지목받은 질문 수 별로 랭킹 조회 API
     *
     * @param user
     * @return {@link List<QuestionData.Search>} 내가 지목받은 질문 수 별로 랭킹 조회
     */
    @GetMapping("/rank")
    public SuccessResponse<List<QuestionData.Search>> searchQuestionsRank(@CurrentUser User user) {
        List<QuestionData.Search> questions = questionService.searchQuestionsRank(user.getId());
        return SuccessResponse.of(questions);
    }

    /**
     * 질문 차단 API
     *
     * @param user
     * @param questionId
     * @return
     */
    @Authenticated
    @PostMapping("{questionId}/ban")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> banQuestion(@CurrentUser User user, @PathVariable("questionId") Long questionId) {
        questionService.banQuestion(user, questionId);
        return SuccessResponse.created();
    }

    /**
     * 질문 차단 해제 API
     *
     * @param user
     * @return
     */
    @Authenticated
    @DeleteMapping("{questionId}/ban")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> unbanQuestion(@CurrentUser User user, @PathVariable("questionId") Long questionId) {
        questionService.unbanQuestion(user, questionId);
        return SuccessResponse.empty();
    }

    /**
     * 내가 차단한 질문 조회 API
     *
     * @param user
     * @return {@link List<QuestionData.Search>} 내가 차단한 질문 목록
     */
    @Authenticated
    @GetMapping("/bans")
    public SuccessResponse<List<QuestionData.Search>> searchBanQuestions(@CurrentUser User user) {
        return SuccessResponse.of(questionService.searchBanQuestions(user.getId()));
    }
}
