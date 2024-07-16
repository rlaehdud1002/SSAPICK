package com.ssapick.server.domain.entity.member;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.ssapick.server.domain.entity.notification.Notification;
import com.ssapick.server.domain.entity.pick.Hint;
import com.ssapick.server.domain.entity.question.QuestionBan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "MEMBER member.id 외래키 참조")) // @Column 어노테이션 대신 사용
	private Member member;

	@Column(nullable = false, updatable = false)
	private int cohort;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "class_id", foreignKey = @ForeignKey(name = "CLASS_SECTION class.id 외래키 참조"))
	private ClassSection classSection;

	private String profileImagePath;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private RoleType roleType = RoleType.USER;

	@Column(nullable = false)
	private int pickco;

	@OneToMany(mappedBy = "fromProfile",cascade = CascadeType.ALL)
	private Set<MemberBan> bannedToProfiles = new HashSet<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<Attendance> attendances = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<PickcoLog> pickcoLogs = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<AlarmSetting> alarmSettings = new ArrayList();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<Hint> hints = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<QuestionBan> questionBans = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	private ArrayList<Notification> notifications = new ArrayList<>();
}
