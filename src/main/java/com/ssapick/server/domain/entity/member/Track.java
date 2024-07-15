package com.ssapick.server.domain.entity.member;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Track {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "track_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "campus_id",updatable = false ,foreignKey = @ForeignKey(name = "CAMPUS campus.id 외래키 참조"))
	private Campus campus;

	@Column(nullable = false, updatable = false)
	private String name;
}
