package com.ssapick.server.domain.user.service;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.Follow;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.FollowRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@DisplayName("유저 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserServiceTest extends UserSupport {

    @InjectMocks
    private UserService userService;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PickRepository pickRepository;

    @Mock
    private FollowRepository followRepository;

    @Test
    @DisplayName("유저 정보 조회")
    void getUserInfo() {
        // Given
        User me = this.createUser("me");
        User other = this.createUser("other");

        Question question = mock(Question.class);
        List<Pick> picks = List.of(
                Pick.of(other, me, question),
                Pick.of(other, me, question)
        );

        List<Follow> followings = List.of(
                Follow.follow(other, me)
        );


        lenient().when(userRepository.findUserWithProfileById(me.getId())).thenReturn(Optional.of(me));
        lenient().when(pickRepository.findReceiverByUserId(me.getId())).thenReturn(picks);
        lenient().when(followRepository.findByFollowingUser(me)).thenReturn(followings);

        // When
//        UserData.UserInfo userInfo = userService.getUserInfo(me);

//         Then
//        assertThat(userInfo.getName()).isEqualTo(me.getName());
//        assertThat(userInfo.getPickCount()).isEqualTo(picks.size());
//        assertThat(userInfo.getFollowingCount()).isEqualTo(followings.size());

    }

}