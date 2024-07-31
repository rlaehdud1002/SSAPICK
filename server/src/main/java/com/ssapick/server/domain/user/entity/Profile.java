package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_profile_user_id"))
    private User user;

    @Column(updatable = false)
    private short cohort;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "campus_id", referencedColumnName = "campus_id", foreignKey = @ForeignKey(name = "foreign_key_profile_campus_id"))
    private Campus campus;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(nullable = false)
    private int pickco = 0;

    public static Profile createEmptyProfile(User user) {
        Profile profile = new Profile();
        profile.user = user;
        return profile;
    }

    public static Profile createProfile(User user, short cohort, Campus campus, String profileImage) {
        Profile profile = new Profile();
        profile.user = user;
        profile.cohort = cohort;
        profile.campus = campus;
        profile.profileImage = profileImage;
        return profile;
    }

    public void changePickco(int amount) {
        if (pickco + amount < 0) {
            throw new IllegalArgumentException("픽코가 부족합니다.");
        }
        this.pickco += amount;
    }

    public void setTestId(Long id) {
        this.id = id;
    }

    public void delete() {
        this.isDeleted = true;
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
