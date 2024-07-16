package com.ssapick.server.domain.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "campus", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "section"})
})
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campus_id")
    private Long id;

    /** 캠퍼스 이름 */
    @Column(nullable = false, updatable = false)
    private String name;

    /** 캠퍼스 내 소속한 반 */
    @Column(nullable = false, updatable = false)
    private int section;

    /** 트랙에 대한 설명 */
    @Column
    private String description;
}
