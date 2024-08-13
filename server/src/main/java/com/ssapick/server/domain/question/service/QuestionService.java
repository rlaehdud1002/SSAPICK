package com.ssapick.server.domain.question.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.service.CommentAnalyzerService;
import com.ssapick.server.core.service.SentenceSimilarityAnalyzerService;
import com.ssapick.server.core.service.SentenceSimilarityResponse;
import com.ssapick.server.domain.notification.dto.FCMData;
import com.ssapick.server.domain.notification.entity.NotificationType;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.repository.*;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ssapick.server.core.constants.PickConst.QUESTION_CREATE_COIN;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    private final EntityManager em;
    private final QuestionRepository questionRepository;
    private final QuestionRegistrationRepository questionRegistrationRepository;
    private final QuestionBanRepository questionBanRepository;
    private final QuestionCategoryRepository questionCategoryRepository;
    private final SentenceSimilarityAnalyzerService sentenceSimilarityAnalyzerService;
    private final CommentAnalyzerService commentAnalyzerService;
    private final QuestionCacheRepository questionCacheRepository;
    private final ApplicationEventPublisher publisher;
    private final PickRepository pickRepository;

    @PostConstruct
    public void init() {
        questionCacheRepository.saveAll((
            questionRepository.findQuestions()
        ));
    }

    /**
     * 모든질문 조회
     *
     * @return
     */
    public List<QuestionData.Search> searchQuestions() {
        // List<Question> all = questionRepository.findQuestions();
        // return all.stream()
        //         .map(QuestionData.Search::fromEntity)
        //         .toList();

        return questionCacheRepository.findAll();
    }

    /**
     * 내가 생성한 질문 조회
     *
     * @param user
     * @return
     */
    public List<QuestionData.MyQuestion> getQuestionsByUser(User user) {
        return questionRepository.findByAuthor(user)
                .stream()
                .filter(question -> !question.isDeleted())
                .map(question -> QuestionData.MyQuestion.fromEntity(question, !pickRepository.existsByQuestionId(question.getId())))
                .toList();
    }

    /**
     * 카테고리별 질문 조회
     *
     * @param questionCategoryId
     * @return
     */
    @Deprecated //필요 없어 보임
    public List<QuestionData.Search> searchQuestionsByCategory(Long questionCategoryId) {
        QuestionCategory category = questionCategoryRepository.findById(questionCategoryId).orElseGet(() -> {
            throw new BaseException(ErrorCode.NOT_FOUND_QUESTION_CATEGORY);
        });

        // return questionRepository.findQuestionsByQuestionCategory(category)
        //     .stream().map(QuestionData.Search::fromEntity)
        //     .toList();
        //
        return questionCacheRepository.findQuestionsByCategory(questionCategoryId);
    }

    /**
     * 질문 ID로 질문 조회
     *
     * @param questionId
     * @return
     */
    public QuestionData.Search searchQuestionByQuestionId(Long questionId) {
        // Question question = questionRepository.findById(questionId)
        //         .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_QUESTION));


        // if (question.isDeleted()) {
        //     throw new BaseException(ErrorCode.DELETED_QUESTION);
        // }

        // return QuestionData.Search.fromEntity(question);

        return questionCacheRepository.findById(questionId)
            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_QUESTION));
    }

    /**
     * 질문 생성 요청
     *
     * @param create
     */
    // @Async("apiExecutor")
    @Transactional
    public void createQuestion(User user, QuestionData.Create create) {
        QuestionCategory category = questionCategoryRepository.findById(create.getCategoryId())
            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_QUESTION_CATEGORY));

        Question newQuestion = questionRepository.save(Question.createQuestion(category, create.getContent(), user, true));

        checkQuestion(user, newQuestion);
    }

    @Async("apiExecutor")
    // @Transactional
    public void checkQuestion(User user, Question newQuestion) {

        try {
            if (commentAnalyzerService.isCommentOffensive(newQuestion.getContent())) {
                publisher.publishEvent(
                    FCMData.NotificationEvent.of(
                        NotificationType.ADD_QUESTION,
                        user,
                        user,
                        newQuestion.getId(),
                        ErrorCode.OFFENSIVE_CONTENT.getMessage(),
                        addQuestionEventMessage(newQuestion.getContent()),
                        null
                    ));
                throw new BaseException(ErrorCode.OFFENSIVE_CONTENT);
            }
        } catch (BaseException e) {
            if (e.getErrorCode() == ErrorCode.OFFENSIVE_CONTENT) {
                throw e;
            } else {
                publisher.publishEvent(
                    FCMData.NotificationEvent.of(
                        NotificationType.ADD_QUESTION,
                        user,
                        user,
                        newQuestion.getId(),
                        ErrorCode.API_REQUEST_ERROR.getMessage(),
                        addQuestionEventMessage(newQuestion.getContent()),
                        null
                    ));


                throw new BaseException(ErrorCode.API_REQUEST_ERROR);
            }
        }

        // 기존 질문과 유사도 분석
        SentenceSimilarityResponse similarity = sentenceSimilarityAnalyzerService.analyzeSentenceSimilarity(newQuestion.getContent());
        if (similarity.getValue() > 0.5) {

            publisher.publishEvent(
                FCMData.NotificationEvent.of(
                    NotificationType.ADD_QUESTION,
                    user,
                    user,
                    newQuestion.getId(),
                    ErrorCode.EXIST_QUESTION.getMessage(),
                    addQuestionEventMessage(newQuestion.getContent()),
                    null
                ));

            throw new BaseException(ErrorCode.EXIST_QUESTION, "이미 존재하는 질문 입니다. \n 기존의 질문 : " + similarity.getDescription());
        }

        publisher.publishEvent(
            FCMData.NotificationEvent.of(
                NotificationType.ADD_QUESTION,
                user,
                user,
                newQuestion.getId(),
                "당신의 질문이 등록 됐어요!",
                addQuestionEventMessage(newQuestion.getContent()),
                null
            ));

        publisher.publishEvent(new PickcoEvent(user, PickcoLogType.QUESTION_CREATE, QUESTION_CREATE_COIN));
        newQuestion.restore();
        questionRepository.save(newQuestion);
        questionCacheRepository.add(newQuestion);
    }


    private String addQuestionEventMessage(String message) {
        return message;
    }

    /**
     * 사용자가 질문 차단
     *
     * @param user
     * @param questionId
     */
    @Transactional
    public void banQuestion(User user, Long questionId) {

        Question question = questionRepository.findById(questionId)
            .orElseThrow(() ->
                new BaseException(ErrorCode.NOT_FOUND_QUESTION)
            );

        questionBanRepository.findBanByUserIdAndQuestionId(user.getId(), questionId)
            .ifPresent(q -> {
                throw new BaseException(ErrorCode.EXIST_QUESTION_BAN);
            });

        question.increaseBanCount();

        questionBanRepository.save(QuestionBan.of(user, question));
    }

    /**
     * 내가 차단한 질문 조회
     *
     * @param userId
     * @return
     */
    public List<QuestionData.Search> searchBanQuestions(Long userId) {
        return questionBanRepository.findQuestionBanByUserId(userId)
                .stream()
                .map(QuestionData.Search::fromEntity)
                .toList();
    }

    /**
     * 내가 지목받은 픽에 대한 질문 랭킹
     *
     * @param userId
     * @return
     */
    public List<QuestionData.Search> searchQuestionsRank(Long userId) {
        return questionRepository.findQRankingByUserId(userId)
                .stream().limit(10)
                .map(QuestionData.Search::fromEntity)
                .toList();
    }

    /**
     * 사용자에게 뿌려줄 질문 리스트 (사용자가 차단하지 않은)
     *
     * @param user
     * @return
     */
    public List<QuestionData.Search> searchQeustionList(User user) {

        List<QuestionData.Search> searches = new ArrayList<>(this.searchQuestions()); // 변경 가능하도록 ArrayList로 변환
        List<QuestionData.Search> banQuestions = this.searchBanQuestions(user.getId());

        searches.removeAll(banQuestions);

        Collections.shuffle(searches);

        if (searches.size() > 15) {
            searches = searches.subList(0, 15);
        }

        return searches;
    }

    /**
     * 질문 차단 해제
     *
     * @param user
     * @param questionId
     */
    @Transactional
    public void unbanQuestion(User user, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
            () ->new BaseException(ErrorCode.NOT_FOUND_QUESTION));

        QuestionBan questionBan = questionBanRepository.findBanByUserIdAndQuestionId(user.getId(), questionId)
            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_QUESTION_BAN));

        question.decreaseBanCount();

        if (question.getBanCount() >= 10) {
            questionCacheRepository.remove(question.getId());
        }

        questionBanRepository.delete(questionBan);
    }

    public List<QuestionData.Category> searchCategories() {
        List<QuestionCategory> categories = questionCategoryRepository.findAll();

        return categories.stream()
            .map(QuestionData.Category::fromEntity)
            .toList();
    }
}
