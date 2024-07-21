package com.ssapick.server.domain.user.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/follow")
public class FollowController {
    @Authenticated
    @GetMapping(value = "")
    public SuccessResponse<Void> findFollow(@CurrentUser User user) {
        return SuccessResponse.of(null);
    }

    @Authenticated
    @PostMapping(value = "/{userId}")
    public SuccessResponse<Void> followUser(@CurrentUser User user, @PathVariable Long userId) {
        return SuccessResponse.of(null);
    }

    @Authenticated
    @DeleteMapping(value = "/{userId}")
    public SuccessResponse<Void> unfollowUser(@CurrentUser User user, @PathVariable Long userId) {
        return SuccessResponse.of(null);
    }
}
