package com.ssapick.server.domain.pick.service;

import java.util.List;
import java.util.Objects;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.core.service.CommentAnalyzerService;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final PickRepository pickRepository;
    private final CommentAnalyzerService commentAnalyzer;
    private final UserRepository userRepository;

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
    @Async("apiExecutor")
    @Transactional
    public void createMessage(User sender, MessageData.Create create) {
        Pick pick = pickRepository.findById(create.getPickId()).orElseThrow(
            () ->new BaseException(ErrorCode.NOT_FOUND_PICK)
        );

        if (pick.isMessageSend()) {
            throw new BaseException(ErrorCode.ALREADY_SEND_MESSAGE);
        }
		pick.send();


        User receiver = userRepository.findById(create.getReceiverId()).orElseThrow(
            () -> new BaseException(ErrorCode.NOT_FOUND_USER)
        );


        if (commentAnalyzer.isCommentOffensive(create.getContent())){
            throw new BaseException(ErrorCode.OFFENSIVE_CONTENT);
        }

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
            () -> new BaseException(ErrorCode.NOT_FOUND_MESSAGE)
        );

        if (!Objects.equals(message.getReceiver().getId(), receiver.getId())) {
            throw new BaseException(ErrorCode.FORBIDDEN);
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
            () -> new BaseException(ErrorCode.NOT_FOUND_MESSAGE)
        );

        if (!Objects.equals(message.getSender().getId(), sender.getId())) {
            throw new BaseException(ErrorCode.FORBIDDEN);
        }

        message.deleteMessageOfSender();
    }
}
