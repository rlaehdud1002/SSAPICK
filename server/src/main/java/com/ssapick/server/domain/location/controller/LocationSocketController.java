package com.ssapick.server.domain.location.controller;

import com.ssapick.server.domain.location.listener.LocationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LocationSocketController {
    private final LocationPublisher locationPublisher;

    @MessageMapping("/location/update")
    public void update() {
        locationPublisher.publish();
    }
}
