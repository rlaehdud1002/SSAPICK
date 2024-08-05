package com.ssapick.server.domain.location.listener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final LocationSubscriber locationSubscriber;
    private final ChannelTopic locationTopic = new ChannelTopic("location");

    @PostConstruct
    public void init() {
        redisMessageListener.addMessageListener(locationSubscriber, locationTopic);
    }

    public void publish() {
//        redisTemplate.convertAndSend(locationTopic.getTopic(), message);
    }
}
