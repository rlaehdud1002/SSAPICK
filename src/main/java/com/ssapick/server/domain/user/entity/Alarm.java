package com.ssapick.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_alarm_user_id"))
    private User user;

    @Column(name = "message_alarm", nullable = false)
    private boolean messageAlarm = true;

    @Column(name = "nearby_alarm", nullable = false)
    private boolean nearbyAlarm = true;

    @Column(name = "pick_alarm", nullable = false)
    private boolean pickAlarm = true;

    @Column(name = "add_question_alarm", nullable = false)
    private boolean addQuestionAlarm = true;

    public static Alarm createAlarm(User user) {
        Alarm alarm = new Alarm();
        alarm.user = user;
        return alarm;
    }
}
