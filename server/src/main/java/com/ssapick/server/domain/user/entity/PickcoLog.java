package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PickcoLog extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pickco_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pickco_log_user_id"))
    private User user;

    @Column(name = "pickco_log_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PickcoLogType pickcoLogType;

    @Column(nullable = false)
    private int change;

    @Column(nullable = false)
    private int remain;

    public static PickcoLog createPickcoLog(User user, PickcoLogType pickcoLogType, int change, int remain) {
        PickcoLog pickcoLog = new PickcoLog();
        pickcoLog.user = user;
        pickcoLog.pickcoLogType = pickcoLogType;
        pickcoLog.change = change;
        pickcoLog.remain = remain;
        return pickcoLog;
    }
}
