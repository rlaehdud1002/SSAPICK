package com.ssapick.server.domain.attendance.dto;

import lombok.Data;

public class AttendanceData {

    @Data
    public static class Status {
        private int streak;
        private boolean todayChecked;
    }

    public static Status CreateStatus(int streak, boolean todayChecked) {
        Status status = new Status();
        status.streak = streak;
        status.todayChecked = todayChecked;
        return status;
    }

}
