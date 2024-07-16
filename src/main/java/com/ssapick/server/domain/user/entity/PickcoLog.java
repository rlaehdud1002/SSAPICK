package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.user.entity.type.PickcoLogType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PickcoLog extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pickco_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pickco_log_profile_id"))
    private Profile profile;

    @Column(name = "pickco_log_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PickcoLogType pickcoLogType;

    @Column(nullable = false)
    private int change;

    @Column(nullable = false)
    private int remain;
}
