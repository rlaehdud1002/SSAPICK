package com.ssapick.server.core.entity;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract  class DeletableEntity extends TimeBaseEntity {

	@Column(name = "is_deleted", nullable = false)
	@ColumnDefault("false")
	private boolean isDeleted = false;
}
