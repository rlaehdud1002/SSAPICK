package com.ssapick.server.domain.question;

import jakarta.persistence.*;

@Entity
@Table(name = "question_category")
public class QuestionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_category_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String thumbnail;
}
