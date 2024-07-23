package com.ssapick.server.domain.pick.repository;

import static com.ssapick.server.domain.pick.entity.QMessage.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.pick.entity.Message;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Message> findReceivedMessageByUserId(Long userId) {
		return queryFactory.selectFrom(message)
				.where(message.receiver.id.eq(userId)
				.and(message.isReceiverDeleted.isFalse()))
				.fetch();
	}

	@Override
	public List<Message> findSentMessageByUserId(Long userId) {
		return queryFactory.selectFrom(message)
				.where(message.sender.id.eq(userId)
				.and(message.isSenderDeleted.isFalse()))
				.fetch();
	}
}