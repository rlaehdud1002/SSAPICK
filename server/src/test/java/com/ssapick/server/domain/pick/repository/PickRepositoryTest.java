package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.core.config.JpaTestConfig;
import com.ssapick.server.core.container.TestDatabaseContainer;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.repository.QuestionCategoryRepository;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.CampusRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("픽 레포지토리 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaTestConfig.class})
class PickRepositoryTest extends TestDatabaseContainer {

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionCategoryRepository questionCategoryRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private EntityManager em;

    private PersistenceUnitUtil utils;

    @BeforeEach
    void init() {
        utils = em.getEntityManagerFactory().getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("Pick전체 조회 테스트")
    @Sql(scripts = "/sql/question-category.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void pick_전체_조회() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User sender = createUser("sender");
        User receiver = createUser("receiver");

        Campus campus = Campus.createCampus("광주", (short) 2, "전공");
        sender.getProfile().updateCampus(campus);
        receiver.getProfile().updateCampus(campus);

        QuestionCategory questionCategory = questionCategoryRepository.findById(1L).orElseThrow();
        Question question = Question.createQuestion(questionCategory, "질문 내용", sender);

        List<Pick> picks = List.of(
                Pick.of(sender, receiver, question),
                Pick.of(sender, receiver, question),
                Pick.of(sender, receiver, question));

        campusRepository.save(campus);
        questionRepository.save(question);
        pickRepository.saveAll(picks);
        em.flush();
        em.clear();


        // * WHEN: 이걸 실행하면
        List<Pick> findPicks = pickRepository.findAllWithReceiverAndSenderAndQuestion();

        // * THEN: 이런 결과가 나와야 한다
        assertThat(findPicks.size()).isEqualTo(3);
        assertThat(utils.isLoaded(findPicks.get(0), "receiver")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0), "sender")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0), "question")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0).getReceiver(), "profile")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0).getSender(), "profile")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0).getReceiver().getProfile(), "campus")).isTrue();
        assertThat(utils.isLoaded(findPicks.get(0).getSender().getProfile(), "campus")).isTrue();
    }

    private User createUser(String username) {
        User user = User.createUser(username, "테스트 유저", 'M', ProviderType.KAKAO, "123");

        return userRepository.save(user);
    }
}