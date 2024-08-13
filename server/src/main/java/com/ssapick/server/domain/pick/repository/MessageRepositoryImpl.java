package com.ssapick.server.domain.pick.repository;

import static com.ssapick.server.domain.pick.entity.QMessage.*;
import static com.ssapick.server.domain.user.entity.QUserBan.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.pick.entity.Message;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Message> findReceivedMessageByUserId(Long userId, Pageable pageable) {
        // 메시지 목록 쿼리
        List<Message> messages = queryFactory.selectFrom(message)
            .leftJoin(message.sender).fetchJoin() // 패치 조인
            .leftJoin(message.sender.profile).fetchJoin()
            .leftJoin(message.pick).fetchJoin()
            .leftJoin(message.sender.alarm).fetchJoin()
            .leftJoin(message.pick.question).fetchJoin()
            .where(message.receiver.id.eq(userId)
                .and(message.isReceiverDeleted.eq(false))
                .and(message.sender.id.notIn(
                    queryFactory.select(userBan.toUser.id)
                        .from(userBan)
                        .where(userBan.fromUser.id.eq(userId)
                        ))
                ))
            .orderBy(message.id.desc()) // 메시지 ID를 역순으로 정렬
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 전체 메시지 수를 계산하는 카운트 쿼리
        long total = queryFactory.selectFrom(message)
            .where(message.receiver.id.eq(userId)
                .and(message.isReceiverDeleted.eq(false))
                .and(message.sender.id.notIn(
                    queryFactory.select(userBan.toUser.id)
                        .from(userBan)
                        .where(userBan.fromUser.id.eq(userId)
                        ))
                ))
            .fetchCount();

        return new PageImpl<>(messages, pageable, total);
    }

    @Override
    public Page<Message> findSentMessageByUserId(Long userId, Pageable pageable) {
        // 메시지 목록 쿼리
        List<Message> messages = queryFactory.selectFrom(message)
            .leftJoin(message.receiver).fetchJoin() // 패치 조인
            .leftJoin(message.receiver.profile).fetchJoin()
            .leftJoin(message.pick).fetchJoin()
            .leftJoin(message.receiver.alarm).fetchJoin()
            .leftJoin(message.pick.question).fetchJoin()
            .where(message.sender.id.eq(userId)
                .and(message.isSenderDeleted.eq(false))
                .and(message.receiver.id.notIn(
                    queryFactory.select(userBan.toUser.id)
                        .from(userBan)
                        .where(userBan.fromUser.id.eq(userId)
                        )))
            )
            .orderBy(message.id.desc()) // 메시지 ID를 역순으로 정렬
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 전체 메시지 수를 계산하는 카운트 쿼리
        long total = queryFactory.selectFrom(message)
            .where(message.sender.id.eq(userId)
                .and(message.isSenderDeleted.eq(false))
                .and(message.receiver.id.notIn(
                    queryFactory.select(userBan.toUser.id)
                        .from(userBan)
                        .where(userBan.fromUser.id.eq(userId)
                        ))
                ))
            .fetchCount();

        return new PageImpl<>(messages, pageable, total);
    }
}