package com.ssapick.server.domain.question.repository;

import static com.ssapick.server.domain.question.entity.QQuestion.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import static com.ssapick.server.domain.pick.entity.QPick.*;
import static com.ssapick.server.domain.question.entity.QQuestion.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.question.entity.Question;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssapick.server.domain.pick.entity.QPick.pick;
import static com.ssapick.server.domain.question.entity.QQuestion.question;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

	private final JPAQueryFactory queryFactory;

	/**
	 * 전체 질문 목록 조회(삭제된 질문 제외)
	 * @return
	 */
	@Override
	public List<Question> findAll() {
		return queryFactory
			.selectFrom(question)
			.where(question.isDeleted.eq(false))
			.fetch();
	}

	/**
	 * 사용자가 받은 질문 랭킹 조회
	 * @param userId
	 * @return
	 */
	@Override
	public List<Question> findQRankingByUserId(Long userId) {
		return queryFactory
			.selectFrom(question)
			.join(question.picks, pick)
			.where(pick.receiver.id.eq(userId), question.isDeleted.eq(false))
			.groupBy(question.id)
			.orderBy(pick.count().desc())
			.fetch();
    }

	/**
	 * 사용자가 등록한 질문 조회
	 * @param userId
	 * @return
	 */
    @Override
    public List<Question> findAddedQsByUserId(Long userId) {
        return queryFactory
            .selectFrom(question)
            .where(question.author.id.eq(userId))
            .fetch();
    }
}
