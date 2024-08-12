package com.ssapick.server.domain.user.repository;

import static com.ssapick.server.domain.user.entity.QAlarm.*;
import static com.ssapick.server.domain.user.entity.QCampus.*;
import static com.ssapick.server.domain.user.entity.QFollow.follow;
import static com.ssapick.server.domain.user.entity.QProfile.*;
import static com.ssapick.server.domain.user.entity.QUser.user;
import static com.ssapick.server.domain.user.entity.QUserBan.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.dto.ProfileData.Friend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	public Page<Friend> findRecommendFriends(Long userId, Pageable pageable) {
		QFollow f1 = QFollow.follow;
		QFollow f2 = new QFollow("f2");
		QUser u = QUser.user;
		Short section = getSection(userId);

		// 전체 결과 수를 계산
		long total = queryFactory.selectFrom(f1)
			.join(f2).on(f1.followingUser.eq(f2.followUser))
			.join(u).on(f2.followingUser.eq(u))
			.where(f1.followUser.id.eq(userId),
				f2.followingUser.id.ne(userId),
				u.notIn(JPAExpressions.select(f1.followingUser)
					.from(f1)
					.where(f1.followUser.id.eq(userId))),
				u.id.notIn(JPAExpressions.select(userBan.toUser.id)
					.from(userBan)
					.where(userBan.fromUser.id.eq(userId))))
			.fetchCount();

		// 페이징 처리된 결과
		List<Friend> friends = queryFactory.select(Projections.constructor(
				Friend.class,
				u.id,
				u.name,
				u.profile.profileImage,
				u.profile.cohort,
				u.profile.campus.section,
				JPAExpressions.select(f1.isNotNull())
					.from(f1)
					.where(f1.followUser.id.eq(userId), f1.followingUser.id.eq(u.id)),
				u.profile.campus.section.eq(section)
			))
			.from(f1)
			.join(f2).on(f1.followingUser.eq(f2.followUser))
			.join(u).on(f2.followingUser.eq(u))
			.where(f1.followUser.id.eq(userId),
				f2.followingUser.id.ne(userId),
				u.notIn(JPAExpressions.select(f1.followingUser)
					.from(f1)
					.where(f1.followUser.id.eq(userId))),
				u.id.notIn(JPAExpressions.select(userBan.toUser.id)
					.from(userBan)
					.where(userBan.fromUser.id.eq(userId))))
			.groupBy(
				u.id,
				u.name,
				u.profile.profileImage,
				u.profile.cohort,
				u.profile.campus.section
			)
			.orderBy(f2.followingUser.count().desc())
			.offset(pageable.getOffset())  // 페이지 시작 위치
			.limit(pageable.getPageSize())  // 페이지 크기
			.fetch();

		return new PageImpl<>(friends, pageable, total);
	}

	private Short getSection(Long userId) {
		return queryFactory.select(user.profile.campus.section)
				.from(user)
				.where(user.id.eq(userId))
				.fetchFirst();
	}

}

