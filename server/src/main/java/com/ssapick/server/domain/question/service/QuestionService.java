package com.ssapick.server.domain.question.service;

import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionBan;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.entity.QuestionRegistration;
import com.ssapick.server.domain.question.repository.QuestionBanRepository;
import com.ssapick.server.domain.question.repository.QuestionCategoryRepository;
import com.ssapick.server.domain.question.repository.QuestionRegistrationRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final EntityManager em;
    private final QuestionRepository questionRepository;
    private final QuestionRegistrationRepository questionRegistrationRepository;
    private final QuestionBanRepository questionBanRepository;
    private final QuestionCategoryRepository questionCategoryRepository;


    /**
     * 모든질문 조회
     *
     * @return
     */
    public List<QuestionData.Search> searchQeustions() {
        List<Question> all = questionRepository.findAll();
        return all.stream()
                .map(QuestionData.Search::fromEntity)
                .toList();
    }

    /**
     * 카테고리별 질문 조회
     *
     * @param questionCategoryId
     * @return
     */
    public List<QuestionData.Search> searchQeustionsByCategory(Long questionCategoryId) {
        QuestionCategory category = questionCategoryRepository.findById(questionCategoryId).orElseGet(() -> {
            throw new IllegalArgumentException("해당 카테고리가 존재하지 않습니다.");
        });

//		return category().stream()
//			.filter(q -> !q.isDeleted())
//			.map(QuestionData.Search::fromEntity)
//			.toList();
        return null;
    }

    /**
     * 질문 ID로 질문 조회
     *
     * @param questionId
     * @return
     */
    public QuestionData.Search searchQeustionByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

        if (question.isDeleted()) {
            throw new IllegalArgumentException("삭제된 질문입니다.");
        }
        return QuestionData.Search.fromEntity(question);
    }

    /**
     * 질문 생성 요청
     *
     * @param create
     */
    public void createQuestion(User user, QuestionData.Create create) {

        QuestionCategory category = questionCategoryRepository.findById(create.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));

        questionRegistrationRepository.save(QuestionRegistration.of(user, category, create.getContent()));
    }

    /**
     * 질문 차단
     *
     * @param user
     * @param questionId
     */
    public void banQuestion(User user, Long questionId) {
        questionBanRepository.findQByUser_IdAndQuestion_Id(questionId, user.getId())

                .ifPresent(q -> {
                    throw new IllegalArgumentException("이미 차단한 질문입니다.");
                });

        questionBanRepository.save(QuestionBan.of(user, em.getReference(Question.class, questionId)));
    }

    public List<QuestionData.Search> searchBanQuestions(Long userId) {
        return questionBanRepository.findUserBanQuestion(userId)
                .stream()
                .map(QuestionData.Search::fromEntity)
                .toList();

    }

    /**
     * 내가 지목받은 질문 수 별로 랭킹 조회
     *
     * @param userId
     * @return
     */
    public List<QuestionData.Search> searchQeustionsRank(Long userId) {
        return questionRepository.findRanking(userId)
                .stream()
                .map(QuestionData.Search::fromEntity)
                .toList();
    }

    public List<QuestionData.Search> searchQeustionList(User user) {

        List<QuestionData.Search> searches = searchQeustions();
        List<QuestionData.Search> banQuestions = searchBanQuestions(user.getId());

        searches.removeAll(banQuestions);

        return searches;

    }
}
