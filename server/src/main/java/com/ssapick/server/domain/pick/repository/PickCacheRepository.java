package com.ssapick.server.domain.pick.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PickCacheRepository {
    public static final int PASS_BLOCK_LIMIT = 5;
	public static final int COOL_TIME = 15;

	public static final Long LOCK_TIMEOUT = 300L;
	public static final String PICK_LOCK = "pick:lock:";
    public static final String PICK_CACHE = "pick:";
	public static final String INDEX_KEY = "index";
	public static final String PICK_COUNT_KEY = "pickCount";
	public static final String PASS_COUNT_KEY = "passCount";
	public static final String BLOCK_COUNT_KEY = "blockCount";
	public static final String COOL_TIME_KEY = "coolTime";

    // private final RedisTemplate<String, Object> redisTemplate;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, String> hashOperations;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;


    public void init(Long userId) {
		String key = PICK_CACHE + userId;

		hashOperations.put(key, INDEX_KEY, "0");
		hashOperations.put(key, PICK_COUNT_KEY, "0");
		hashOperations.put(key, PASS_COUNT_KEY, "0");
		hashOperations.put(key, BLOCK_COUNT_KEY, "0");
		hashOperations.put(key, COOL_TIME_KEY, LocalDateTime.now().toString());
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
	}

	public LocalDateTime getEndTime(Long userId) {
		String key = PICK_CACHE + userId;
		return LocalDateTime.parse(hashOperations.get(key, COOL_TIME_KEY));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean lock(Long userId) {
		log.debug("run lock code");
		return Boolean.TRUE.equals(valueOperations.setIfAbsent(PICK_LOCK + userId, "use", Duration.ofMillis(LOCK_TIMEOUT)));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void unlock(Long userId) {
		log.debug("run unlock code");
		valueOperations.getOperations().delete(PICK_LOCK + userId);
	}

	public void increaseIndex(Long userId) {
		String key = PICK_CACHE + userId;

		hashOperations.put(key, INDEX_KEY, String.valueOf(Integer.parseInt(hashOperations.get(key, INDEX_KEY)) + 1));
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
	}
	public void pick(Long userId) {
		String key = PICK_CACHE + userId;

		hashOperations.put(key, PICK_COUNT_KEY, String.valueOf(Integer.parseInt(hashOperations.get(key, PICK_COUNT_KEY)) + 1));
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
    }

    public void block(Long userId) {
		String key = PICK_CACHE + userId;

		hashOperations.put(key, BLOCK_COUNT_KEY, String.valueOf(Integer.parseInt(hashOperations.get(key, BLOCK_COUNT_KEY)) + 1));
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
    }

    public void pass(Long userId) {
		String key = PICK_CACHE + userId;

		hashOperations.put(key, PASS_COUNT_KEY, String.valueOf(Integer.parseInt(hashOperations.get(key, PASS_COUNT_KEY)) + 1));
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
    }

    public Integer getIndex(Long userId) {
		String key = PICK_CACHE + userId;

		String index = hashOperations.get(key, INDEX_KEY);

		if (index == null) {
			init(userId);
			return 0;
		}

		return Integer.parseInt(index);
    }

    public Integer getPickCount(Long userId) {
		String key = PICK_CACHE + userId;

		return Integer.parseInt(hashOperations.get(key, PICK_COUNT_KEY));
    }

    public Integer getPassCount(Long userId) {
		String key = PICK_CACHE + userId;

		return Integer.parseInt(hashOperations.get(key, PASS_COUNT_KEY));
    }

    public Integer getBlockCount(Long userId) {
		String key = PICK_CACHE + userId;

		return Integer.parseInt(hashOperations.get(key, BLOCK_COUNT_KEY));
    }


    public void setCooltime(Long userId) {
		String key = PICK_CACHE + userId;
		hashOperations.put(key, COOL_TIME_KEY, LocalDateTime.now().plusMinutes(COOL_TIME).toString());
		hashOperations.getOperations().expire(key, Duration.ofDays(1));
    }

    public boolean isCooltime(Long userId) {
		String key = PICK_CACHE + userId;

		String coolTime = hashOperations.get(key, COOL_TIME_KEY);
		return LocalDateTime.now().isBefore(LocalDateTime.parse(coolTime));
    }

	public boolean isEmpty(Long userId) {
		String key = PICK_CACHE + userId;

		return hashOperations.size(key) ==0;
	}

}
