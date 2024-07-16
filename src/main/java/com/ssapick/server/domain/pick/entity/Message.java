package com.ssapick.server.domain.pick.entity;

import com.ssapick.server.core.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pick_id", nullable = false, updatable = false)
    private Pick pick;

    @Column(nullable = false, updatable = false)
    private String content;

    @Column(nullable = false)
    private boolean toDeleted = false;

    @Column(nullable = false)
    private boolean fromDeleted = false;
}
