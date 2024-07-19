package com.ssapick.server.domain.pick.entity;


import com.ssapick.server.core.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class HintOpen extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hint_open_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "hint_id", nullable = false,foreignKey = @ForeignKey(name = "foreign_key_hint_open_hint_id"))
    private Hint hint;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pick_id", nullable = false, updatable = false,foreignKey = @ForeignKey(name = "PICK pick_id 외래키 참조"))
    private Pick pick;

}
