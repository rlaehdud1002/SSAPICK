package com.ssapick.server.domain.user.event;

import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.PickcoLogType;
import com.ssapick.server.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PickcoEvent {
    private User user;
    private PickcoLogType type;
    private int amount;
    private int current;

    public PickcoLog toEntity() {
        return PickcoLog.createPickcoLog(user, type, amount, current + amount);
    }
}
