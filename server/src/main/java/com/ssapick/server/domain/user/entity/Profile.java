package com.ssapick.server.domain.user.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.util.Objects;

import com.ssapick.server.core.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Profile extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_profile_user_id"))
	private User user;

	@Column(nullable = false, updatable = false)
	private short cohort;

	@ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "campus_id", referencedColumnName = "campus_id", foreignKey = @ForeignKey(name = "foreign_key_profile_campus_id"))
	private Campus campus;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(nullable = false)
	private int pickco = 0;

	public void changePickco(int amount) {
		if (pickco + amount < 0) {
			throw new IllegalArgumentException("픽코가 부족합니다.");
		}
		this.pickco += amount;
	}

	public void setTestId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Profile profile = (Profile)o;
		return Objects.equals(id, profile.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public static Profile createProfile(User user, short cohort, Campus campus, String profileImage) {
		Profile profile = new Profile();
		profile.user = user;
		profile.cohort = cohort;
		profile.campus = campus;
		profile.profileImage = profileImage;
		return profile;
	}



	//	@OneToMany(mappedBy = "fromProfile",cascade = CascadeType.ALL)
	//	private Set<MemberBan> bannedToProfiles = new HashSet<>();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<Attendance> attendances = new ArrayList<>();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<PickcoLog> pickcoLogs = new ArrayList<>();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<AlarmSetting> alarmSettings = new ArrayList();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<Hint> hints = new ArrayList<>();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<QuestionBan> questionBans = new ArrayList<>();
	//
	//	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	//	private ArrayList<Notification> notifications = new ArrayList<>();
}
