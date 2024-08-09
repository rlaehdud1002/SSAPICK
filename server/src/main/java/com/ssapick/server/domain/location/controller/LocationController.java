package com.ssapick.server.domain.location.controller;

import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.location.service.LocationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping(value = "")
    public SuccessResponse<List<LocationData.Response>> getUserLocation(
            @CurrentUser User user
        ) {
        return SuccessResponse.of(locationService.findFriends(user));
    }
}
