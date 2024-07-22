package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "username")},
        indexes = {
                @Index(name = "index_user_username", columnList = "username")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private char gender;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.USER;

    @OneToMany(fetch = LAZY, mappedBy = "followUser")
    private List<Follow> followers = new ArrayList<>();

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "is_email_verified", nullable = false)
    private boolean isEmailVerified = false;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = false;

    /**
     * 사용자 생성 메서드
     * @param username 사용자 이름
     * @param providerType 제공자 타입 (GOOGLE, NAVER, KAKAO)
     * @param providerId 제공자 ID
     * @return {@link User} 새롭게 생성한 유저 객체
     */
    public static User createUser(String username, String name, ProviderType providerType, String providerId) {
        User user = new User();
        user.username = username;
        user.name = name;
        user.email = username;
        user.providerType = providerType;
        user.providerId = providerId;
        return user;
    }

    public void updateUser(String name, String email) {

    }
}
