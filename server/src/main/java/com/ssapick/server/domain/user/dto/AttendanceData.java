package com.ssapick.server.domain.user.dto;

import lombok.Data;

public class AttendanceData {

    public static Status CreateStatus(int streak, boolean todayChecked) {
        Status status = new Status();
        status.streak = streak;
        status.todayChecked = todayChecked;
        return status;
    }

    @Data
    public static class Status {
        private int streak;
        private boolean todayChecked;
    }

}
