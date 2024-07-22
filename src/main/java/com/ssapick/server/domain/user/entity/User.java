package com.ssapick.server.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssapick.server.core.entity.BaseEntity;
import com.ssapick.server.domain.pick.entity.Hint;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Hint> hints = new ArrayList<>();

	@Builder
	private User(Long id, String username, String name, String email, ProviderType providerType,
		RoleType roleType, String providerId,
		boolean isEmailVerified, boolean isLocked) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.providerType = providerType;
		this.roleType = roleType;
		this.providerId = providerId;
		this.isEmailVerified = isEmailVerified;
		this.isLocked = isLocked;
	}

}
