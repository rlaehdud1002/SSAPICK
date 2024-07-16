package com.ssapick.server.domain.entity.member;

import static lombok.AccessLevel.*;

import com.ssapick.server.core.entity.DeletableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member extends DeletableEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private Profile profile;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String providerType;

	@Column(nullable = false)
	private boolean isEmailVerified;

	@Column(nullable = false)
	private boolean is_locked;

}
