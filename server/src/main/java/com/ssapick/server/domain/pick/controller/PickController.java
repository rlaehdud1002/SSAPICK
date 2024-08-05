package com.ssapick.server.domain.pick.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.service.PickService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/pick")
public class PickController {
    private final PickService pickService;

    /**
     * 받은 픽 조회하는 API
     *
     * @param user 로그인한 유저
     * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 받은 픽 리스트
     */
    @Authenticated
    @GetMapping("/receive")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<List<PickData.Search>> getReceivePick(@CurrentUser User user) {
        return SuccessResponse.of(pickService.searchReceivePick(user));
    }

    /**
     * 보낸 픽 조회하는 API
     *
     * @param user 로그인한 유저
     * @return {@link com.ssapick.server.domain.pick.dto.PickData.Search} 보낸 픽 리스트
     */
    @Authenticated
    @GetMapping("/send")
    @ResponseStatus(value = HttpStatus.OK)
    public SuccessResponse<List<PickData.Search>> getSendPick(@CurrentUser User user) {
        return SuccessResponse.of(pickService.searchSendPick(user));
    }


    /**
     * 픽 생성하는 API
     *
     * @param user   로그인한 유저
     * @param create {@link com.ssapick.server.domain.pick.dto.PickData.Create} 픽 생성 정보
     * @return 처리 성공 응답
     */
    @Authenticated
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Void> createPick(
            @CurrentUser User user,
            @RequestBody PickData.Create create
    ) {
        pickService.createPick(user, create);
        return SuccessResponse.empty();
    }


    /**
     * 픽 알람설정 API
     *
     *
     */
    @PatchMapping("/{pickId}")
    public SuccessResponse<Void> updatePickAlarm(
            @CurrentUser User user,
            @PathVariable("pickId") Long pickId
    ) {
        pickService.updatePickAlarm(user, pickId);
        return SuccessResponse.empty();
    }
}
