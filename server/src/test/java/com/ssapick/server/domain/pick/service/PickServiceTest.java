package com.ssapick.server.domain.pick.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.test.context.support.WithMockUser;

import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.ProviderType;
import com.ssapick.server.domain.user.entity.RoleType;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PickServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PickServiceTest.class);

    @InjectMocks
    private PickService pickService;

    @Mock
    private PickRepository pickRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    static User receiver;
    static User sender;
    static Pick pick;
    static Question question;

    @BeforeEach
    void setUp() {
        receiver = userCreate(1L, "test-user1", 'F');
        sender = userCreate(2L, "test-user2", 'M');
        question = Question.builder().id(1L).content("질문").build();
        pick = pickCreate(receiver, sender, question);
    }

    @Test
    @WithMockUser
    @DisplayName("유저가 받은 픽을 조회하는 테스트")
    void searchReceivedPick() {
        // given
        when(pickRepository.findReceiverByUserId(1L)).thenReturn(List.of(pick));

        // when
        List<PickData.Search> picks = pickService.searchReceiver(1L);

        // then
        verify(pickRepository, times(1)).findReceiverByUserId(1L);
        Assertions.assertThat(picks.get(0).getReceiver().getId()).isEqualTo(1L);
    }


    @Test
    @WithMockUser
    @DisplayName("유저가 보낸 픽을 조회하는 테스트")
    void searchSentPick() {
        // given
        when(pickRepository.findSenderByUserId(2L)).thenReturn(List.of(pick));

        // when
        List<PickData.Search> picks = pickService.searchSender(2L);

        // then
        verify(pickRepository, times(1)).findSenderByUserId(2L);
        Assertions.assertThat(picks.get(0).getSender().getId()).isEqualTo(2L);
        Assertions.assertThat(picks.get(0).getReceiver().getId()).isEqualTo(1L);
        Assertions.assertThat(picks.get(0).getSender().getGender()).isEqualTo('M');
    }


    @Test
    @WithMockUser
    @DisplayName("유저1이 유저2를 픽하는 테스트")
    void setPick() {
        // given
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));
        when(questionRepository.findById(question.getId())).thenReturn(Optional.of(question));

        // when
        pickService.createPick(sender, new PickData.Create(1L, 1L));

        // then
        verify(pickRepository, times(1)).save(argThat(pick ->
                pick.getReceiver().equals(receiver)
                        && pick.getSender().equals(sender)
                        && pick.getQuestion().equals(question)
        ));
    }

    @Test
    @DisplayName("픽한_질문이_없을_때_예외처리_테스트")
    void 픽한_질문이_없을_때_예외처리_테스트() {
        // * GIVEN: 이런게 주어졌을 때
        when(userRepository.findById(receiver.getId())).thenReturn(Optional.of(receiver));

        // * THEN: 이런 결과가 나와야 한다
        assertThrows(IllegalArgumentException.class,
                () -> pickService.createPick(sender, new PickData.Create(1L, 1L)));
    }

    @Test
    @DisplayName("받는_사람을_찾을_수_없을_때_예외처리_테스트")
    void 받는_사람을_찾을_수_없을_때_예외처리_테스트() {
        // * THEN: 이런 결과가 나와야 한다
        assertThrows(IllegalArgumentException.class,
                () -> pickService.createPick(sender, new PickData.Create(1L, 1L)));
    }


    private Pick pickCreate(User receiver, User sender, Question question) {
        return Pick.builder()
                .receiver(receiver)
                .sender(sender)
                .question(question)
                .build();
    }

    private User userCreate(Long id, String username, char gender) {
        return User.builder()
                .id(id)
                .username(username)
                .name("이름")
                .email("이메일")
                .gender(gender)
                .providerType(ProviderType.GOOGLE)
                .roleType(RoleType.USER)
                .providerId("프로바이더 아이디")
                .isMattermostConfirmed(true)
                .isLocked(false)
                .build();
    }
}
