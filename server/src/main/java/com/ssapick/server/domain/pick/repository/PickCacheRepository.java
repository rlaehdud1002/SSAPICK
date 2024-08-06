package com.ssapick.server.domain.pick.repository;

import java.time.Duration;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final int LAST_INDEX = 15;
    public static final int COOL_TIME = 15;
    public static final String PICK_INDEX = "pick:index:";
    public static final String PICK_COOLTIME = "pick:cooltime:";

    private final RedisTemplate<String, Object> redisTemplate;

    public void init(Long userId) {
        String key = PICK_INDEX + userId;
        redisTemplate.opsForValue().set(key, 1, Duration.ofDays(1));
    }

    public void increment(Long userId) {
        String key = PICK_INDEX + userId;
        Object value = redisTemplate.opsForValue().get(key);

        redisTemplate.opsForValue().set(key, (Integer) value + 1, Duration.ofDays(1));
    }

    public Integer index(Long userId) {
        String key = PICK_INDEX + userId;
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null){
            this.init(userId);
            return 1;
        }

        return (Integer) value;
    }

    public void setCooltime(Long userId) {
        String key = PICK_COOLTIME + userId;
        redisTemplate.opsForValue().set(key, true, Duration.ofMinutes(COOL_TIME));
    }

    public boolean isCooltime(Long userId) {
        String key = PICK_COOLTIME + userId;
        return redisTemplate.hasKey(key);
    }


    // public void clear(Long userId) {
    //     String key = PICK_INDEX + userId;
    //     redisTemplate.delete(key);
    // }
}
