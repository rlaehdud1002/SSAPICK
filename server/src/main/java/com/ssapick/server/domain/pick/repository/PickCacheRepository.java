package com.ssapick.server.domain.pick.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final int NOT_EXIST = -1;
    public static final String PICK_INDEX = "pick:index:";
    private final RedisTemplate<String, Object> redisTemplate;

    public void init(Long userId) {
        String key = PICK_INDEX + userId;
        redisTemplate.opsForValue().set(key, 1);
    }

    public void increment(Long userId) {
        String key = PICK_INDEX + userId;
        Object value = redisTemplate.opsForValue().get(key);
        if (!(value instanceof Integer)) {
            throw new IllegalArgumentException("픽 인덱스가 올바르지 않습니다.");
        }
        redisTemplate.opsForValue().set(key, (int) value + 1);
    }

    public int index(Long userId) {
        String key = PICK_INDEX + userId;
        Object value = redisTemplate.opsForValue().get(key);
        if (!(value instanceof Integer)) {
            return NOT_EXIST;
        }
        return (int) value;
    }

    public void clear(Long userId) {
        String key = PICK_INDEX + userId;
        redisTemplate.delete(key);
    }
}
