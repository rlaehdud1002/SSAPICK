package com.ssapick.server.domain.location.controller;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.location.service.LocationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/test")
    public void test(@CurrentUser User user) {
        locationService.saveUserLocation(user, new LocationData.Geo(0.4, 0.3));
    }
}
