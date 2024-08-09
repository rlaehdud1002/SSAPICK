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

import com.ssapick.server.domain.pick.dto.PickData;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final int PASS_BLOCK_LIMIT = 5;
    public static final int COOL_TIME = 1;
    public static final String CACHE_INFO = "pick:cacheInfo:";

    private final RedisTemplate<String, Object> redisTemplate;



    public void init(Long userId) {
        String key = CACHE_INFO + userId;

        redisTemplate.opsForValue().set(key, PickData.Cache.builder()
            .index(0)
            .pickCount(0)
            .passCount(0)
            .blockCount(0)
            .isCooltime(false)
            .build(), Duration.ofDays(1));


    }


    public void pick(Long userId) {
        String key = CACHE_INFO + userId;

        PickData.Cache cache = (PickData.Cache)redisTemplate.opsForValue().get(key);

        cache.setPickCount(cache.getPickCount() + 1);
        cache.setIndex(cache.getIndex() + 1);

        redisTemplate.opsForValue().set(key, cache, Duration.ofDays(1));
    }
    public void block(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache)redisTemplate.opsForValue().get(key);


        cache.setBlockCount(cache.getBlockCount() + 1);
        cache.setIndex(cache.getIndex() + 1);

        redisTemplate.opsForValue().set(key, cache, Duration.ofDays(1));
    }

    public void pass(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache)redisTemplate.opsForValue().get(key);


        cache.setPassCount(cache.getPassCount() + 1);
        cache.setIndex(cache.getIndex() + 1);

        redisTemplate.opsForValue().set(key, cache, Duration.ofDays(1));
    }

    public Integer getIndex(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);
        return cache.getIndex();
    }

    public Integer getPickCount(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);
        return cache.getPickCount();
    }

    public Integer getPassCount(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);
        return cache.getPassCount();
    }

    public Integer getBlockCount(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);
        return cache.getBlockCount();
    }


    public void setCooltime(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);

        cache.setCooltime(true);

        redisTemplate.opsForValue().set(key, cache, Duration.ofMinutes(COOL_TIME));
    }

    public boolean isCooltime(Long userId) {
        String key = CACHE_INFO + userId;
        PickData.Cache cache = (PickData.Cache) redisTemplate.opsForValue().get(key);

        return cache.isCooltime();
    }

}
