package com.ssapick.server.domain.entity.member;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.core.entity.TimeBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class MemberBan extends TimeBaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_ban_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "profile_id",nullable = false ,foreignKey = @ForeignKey(name = "PROFILE profile_id 외래키 참조"))
	private Profile fromProfile;

	// @ManyToOne(fetch = LAZY)
	// @JoinColumn(name = "profile_id",nullable = false ,foreignKey = @ForeignKey(name = "PROFILE profile.id 외래키 참조"))
	// private Profile toProfile;
	private Long toProfileId;
}