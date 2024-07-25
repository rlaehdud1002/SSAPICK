package com.ssapick.server.domain.user.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/follow")
public class FollowController {
    private final FollowService followService;

    @Authenticated
    @GetMapping(value = "")
    public SuccessResponse<List<ProfileData.Search>> findFollow(@CurrentUser User user) {
        log.debug("user: {}", user);
        return SuccessResponse.of(followService.findFollowUsers(user));
    }

    @Authenticated
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> followUser(@CurrentUser User user, @PathVariable Long userId) {
        followService.followUser(user, userId);
        return SuccessResponse.empty();
    }

    @Authenticated
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> unfollowUser(@CurrentUser User user, @PathVariable Long userId) {
        followService.unfollowUser(user, userId);
        return SuccessResponse.empty();
    }
}
