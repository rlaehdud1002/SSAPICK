package com.ssapick.server.domain.entity.pick;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.ssapick.server.core.entity.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Message extends TimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "message_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "pick_id", nullable = false, updatable = false)
	private Pick pick;

	@Column(nullable = false, updatable = false)
	private String content;

	@Column(nullable = false)
	private boolean toDeleted;

	@Column(nullable = false)
	private boolean fromDeleted;
}
