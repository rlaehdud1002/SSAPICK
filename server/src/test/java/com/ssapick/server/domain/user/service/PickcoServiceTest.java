package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.event.PickcoEvent;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("픽코 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class PickcoServiceTest {

    @Mock
    private PickcoLogRepository pickcoLogRepository;

    @InjectMocks
    private PickcoService pickcoService;

    private User user;
    private Profile profile;

    @BeforeEach
    public void setUp() {
        user = mock(User.class);
        profile = mock(Profile.class);
        when(user.getProfile()).thenReturn(profile);
    }

    @DisplayName("픽코가 충분하면 픽코를 줄이고 픽코 로그 생성")
    @Test
    public void createPickcoLogWithSufficientPickco() {
        // Given
        int initialPickco = 10;
        int changeAmount = -5;
        when(profile.getPickco()).thenReturn(initialPickco);

        PickcoEvent event = new PickcoEvent(user, PickcoLogType.HINT_OPEN, changeAmount);

        // When
        pickcoService.createPickcoLog(event);

        // Then
        verify(profile).changePickco(changeAmount);
        verify(pickcoLogRepository).save(argThat(pickcoLog ->
                pickcoLog.getRemain() == initialPickco + changeAmount
        ));
    }

    @DisplayName("픽코가 부족하면 예외 발생")
    @Test
    public void createPickcoLogWithInsufficientPickco() {
        // Given
        int initialPickco = 3;
        int changeAmount = -5;
        when(profile.getPickco()).thenReturn(initialPickco);

        PickcoEvent event = new PickcoEvent(user, PickcoLogType.HINT_OPEN, changeAmount);

        // When
        assertThrows(IllegalArgumentException.class, () -> {
            pickcoService.createPickcoLog(event);
        });

        // Then
        verify(profile, never()).changePickco(changeAmount);
        verify(pickcoLogRepository, never()).save(any());
    }

}
