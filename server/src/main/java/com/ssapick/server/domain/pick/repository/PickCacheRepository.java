package com.ssapick.server.domain.pick.repository;

import java.time.Duration;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final int PASS_BLOCK_LIMIT = 5;
    public static final int COOL_TIME = 1;
    public static final String QUESTION_INDEX = "pick:questionIndex:";
    public static final String PICK_COUNT = "pick:pickCount:";
    public static final String PASS_COUNT = "pick:pass:";
    public static final String BLOCK_COUNT = "pick:ban:";
    public static final String PICK_COOLTIME = "pick:cooltime:";

    private final RedisTemplate<String, Object> redisTemplate;



    public void init(Long userId) {
        String questionIndexKey = QUESTION_INDEX + userId;
        String pickCountKey = PICK_COUNT + userId;
        String passCountKey = PASS_COUNT + userId;
        String blockCountKey = BLOCK_COUNT + userId;

        redisTemplate.opsForValue().set(questionIndexKey, 0, Duration.ofDays(1));
        redisTemplate.opsForValue().set(pickCountKey, 0, Duration.ofDays(1));
        redisTemplate.opsForValue().set(passCountKey, 0, Duration.ofDays(1));
        redisTemplate.opsForValue().set(blockCountKey, 0, Duration.ofDays(1));
    }


    public void pick(Long userId) {
        String questionIndexKey = QUESTION_INDEX + userId;
        String countKey = PICK_COUNT + userId;

        redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().increment(questionIndexKey);
                operations.expire(questionIndexKey, Duration.ofDays(1));
                operations.opsForValue().increment(countKey);
                operations.expire(countKey, Duration.ofDays(1));
                return operations.exec();
            }
        });
    }
    public void block(Long userId) {
        String blockKey = BLOCK_COUNT + userId;
        String questionIndexKey = QUESTION_INDEX + userId;
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().increment(questionIndexKey);
                operations.expire(questionIndexKey, Duration.ofDays(1));
                operations.opsForValue().increment(blockKey);
                operations.expire(blockKey, Duration.ofDays(1));
                return operations.exec();
            }
        });
    }

    public void pass(Long userId) {
        String passKey = PASS_COUNT + userId;
        String questionIndexKey = QUESTION_INDEX + userId;

        redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().increment(questionIndexKey);
                operations.expire(questionIndexKey, Duration.ofDays(1));
                operations.opsForValue().increment(passKey);
                operations.expire(passKey, Duration.ofDays(1));
                return operations.exec();
            }
        });
    }

    public Integer getIndex(Long userId) {
        String key = QUESTION_INDEX + userId;
        return (Integer)redisTemplate.opsForValue().get(key);
    }

    public Integer getPickCount(Long userId) {
        String key = PICK_COUNT + userId;
        Integer value = (Integer) redisTemplate.opsForValue().get(key);
        return value;
    }

    public Integer getPassCount(Long userId) {
        String key = PASS_COUNT + userId;
        Integer value = (Integer) redisTemplate.opsForValue().get(key);
        return value;
    }

    public Integer getBlockCount(Long userId) {
        String key = BLOCK_COUNT + userId;
        Integer value = (Integer) redisTemplate.opsForValue().get(key);
        return value;
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
