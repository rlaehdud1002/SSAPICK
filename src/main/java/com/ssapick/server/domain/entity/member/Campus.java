package com.ssapick.server.domain.entity.member;

import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Campus {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campus_id")
	private Long id;

	@Column(nullable = false, updatable = false)
	private String name;

}
