package com.ssapick.server.domain.question.repository;

import static com.ssapick.server.domain.pick.entity.QPick.*;
import static com.ssapick.server.domain.question.entity.QQuestion.*;

import java.util.List;
import java.util.Optional;

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
			.join(question.picks, pick).fetchJoin()
                .where(pick.receiver.id.eq(userId), question.isDeleted.eq(false))
                .groupBy(question.id)
                .orderBy(pick.count().desc())
                .fetch();
    }

    @Override
    public List<Question> findAddedQuestionsById(Long id) {
        return queryFactory
            .selectFrom(question)
            .where(question.author.id.eq(id))
            .fetch();
    }
}
