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
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class MessageComplaints extends TimeEntity {

	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "message_complaints_id")
	private Long id;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "message_id", nullable = false, updatable = false)
	private Message message;

	@Column(nullable = false)
	private String complaint_reason;

}
