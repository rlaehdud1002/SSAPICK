package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final PickRepository pickRepository;
    private final EntityManager em;

    /**
     * 보낸 메시지 조회하기
     * 보낸 메시지를 조회하고 익명으로 보내진 메시지는 익명으로 표시한다.
     *
     * @param user 로그인된 사용자
     * @return {@link MessageData.Search} 보낸 메시지 리스트
     */
    public List<MessageData.Search> searchSendMessage(User user) {
        return messageRepository.findSentMessageByUserId(user.getId()).stream()
                .map((message) -> MessageData.Search.fromEntity(message, false))
                .toList();
    }

    /**
     * 받은 메시지 조회하기
     * 받은 메시지를 조회하고 익명으로 보내진 메시지는 익명으로 표시한다.
     *
     * @param user 로그인된 사용자
     * @return {@link MessageData.Search} 받은 메시지 리스트
     */
    public List<MessageData.Search> searchReceiveMessage(User user) {
        return messageRepository.findReceivedMessageByUserId(user.getId()).stream()
                .map((message) -> MessageData.Search.fromEntity(message, true))
                .toList();
    }

    /**
     * 메시지 보내기
     * 메시지를 보내고 픽의 메시지 전송 여부를 true 로 변경한다.
     *
     * @param sender 보내는 사람
     * @param create {@link MessageData.Create} 메시지 생성 정보
     */
    @Transactional
    public void createMessage(User sender, MessageData.Create create) {
        Pick pick = pickRepository.findById(create.getPickId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 픽입니다.")
        );

        if (pick.isMessageSend()) {
            throw new IllegalArgumentException("하나의 픽에 대해서는 하나의 메시지만 보낼 수 있습니다.");
        }
		pick.send();

        // FIXME: 유저에 대한 정보를 entity manager 를 사용해서 가져오는데 이게 올바른 방법일까?
        User receiver = em.getReference(User.class, create.getReceiverId());

        messageRepository.save(Message.createMessage(sender, receiver, pick, create.getContent()));
    }

    /**
     * 받은 메시지 삭제하기
     * 로그인된 사용자를 받아 로그인 된 사용자가 받은 메시지라면 메시지를 삭제한다.
     *
     * @param receiver  메시지를 받은 사람
     * @param messageId 받은 메시지 ID
     */
    @Transactional
    public void deleteReceiveMessage(User receiver, Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 메시지입니다.")
        );

        if (!Objects.equals(message.getReceiver().getId(), receiver.getId())) {
            throw new IllegalArgumentException("본인이 받은 메시지만 삭제할 수 있습니다.");
        }

        message.deleteMessageOfReceiver();
    }

    /**
     * 보낸 메시지 삭제하기
     * 로그인된 사용자를 받아 로그인 된 사용자가 보낸 메시지라면 메시지를 삭제한다.
     *
     * @param sender    메시지를 보낸 사람
     * @param messageId 보낸 메시지 ID
     */
    @Transactional
    public void deleteSendMessage(User sender, Long messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 메시지입니다.")
        );

        if (!Objects.equals(message.getSender().getId(), sender.getId())) {
            throw new IllegalArgumentException("본인이 보낸 메시지만 삭제할 수 있습니다.");
        }

        message.deleteMessageOfSender();
    }
}
