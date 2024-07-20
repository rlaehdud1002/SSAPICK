package com.ssapick.server.domain.pick.entity;

import static jakarta.persistence.FetchType.*;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pick_id", nullable = false, updatable = false)
    private Pick pick;

    @Column(nullable = false, updatable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_user_id", nullable = false, updatable = false)
    private User fromUser;


    @Column(nullable = false)
    private boolean fromDeleted = false;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_user_id", nullable = false, updatable = false)
    private User toUser;

    @Column(nullable = false)
    private boolean toDeleted = false;

    @Column(name = "is_alarm_sent")
    private boolean isAlarmSent = false;


    public static Message of(MessageData.Create create) {
        Message message = new Message();
        message.content = create.getContent();
        message.pick = create.getPick();
        message.fromUser = create.getFromUser();
        message.toUser = create.getToUser();
        return message;
    }

    public void deleteFromMessage() {
        fromDeleted = true;
    }

    public void deleteToMessage() {
        toDeleted = true;
    }
}
