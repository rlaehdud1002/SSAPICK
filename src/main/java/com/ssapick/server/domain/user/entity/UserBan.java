package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.TimeEntity;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class UserBan extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_ban_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id", referencedColumnName = "user_id", nullable = false ,foreignKey = @ForeignKey(name = "foreign_key_user_ban_from_user_id"))
    private User fromUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id", referencedColumnName = "user_id", nullable = false ,foreignKey = @ForeignKey(name = "foreign_key_user_ban_to_user_id"))
    private User toUser;
}
