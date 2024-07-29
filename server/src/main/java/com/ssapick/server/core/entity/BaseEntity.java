package com.ssapick.server.core.entity;

import jakarta.persistence.EntityListeners;
import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends TimeEntity {
	@Column(name = "is_deleted", nullable = false)
	@ColumnDefault("false")
    protected boolean isDeleted = false;
}
