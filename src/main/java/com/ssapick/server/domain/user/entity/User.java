package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.BaseEntity;
import com.ssapick.server.domain.pick.entity.Hint;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followUser")
    private List<Follow> followers = new ArrayList<>();

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "is_mattermost_confirmed", nullable = false)
    private boolean isMattermostConfirmed = false;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = false;

	/**
	 * 사용자 생성 메서드
	 *
	 * @param username     사용자 이름
	 * @param gender
	 * @param providerType 제공자 타입 (GOOGLE, NAVER, KAKAO)
	 * @param providerId   제공자 ID
	 * @return {@link User} 새롭게 생성한 유저 객체
	 */
	public static User createUser(String username, String name, char gender, ProviderType providerType, String providerId) {
		User user = new User();
		user.username = username;
		user.name = name;
		user.email = username;
		user.gender = gender;
		user.providerType = providerType;
		user.providerId = providerId;
		return user;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Hint> hints = new ArrayList<>();


	public void mattermostConfirm() {
		this.isMattermostConfirmed = true;
	}

	@Builder
	private User(Long id, String username, String name, String email, ProviderType providerType,
		RoleType roleType, String providerId,
		boolean isMattermostConfirmed, boolean isLocked) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.providerType = providerType;
		this.roleType = roleType;
		this.providerId = providerId;
		this.isMattermostConfirmed = isMattermostConfirmed;
		this.isLocked = isLocked;
	}

	public void updateUser(String username, String name) {
		this.username = username;
		this.name = name;
	}

}
