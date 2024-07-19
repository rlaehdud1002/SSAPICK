package com.ssapick.server.domain.pick.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pick extends TimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pick_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_from_user_id"))
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_to_user_id"))
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_question_id"))
    private Question question;

    @Column(name = "is_alarm_sent")
    private boolean isAlarmSent = false;

    @OneToMany(mappedBy = "pick", cascade = CascadeType.ALL)
    private List<HintOpen> hintOpens = new ArrayList<>();




}
