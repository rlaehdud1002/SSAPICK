package com.ssapick.server.domain.location.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.location.service.LocationService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping(value = "")
    public SuccessResponse<LocationData.Response> getUserLocation(
            @CurrentUser User user
        ) {
        return SuccessResponse.of(locationService.findFriends(user));
    }

    @Authenticated
    @PostMapping(value = "")
    public SuccessResponse<Void> pickUser(@CurrentUser User user, @RequestBody LocationData.PickRequest request) {
        locationService.pickUser(user, request.getUsername());
        return SuccessResponse.empty();
    }
}
