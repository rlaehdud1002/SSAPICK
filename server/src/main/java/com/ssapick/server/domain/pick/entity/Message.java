package com.ssapick.server.domain.pick.entity;

import static jakarta.persistence.FetchType.*;

import com.ssapick.server.core.entity.TimeEntity;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "pick_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_message_pick_id"))
    private Pick pick;

    @Column(nullable = false, updatable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_message_sender"))
    private User sender;


    @Column(nullable = false)
    private boolean isSenderDeleted = false;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "foreign_key_message_receiver"))
    private User receiver;

    @Column(nullable = false)
    private boolean isReceiverDeleted = false;

    @Column(name = "is_alarm_sent")
    private boolean isAlarmSent = false;

    public static Message createMessage(User sender, User receiver, Pick pick, String content) {
        Message message = new Message();
        message.sender = sender;
        message.receiver = receiver;
        message.pick = pick;
        message.content = content;
        return message;
    }

    public void sendAlarm() {
        isAlarmSent = true;
    }

    public void deleteMessageOfSender() {
        isSenderDeleted = true;
    }

    public void deleteMessageOfReceiver() {
        isReceiverDeleted = true;
    }
}
