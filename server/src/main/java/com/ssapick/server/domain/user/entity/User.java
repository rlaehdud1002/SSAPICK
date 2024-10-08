package com.ssapick.server.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.ssapick.server.core.entity.BaseEntity;
import com.ssapick.server.domain.pick.entity.Hint;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "followUser")
	private final List<Follow> followers = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "followingUser")
	private final List<Follow> followings = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromUser")
	private final List<UserBan> bannedUser = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Hint> hints = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private final List<Attendance> attendances = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Profile profile;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Alarm alarm;

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

	@Column(name = "is_mattermost_confirmed", nullable = false)
	private boolean isMattermostConfirmed = false;

	@Column(name = "is_locked", nullable = false)
	private boolean isLocked = false;

	@Column(name = "ban_count", nullable = false)
	@ColumnDefault("0")
	private short banCount = 0;

	/**
	 * 사용자 생성 메서드
	 *
	 * @param username     사용자 이름
	 * @param gender
	 * @param providerType 제공자 타입 (GOOGLE, NAVER, KAKAO)
	 * @param providerId   제공자 ID
	 * @return {@link User} 새롭게 생성한 유저 객체
	 */
	public static User createUser(String username, String name, char gender, ProviderType providerType,
		String providerId) {
		User user = new User();
		user.username = username;
		user.name = name;
		user.email = username;
		user.gender = gender;
		user.providerType = providerType;
		user.providerId = providerId;
		user.profile = Profile.createEmptyProfile(user);
		user.alarm = Alarm.createAlarm(user);
		return user;
	}

	public void lock() {
		this.isLocked = true;
	}

	public void unlock() {
		this.isLocked = false;
	}

	public void increaseBanCount() {
		this.banCount++;
		if (this.banCount >= 10) {
			this.lock();
		}
	}

	public void decreaseBanCount() {
		this.banCount--;
	}

	public void mattermostConfirm() {
		this.isMattermostConfirmed = true;
	}

	public void updateUser(String username, String name) {
		this.username = username;
		this.name = name;
	}

	public void updateName(String newName) {
		this.name = newName;
	}

	public void updateGender(char newGender) {
		this.gender = newGender;
	}

	public void updateProfile(Profile newProfile) {
		this.profile = newProfile;
	}

	public void updateHints(List<Hint> newHints) {
		this.hints.clear();
		for (Hint hint : newHints) {
			hint.updateUser(this);
		}
		this.hints.addAll(newHints);
	}

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", profile=" + profile +
			", username='" + username + '\'' +
			", gender=" + gender +
			", name='" + name + '\'' +
			", email='" + email + '\'' +
			", providerType=" + providerType +
			'}';
	}

	public void delete() {
		this.isDeleted = true;
		this.getProfile().delete();
		// this.bannedUser.clear();
		this.hints.clear();
	}
}
