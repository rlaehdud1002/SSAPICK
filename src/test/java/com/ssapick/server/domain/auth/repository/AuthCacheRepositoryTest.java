package com.ssapick.server.domain.auth.repository;

import com.ssapick.server.core.config.RedisTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AuthCacheRepository.class)
@Import({RedisTestConfig.class})
class AuthCacheRepositoryTest {
    @Test
    @DisplayName("캐시_값_저장_테스트")
    void 캐시_값_저장_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때

        // * WHEN: 이걸 실행하면

        // * THEN: 이런 결과가 나와야 한다
    }
}