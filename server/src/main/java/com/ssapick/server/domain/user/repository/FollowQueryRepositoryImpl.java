package com.ssapick.server.domain.user.repository;

import static com.ssapick.server.domain.user.entity.QAlarm.*;
import static com.ssapick.server.domain.user.entity.QCampus.*;
import static com.ssapick.server.domain.user.entity.QProfile.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.user.entity.QFollow;
import com.ssapick.server.domain.user.entity.QUser;
import com.ssapick.server.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepositoryImpl implements FollowQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<User> findRecommendUserIds(User user) {
		QFollow f1 = QFollow.follow;
		QFollow f2 = new QFollow("f2");
		QUser u = QUser.user;

		return queryFactory
			.selectFrom(u)
			.leftJoin(u.profile, profile).fetchJoin()
			.leftJoin(u.alarm, alarm).fetchJoin()
			.leftJoin(u.profile.campus, campus).fetchJoin()
			.where(u.id.in(JPAExpressions.select(f2.followingUser.id)
				.from(f1)
				.join(f2).on(f1.followingUser.eq(f2.followUser))
				.where(f1.followUser.ne(f2.followingUser))
				.groupBy(f2.followingUser)
				.orderBy(f2.followingUser.id.count().desc()))
			).fetch();
	}

}

