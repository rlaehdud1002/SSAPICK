package com.ssapick.server.domain.question.entity;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;


/**
 * 질문 밴 엔티티
 * Author : 성민
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "question_bans",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "question_id"})
        }
)
public class QuestionBan extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_ban_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_question_ban_user_id"))
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_question_ban_question_id"))
    private Question question;


    public static QuestionBan of(User user, Question question) {
        QuestionBan questionBan = new QuestionBan();
        questionBan.user = user;
        questionBan.question = question;
        return questionBan;
    }
}
