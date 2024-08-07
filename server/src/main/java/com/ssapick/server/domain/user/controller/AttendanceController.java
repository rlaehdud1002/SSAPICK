package com.ssapick.server.domain.user.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.AttendanceData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * 출석 조회
     * @param user
     * @return SuccessResponse<AttendanceData.Status>
     */
    @Authenticated
    @GetMapping
    public SuccessResponse<AttendanceData.Status> getUserAttendance(
            @CurrentUser User user
    ) {
        return SuccessResponse.of(attendanceService.getUserAttendanceStatus(user));
    }

    /**
     * 출석 생성
     *
     * @param user
     * @return SuccessResponse<Void>
     */
    @Authenticated
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<AttendanceData.Status> checkIn(
            @CurrentUser User user
    ) {
        attendanceService.checkIn(user);

        return SuccessResponse.of(attendanceService.getUserAttendanceStatus(user));
    }
}
