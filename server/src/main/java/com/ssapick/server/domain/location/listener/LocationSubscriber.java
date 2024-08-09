package com.ssapick.server.domain.location.listener;

import com.google.gson.Gson;
import com.ssapick.server.domain.location.dto.LocationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationSubscriber implements MessageListener {
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
        LocationData.Geo request = new Gson().fromJson(publishMessage, LocationData.Geo.class);
        if (request.getLatitude() != 0 && request.getLongitude() != 0) {
            messagingTemplate.convertAndSend("/sub/location/update", "change");
        }
    }
}
