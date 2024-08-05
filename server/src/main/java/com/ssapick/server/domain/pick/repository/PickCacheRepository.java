package com.ssapick.server.domain.pick.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final Integer LAST_INDEX = 15;
    public static final String PICK_INDEX = "pick:index:";
    private final RedisTemplate<String, Object> redisTemplate;

    public void init(Long userId) {
        String key = PICK_INDEX + userId;
        redisTemplate.opsForValue().set(key, 1);
    }

    public void increment(Long userId) {
        String key = PICK_INDEX + userId;
        Object value = redisTemplate.opsForValue().get(key);

        if (value == null){
            this.init(userId);
            return;
        }
        redisTemplate.opsForValue().set(key, (Integer) value + 1);
    }

    public Integer index(Long userId) {
        String key = PICK_INDEX + userId;
        Integer value = (Integer) redisTemplate.opsForValue().get(key);

        return value;
    }

    public void clear(Long userId) {
        String key = PICK_INDEX + userId;
        redisTemplate.delete(key);
    }
}
