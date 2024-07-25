package com.ssapick.server.domain.question.repository;

import static com.ssapick.server.domain.question.entity.QQuestion.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.question.entity.Question;

@Repository
public class QuestionQueryRepositoryImpl implements QuestionQueryRepository {

	private JPAQueryFactory queryFactory;

	@Override
	public List<Question> findAll() {
		return queryFactory
			.selectFrom(question)
			.where(question.isDeleted.eq(false))
			.fetch();
	}

	@Override
	public List<Question> findRanking(Long userId) {

		return queryFactory
			.select(question)
			.from(question)
			// .join(question.pick, pick).fetchJoin()
			//     .where(pick.receiver.id.eq(userId))
			//     .groupBy(question.id)
			//     .orderBy(pick.count().desc())
			.fetch();
	}
}
