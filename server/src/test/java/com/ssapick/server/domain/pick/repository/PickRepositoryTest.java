package com.ssapick.server.domain.pick.repository;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserQueryRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@DataJpaTest
class PickRepositoryTest extends UserSupport {

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

//    @Test
//    @DisplayName("픽을 많이 받은 사람 TOP3 조회")
//    void 픽을_많이_받은_사람_TOP3_조회() throws Exception {
//        // * GIVEN: 이런게 주어졌을 때
//
//        List<User> users = List.of(
//            User.createUser("user1@example.com", "name", 'M', ProviderType.GOOGLE, "providerId"),
//            User.createUser("user2@example.com", "name", 'M', ProviderType.GOOGLE, "providerId"),
//            User.createUser("user3@example.com", "name", 'M', ProviderType.GOOGLE, "providerId"),
//            User.createUser("user4@example.com", "name", 'M', ProviderType.GOOGLE, "providerId")
//        );
//
//        User user1 = users.get(0);
//        User user2 = users.get(1);
//        User user3 = users.get(2);
//        User user4 = users.get(3);
//
//        userRepository.saveAll(users);
//
//
//        Question question = Question.createQuestion(
//                QuestionCategory.create("category", "thumbnail"), "question", user1);
//
//
//        questionRepository.save(question);
//
//        List<Pick> picks = List.of(
//                Pick.of(user1, user3, question),
//                Pick.of(user1, user3, question),
//                Pick.of(user1, user3, question),
//                Pick.of(user1, user3, question),
//                Pick.of(user1, user2, question),
//                Pick.of(user1, user2, question),
//                Pick.of(user1, user2, question),
//                Pick.of(user2, user1, question),
//                Pick.of(user2, user1, question),
//                Pick.of(user2, user4, question)
//        );
//
//        pickRepository.saveAll(picks);
//        // * WHEN: 이걸 실행하면
//
//        List<RankingData.PickReceiver> topPickReceivers = pickRepository.findTopPickReceivers();
//
//        // * THEN: 이런 결과가 나와야 한다
//        assertThat(topPickReceivers).hasSize(3);
//
//    }
}