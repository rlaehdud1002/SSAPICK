package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.BaseEntity;
import com.ssapick.server.domain.user.entity.type.ProviderType;
import com.ssapick.server.domain.user.entity.type.RoleType;
import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")}
)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Profile profile;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "is_email_verified", nullable = false)
    private boolean isEmailVerified = false;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = false;
}
