package com.ssapick.server.domain.ranking.service;

import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;
import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.user.entity.PickcoLog;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.PickcoLogRepository;
import com.ssapick.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RankingService {

    private final PickRepository pickRepository;
    private final MessageRepository messageRepository;
    private final PickcoLogRepository pickcoLogRepository;
    private final UserRepository userRepository;

    public RankingData.Response getAllRanking() {

        List<Pick> picks = pickRepository.findAllWithReceiverAndSenderAndQuestion();
        List<Message> messages = messageRepository.findAllWithReceiverAndSender();
        List<PickcoLog> pickcoLogs = pickcoLogRepository.findAllSpendWithUser();
        List<User> pickcoUsers = userRepository.findTopPickcoUsers();

        List<RankingData.UserCount> topPickReceivers = getTopUsers(picks, Pick::getReceiver);
        List<RankingData.UserCount> topPickSenders = getTopUsers(picks, Pick::getSender);
        List<RankingData.UserCount> topMessageReceivers = getTopUsers(messages, Message::getReceiver);
        List<RankingData.UserCount> topMessageSenders = getTopUsers(messages, Message::getSender);
        List<RankingData.UserCount> topSpendPickcoUsers = getTopSpendPickcoUsers(pickcoLogs);
        List<RankingData.UserCount> topReservePickcoUsers = getTopReservePickcoUsers(pickcoUsers);
        List<RankingData.QuestionUserRanking> questionUserRanking = getQuestionUserRanking(picks);


        return new RankingData.Response(
                topPickReceivers,
                topPickSenders,
                topMessageReceivers,
                topMessageSenders,
                topSpendPickcoUsers,
                topReservePickcoUsers,
                questionUserRanking
        );
    }

    private static List<RankingData.QuestionUserRanking> getQuestionUserRanking(List<Pick> picks) {
        Map<Long, List<Pick>> picksByQuestion = picks.stream()
                .collect(Collectors.groupingBy(p -> p.getQuestion().getId()));

        return picksByQuestion.entrySet().stream()
                .map(entry -> {
                    Long questionId = entry.getKey();
                    List<Pick> questionPicks = entry.getValue();

                    // Receiver별 픽 수 계산
                    Map<User, Long> pickCountByReceiver = questionPicks.stream()
                            .collect(Collectors.groupingBy(Pick::getReceiver, Collectors.counting()));

                    // 가장 많이 픽을 받은 사용자 찾기
                    Map.Entry<User, Long> topReceiverEntry = pickCountByReceiver.entrySet().stream()
                            .max(Map.Entry.comparingByValue())
                            .orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_PICK));

                    User topReceiver = topReceiverEntry.getKey();
                    Long pickCount = topReceiverEntry.getValue();

                    return new RankingData.QuestionUserRanking(
                            questionId,
                            questionPicks.get(0).getQuestion().getContent(), // 질문 내용
                            topReceiver.getId(),
                            topReceiver.getName(),
                            topReceiver.getProfile().getProfileImage(),
                            topReceiver.getProfile().getCohort(),
                            topReceiver.getProfile().getCampus().getName(),
                            topReceiver.getProfile().getCampus().getSection(),
                            pickCount
                    );
                })
                .toList();
    }

    private static List<RankingData.UserCount> getTopReservePickcoUsers(List<User> pickcoUsers) {
        return pickcoUsers.stream()
                .filter(user -> user.getProfile() != null)
                .map(user -> new RankingData.UserCount(
                        new RankingData.UserRankingProfile(
                                user.getName(),
                                user.getProfile().getCohort(),
                                user.getProfile().getCampus(),
                                user.getProfile().getProfileImage()
                        ),
                        (long) user.getProfile().getPickco()
                ))
                .toList();
    }

    private <T> List<RankingData.UserCount> getTopUsers(List<T> items, Function<T, User> userExtractor) {

        Map<RankingData.UserRankingProfile, Long> userCountMap = items.stream()
                .collect(Collectors.groupingBy(
                        item -> {
                            User user = userExtractor.apply(item);
                            Profile profile = user.getProfile();

                            return new RankingData.UserRankingProfile(
                                    user.getName(),
                                    profile.getCohort(),
                                    profile.getCampus(),
                                    profile.getProfileImage());
                        },
                        Collectors.counting()
                ));

        return userCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> new RankingData.UserCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private List<RankingData.UserCount> getTopSpendPickcoUsers(List<PickcoLog> pickcoLogs) {

        Map<RankingData.UserRankingProfile, Integer> userSpendMap = pickcoLogs.stream()
                .collect(Collectors.groupingBy(
                        log -> {
                            User user = log.getUser();
                            Profile profile = user.getProfile();
                            return new RankingData.UserRankingProfile(
                                    user.getName(),
                                    profile.getCohort(),
                                    profile.getCampus(),
                                    profile.getProfileImage()
                            );
                        },
                        Collectors.summingInt(log -> -log.getChange())  // Convert negative values to positive
                ));

        // Sorting the map by values in descending order and limiting to top 3
        return userSpendMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> new RankingData.UserCount(entry.getKey(), entry.getValue().longValue()))
                .collect(Collectors.toList());
    }
}
