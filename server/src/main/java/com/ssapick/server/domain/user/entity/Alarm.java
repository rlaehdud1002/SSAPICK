package com.ssapick.server.domain.user.entity;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.domain.user.dto.AlarmData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Alarm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;

	@OneToOne(fetch = LAZY)
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

	public void update(AlarmData.Update update) {
		this.messageAlarm = update.isMessageAlarm();
		this.nearbyAlarm = update.isNearbyAlarm();
		this.pickAlarm = update.isPickAlarm();
		this.addQuestionAlarm = update.isAddQuestionAlarm();
	}

	public void updateAll(boolean onOff) {
		this.messageAlarm = onOff;
		this.nearbyAlarm = onOff;
		this.pickAlarm = onOff;
		this.addQuestionAlarm = onOff;
	}

}
