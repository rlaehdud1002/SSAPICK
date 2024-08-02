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
			.join(question.picks, pick).fetchJoin()
                .where(pick.receiver.id.eq(userId), question.isDeleted.eq(false))
                .groupBy(question.id)
                .orderBy(pick.count().desc())
                .fetch();
    }

    @Override
    public List<Question> findAddedQuestionsByUserId(Long userId) {
        return queryFactory
            .selectFrom(question)
            .where(question.author.id.eq(userId))
            .fetch();
    }
}
