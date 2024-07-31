package com.ssapick.server.domain.pick.controller;

import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.domain.auth.controller.AuthController;
import com.ssapick.server.domain.pick.service.HintService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("힌트 컨트롤러 테스트")
@WebMvcTest(
        value = HintController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
public class HintControllerTest {
    @MockBean
    private HintService hintService;

//    @Test
//    @DisplayName("픽 아이디를 받으면 랜덤한 힌트를 반환한다")
//    public void getRandomHintByPickId() {
//        // given
//        Long pickId = 1L;
//
//        // when
//        String hint = hintService.getRandomHintByPickId(pickId);
//
//        // then
//        assertNotNull(hint);
//    }
}
