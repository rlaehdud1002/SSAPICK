package com.ssapick.server.domain.question.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssapick.server.core.entity.BaseEntity;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_category_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_question_category_id"))
    private QuestionCategory questionCategory;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_question_user_id"))
    private User author;

    @Column(name = "ban_count", nullable = false)
    private int banCount = 0;

    @OneToMany(mappedBy = "question")
    private List<Pick> picks = new ArrayList<>();

    @Column(name = "is_alarm_sent")
    private boolean isAlarmSent = false;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public static Question createQuestion(QuestionCategory category, String content, User author) {
        Question question = new Question();
        question.questionCategory = category;
        question.content = content;
        question.author = author;
        return question;
    }

    public static Question of(Long questionId, String content) {
        Question question = new Question();
        question.id = questionId;
        question.content = content;
        return question;
    }
}
