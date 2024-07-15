package com.ssapick.server.domain.entity.notification;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import java.util.ArrayList;
import java.util.Objects;

import com.ssapick.server.domain.entity.member.Profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Notification {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id", nullable = false, updatable = false,foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile profile;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;


	@Column(nullable = false)
	private Long typeId;

}
