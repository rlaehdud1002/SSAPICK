package com.ssapick.server.domain.location.controller;

import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.location.listener.LocationPublisher;
import com.ssapick.server.domain.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LocationSocketController {
    private final LocationService locationService;
    private final LocationPublisher locationPublisher;

    @MessageMapping("/location/update")
    public void update(
            @Header("Authorization") String authentication,
            LocationData.Geo geo
    ) {
        log.debug("user: {}, geo: {}", authentication, geo);
        log.debug("authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        locationService.saveUserLocation("user.getUsername()", geo);
        locationPublisher.publish(geo);
    }
}
