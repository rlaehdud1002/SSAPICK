package com.ssapick.server.domain.ranking.service;

import com.ssapick.server.domain.pick.entity.Message;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.pick.repository.MessageRepository;
import com.ssapick.server.domain.pick.repository.PickRepository;
import com.ssapick.server.domain.ranking.dto.RankingData;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RankingService {

    private final PickRepository pickRepository;
    private final MessageRepository messageRepository;

    public RankingData.Response getAllRanking() {

        List<Pick> picks = pickRepository.findAllWithReceiverAndSenderAndQuestion();

        List<RankingData.UserCount> topPickReceivers = getTopPickReceivers(picks);
        List<RankingData.UserCount> topPickSenders = getTopPickSenders(picks);

        List<Message> messages = messageRepository.findAllWithReceiverAndSender();



        return new RankingData.Response(topPickReceivers, topPickSenders);
    }

    private List<RankingData.UserCount> getTopPickSenders(List<Pick> picks) {
        Map<RankingData.UserRankingProfile, Long> pickReceiverCount = picks.stream()
                .collect(Collectors.groupingBy(
                        pick -> {
                            User receiver = pick.getSender();
                            Profile profile = receiver.getProfile();

                            return new RankingData.UserRankingProfile(
                                    receiver.getName(),
                                    profile.getCohort(),
                                    profile.getCampus(),
                                    profile.getProfileImage());
                        },
                        Collectors.counting()
                ));

        List<RankingData.UserCount> topPickSenders = pickReceiverCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> new RankingData.UserCount(entry.getKey(), entry.getValue()))
                .toList();

        return topPickSenders;
    }

    private static List<RankingData.UserCount> getTopPickReceivers(List<Pick> picks) {
        Map<RankingData.UserRankingProfile, Long> pickReceiverCount = picks.stream()
                .collect(Collectors.groupingBy(
                        pick -> {
                            User receiver = pick.getReceiver();
                            Profile profile = receiver.getProfile();

                            return new RankingData.UserRankingProfile(
                                    receiver.getName(),
                                    profile.getCohort(),
                                    profile.getCampus(),
                                    profile.getProfileImage());
                        },
                        Collectors.counting()
                ));

        List<RankingData.UserCount> topPickReceivers = pickReceiverCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .map(entry -> new RankingData.UserCount(entry.getKey(), entry.getValue()))
                .toList();

        return topPickReceivers;
    }
}
