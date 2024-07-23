package com.ssapick.server.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(
	name = "campus",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"name", "section"})
	},
	indexes = {
		@Index(name = "index_campus_name", columnList = "name")
	}
)
public class Campus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campus_id")
	private Long id;

	/** 캠퍼스 이름 */
	@Column(nullable = false)
	private String name;

	/** 캠퍼스 내 소속한 반 */
	@Column(nullable = false, updatable = false)
	private short section;

	/** 트랙에 대한 설명 */
	@Column
	private String description;

	public static Campus createCampus(String name, short section, String description) {
		Campus campus = new Campus();
		campus.name = name;
		campus.section = section;
		campus.description = description;
		return campus;
	}
}
