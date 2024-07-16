package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	@JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_profile_user_id")) // @Column 어노테이션 대신 사용
	private User user;

	@Column(nullable = false, updatable = false)
	private short cohort;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "campus_id", referencedColumnName = "campus_id", foreignKey = @ForeignKey(name = "foreign_key_profile_campus_id"))
	private Campus campus;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(nullable = false)
	private int pickco = 0;

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
