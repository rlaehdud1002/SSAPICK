package com.ssapick.server.domain.ranking.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("랭킹 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class RankingServiceTest extends UserSupport {

    @InjectMocks
    private RankingService rankingService;

    @Mock
    private PickRepository pickRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private PickcoLogRepository pickcoLogRepository;

    private List<User> users;
    private List<Pick> picks;
    private List<Message> messages;

    @BeforeEach
    void setUp() {
        users = List.of(
                this.createUser("user1"),
                this.createUser("user2"),
                this.createUser("user3"),
                this.createUser("user4")
        );

        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);
        User user4 = users.get(3);

        Question question = mock(Question.class);
        picks = List.of(
                Pick.of(user1, user3, question),
                Pick.of(user1, user3, question),
                Pick.of(user1, user3, question),
                Pick.of(user1, user3, question),
                Pick.of(user1, user2, question),
                Pick.of(user1, user2, question),
                Pick.of(user1, user2, question),
                Pick.of(user2, user1, question),
                Pick.of(user2, user1, question),
                Pick.of(user3, user4, question)
        );
        when(pickRepository.findAllWithReceiverAndSenderAndQuestion()).thenReturn(picks);

        messages = List.of(
                Message.createMessage(user1, user3, picks.get(0), "message"),
                Message.createMessage(user1, user3, picks.get(0), "message"),
                Message.createMessage(user1, user3, picks.get(0), "message"),
                Message.createMessage(user1, user3, picks.get(0), "message"),
                Message.createMessage(user1, user2, picks.get(0), "message"),
                Message.createMessage(user1, user2, picks.get(0), "message"),
                Message.createMessage(user1, user2, picks.get(0), "message"),
                Message.createMessage(user2, user1, picks.get(0), "message"),
                Message.createMessage(user2, user1, picks.get(0), "message"),
                Message.createMessage(user3, user4, picks.get(0), "message")
        );
        when(messageRepository.findAllWithReceiverAndSender()).thenReturn(messages);

    }

    @Test
    @DisplayName("픽을 많이 받은 사람 TOP3 조회")
    void getTOP3PickReceiversRanking() {
        // when
        RankingData.Response response = rankingService.getAllRanking();

        // then
        assertThat(response.getTopPickReceivers().size()).isEqualTo(3);
        assertThat(response.getTopPickReceivers().get(0).getUser().getName()).isEqualTo("user3");
        assertThat(response.getTopPickReceivers().get(1).getUser().getName()).isEqualTo("user2");
        assertThat(response.getTopPickReceivers().get(2).getUser().getName()).isEqualTo("user1");
        assertThat(response.getTopPickReceivers().get(0).getCount()).isEqualTo(4);
        assertThat(response.getTopPickReceivers().get(1).getCount()).isEqualTo(3);
        assertThat(response.getTopPickReceivers().get(2).getCount()).isEqualTo(2);

    }

    @Test
    @DisplayName("픽을 많이 보낸 사람 TOP3 조회")
    void getTOP3PickSendersRanking() {
        // when
        RankingData.Response response = rankingService.getAllRanking();

        // then
        assertThat(response.getTopPickSenders().size()).isEqualTo(3);
        assertThat(response.getTopPickSenders().get(0).getUser().getName()).isEqualTo("user1");
        assertThat(response.getTopPickSenders().get(1).getUser().getName()).isEqualTo("user2");
        assertThat(response.getTopPickSenders().get(2).getUser().getName()).isEqualTo("user3");
        assertThat(response.getTopPickSenders().get(0).getCount()).isEqualTo(7);
        assertThat(response.getTopPickSenders().get(1).getCount()).isEqualTo(2);
        assertThat(response.getTopPickSenders().get(2).getCount()).isEqualTo(1);


    }

    @Test
    @DisplayName("쪽지를 많이 받은 사람 TOP3 조회")
    void getTOP3MessageReceiversRanking() {
        // when
        RankingData.Response response = rankingService.getAllRanking();

        // then
        assertThat(response.getTopMessageReceivers().size()).isEqualTo(3);
        assertThat(response.getTopMessageReceivers().get(0).getUser().getName()).isEqualTo("user3");
        assertThat(response.getTopMessageReceivers().get(1).getUser().getName()).isEqualTo("user2");
        assertThat(response.getTopMessageReceivers().get(2).getUser().getName()).isEqualTo("user1");
        assertThat(response.getTopMessageReceivers().get(0).getCount()).isEqualTo(4);
        assertThat(response.getTopMessageReceivers().get(1).getCount()).isEqualTo(3);
        assertThat(response.getTopMessageReceivers().get(2).getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("쪽지를 많이 보낸 사람 TOP3 조회")
    void getTOP3MessageSendersRanking() {
        // when
        RankingData.Response response = rankingService.getAllRanking();

        // then
        assertThat(response.getTopMessageSenders().size()).isEqualTo(3);
        assertThat(response.getTopMessageSenders().get(0).getUser().getName()).isEqualTo("user1");
        assertThat(response.getTopMessageSenders().get(1).getUser().getName()).isEqualTo("user2");
        assertThat(response.getTopMessageSenders().get(2).getUser().getName()).isEqualTo("user3");
        assertThat(response.getTopPickSenders().get(0).getCount()).isEqualTo(7);
        assertThat(response.getTopPickSenders().get(1).getCount()).isEqualTo(2);
        assertThat(response.getTopPickSenders().get(2).getCount()).isEqualTo(1);

    }

    @Test
    @DisplayName("픽코를 많이 사용한 사람 TOP3 조회")
    void getTOP3PickcoSpendUserRanking() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);
        User user4 = users.get(3);

        List<PickcoLog> pickcoLogs = List.of(
                PickcoLog.createPickcoLog(user1, PickcoLogType.HINT_OPEN, -100, 100),
                PickcoLog.createPickcoLog(user1, PickcoLogType.HINT_OPEN, -10, 100),
                PickcoLog.createPickcoLog(user2, PickcoLogType.HINT_OPEN, -20, 100),
                PickcoLog.createPickcoLog(user2, PickcoLogType.HINT_OPEN, -10, 100),
                PickcoLog.createPickcoLog(user3, PickcoLogType.HINT_OPEN, -2, 100),
                PickcoLog.createPickcoLog(user4, PickcoLogType.HINT_OPEN, -1, 100)
        );

        when(pickcoLogRepository.findAllSpendWithUser()).thenReturn(pickcoLogs);

        // * WHEN: 이걸 실행하면

        RankingData.Response response = rankingService.getAllRanking();

        // * THEN: 이런 결과가 나와야 한다
        assertThat(response.getTopSpendPickcoUsers().size()).isEqualTo(3);
        assertThat(response.getTopSpendPickcoUsers().get(0).getUser().getName()).isEqualTo("user1");
        assertThat(response.getTopSpendPickcoUsers().get(1).getUser().getName()).isEqualTo("user2");
        assertThat(response.getTopSpendPickcoUsers().get(2).getUser().getName()).isEqualTo("user3");
        assertThat(response.getTopSpendPickcoUsers().get(0).getCount()).isEqualTo(110);
        assertThat(response.getTopSpendPickcoUsers().get(1).getCount()).isEqualTo(30);
        assertThat(response.getTopSpendPickcoUsers().get(2).getCount()).isEqualTo(2);
    }

}