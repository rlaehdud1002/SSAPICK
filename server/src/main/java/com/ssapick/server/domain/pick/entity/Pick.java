package com.ssapick.server.domain.pick.entity;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pick extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pick_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_from_user_id"))
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_to_user_id"))
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "foreign_key_pick_question_id"))
    private Question question;

    @Column(name = "is_alarm_sent")
    private boolean isAlarmSent = false;

    @Column(name = "is_message_sent")
    private boolean isMessageSend = false;

    @OneToMany(mappedBy = "pick", cascade = CascadeType.ALL)
    private List<HintOpen> hintOpens = new ArrayList<>();

    public static Pick of(User sender, User receiver, Question question) {
        Pick pick = new Pick();
        pick.sender = sender;
        pick.receiver = receiver;
        pick.question = question;
        return pick;
    }

    public void send() {
        this.isMessageSend = true;
    }
}
