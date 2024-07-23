package com.ssapick.server.domain.question.entity;

import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
public class QuestionRegistration {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_registration_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_category_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_question_category_id"))
    private QuestionCategory questionCategory;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_question_user_id"))
    private User author;
}
