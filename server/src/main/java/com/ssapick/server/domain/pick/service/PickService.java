package com.ssapick.server.domain.pick.service;

import com.ssapick.server.domain.pick.dto.PickData;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.repository.QuestionRepository;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PickService {

    private static final Logger log = LoggerFactory.getLogger(PickService.class);
    private final PickRepository pickRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;


    /**
     * 받은 픽 조회하기
     *
     * @param userId
     * @return List<PickData.Search>
     */
    public List<PickData.Search> searchReceiver(Long userId) {
        return pickRepository.findReceiverByUserId(userId).stream()
            .map((Pick pick) -> PickData.Search.fromEntity(pick, true))
            .toList();

    }

    /**
     * 보낸 픽 조회하기
     *
     * @param userId
     * @return
     */
    public List<PickData.Search> searchSender(Long userId) {
        return pickRepository.findSenderByUserId(userId).stream()
            .map((Pick pick) -> PickData.Search.fromEntity(pick, false))
            .toList();
    }

    /**
     * 픽 생성하기
     * @param create
     */
    public void createPick(User user, PickData.Create create) {
        User receiver = userRepository.findById(create.getReceiverId()).orElseThrow(
            () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        Question question = questionRepository.findById(create.getQuestionId()).orElseThrow(
            () -> new IllegalArgumentException("해당 질문이 존재하지 않습니다.")
        );

        pickRepository.save(Pick.of(user, receiver, question));
    }
}
