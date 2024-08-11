package com.ssapick.server.domain.pick.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.pick.entity.Message;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssapick.server.domain.pick.entity.QMessage.message;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Message> findReceivedMessageByUserId(Long userId, Pageable pageable) {
        List<Message> messages = queryFactory.selectFrom(message)
            .leftJoin(message.sender).fetchJoin() // 패치 조인
            .leftJoin(message.sender.profile).fetchJoin()
            .leftJoin(message.pick).fetchJoin()
            .leftJoin(message.sender.alarm).fetchJoin()
            .leftJoin(message.pick.question).fetchJoin()
            .where(message.receiver.id.eq(userId)
                .and(message.isReceiverDeleted.eq(false)))
            .orderBy(message.id.desc()) // 메시지 ID를 역순으로 정렬
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        return new PageImpl<>(messages, pageable, messages.size());
    }

    @Override
    public Page<Message> findSentMessageByUserId(Long userId, Pageable pageable) {
        List<Message> messages = queryFactory.selectFrom(message)
            .leftJoin(message.receiver).fetchJoin() // 패치 조인
            .leftJoin(message.receiver.profile).fetchJoin()
            .leftJoin(message.pick).fetchJoin()
            .leftJoin(message.receiver.alarm).fetchJoin()
            .leftJoin(message.pick.question).fetchJoin()
            .where(message.sender.id.eq(userId)
                .and(message.isSenderDeleted.eq(false)))
            .orderBy(message.id.desc()) // 메시지 ID를 역순으로 정렬
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(messages, pageable, messages.size());
    }
}