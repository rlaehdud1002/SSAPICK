package com.ssapick.server.domain.question.repository;

import static com.ssapick.server.domain.question.entity.QQuestion.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.question.entity.Question;

@Repository
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

	private JPAQueryFactory queryFactory;

	@Override
	public List<Question> findQuestionsByCategory_Name(String questionCategory) {
		return queryFactory
			.selectFrom(question)
			.where(question.questionCategory.name.eq(questionCategory)
				.and(question.isDeleted.eq(false)))
			.fetch();
	}

	@Override
	public List<Question> findAll() {
		return queryFactory
			.selectFrom(question)
			.where(question.isDeleted.eq(false))
			.fetch();
	}

}
