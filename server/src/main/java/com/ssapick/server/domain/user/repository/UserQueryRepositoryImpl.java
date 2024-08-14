package com.ssapick.server.domain.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.user.dto.ProfileData.Friend;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static com.ssapick.server.domain.user.entity.QCampus.campus;
import static com.ssapick.server.domain.user.entity.QFollow.follow;
import static com.ssapick.server.domain.user.entity.QProfile.profile;
import static com.ssapick.server.domain.user.entity.QUser.user;
import static com.ssapick.server.domain.user.entity.QUserBan.userBan;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<Friend> findFollowUserByUserId(Long userId) {
		Short section = getSection(userId);
		return Stream.concat(findUserByCampusId(userId, section).stream(), findUserByFollow(userId, section).stream())
			.distinct()
			.sorted(Comparator.comparingInt(Friend::getCohort))
			.sorted(Comparator.comparingInt(Friend::getCampusSection))
			.sorted(Comparator.comparing(Friend::getName))
			.toList();
	}

	@Override
	public Page<Friend> searchUserByKeyword(Long userId, String keyword, Pageable pageable) {
		Short section = getSection(userId);

		List<Friend> friends = queryFactory.select(Projections.constructor(
				Friend.class,
				user.id,
				user.name,
				user.profile.profileImage,
				user.profile.cohort,
				user.profile.campus.section,
				JPAExpressions.select(follow.isNotNull())
					.from(follow)
					.where(follow.followUser.id.eq(userId), follow.followingUser.id.eq(user.id)),

				user.profile.campus.section.eq(section)))

			.from(user)
			.leftJoin(user.profile, profile)
			.leftJoin(user.profile.campus, campus)
			.where(user.id.in(JPAExpressions.select(user.id)
					.from(user)
					.leftJoin(user.profile, profile)
					.leftJoin(user.profile.campus, campus)
					.where(
							user.id.notIn(
									JPAExpressions.select(userBan.toUser.id)
											.from(userBan)
											.where(userBan.fromUser.id.eq(userId))
							),
							user.id.ne(userId),
						user.id.ne(userId),
						user.profile.campus.eq(
							JPAExpressions.select(user.profile.campus)
								.from(user)
								.where(user.id.eq(userId))),
						user.profile.cohort.stringValue()
							.concat("기 ")
							.concat(user.profile.campus.section.stringValue())
							.concat("반 ")
							.concat(user.name)
							.like("%" + keyword + "%")
					))
			)
			.orderBy(user.profile.cohort.asc())
			.orderBy(user.profile.campus.section.asc())
			.orderBy(user.name.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(friends, pageable, () -> searchUserIds(userId, keyword));
	}

	private Long searchUserIds(Long userId, String keyword){
		return queryFactory.select(user.count())
			.from(user)
			.leftJoin(user.profile, profile)
			.leftJoin(user.profile.campus, campus)
			.where(
				user.id.notIn(
					JPAExpressions.select(userBan.toUser.id)
						.from(userBan)
						.where(userBan.fromUser.id.eq(userId))
				),
				user.id.ne(userId),
				user.profile.campus.eq(
					JPAExpressions.select(user.profile.campus)
						.from(user)
						.where(user.id.eq(userId))
				),
				user.profile.cohort.stringValue().concat("기 " + user.profile.campus.section.stringValue() + "반 ").concat(user.name) .like("%" + keyword + "%")
			).fetchOne();
	}

	private Short getSection(Long userId) {
		return queryFactory.select(user.profile.campus.section)
			.from(user)
			.where(user.id.eq(userId))
			.fetchFirst();
	}

	private List<Friend> findUserByCampusId(Long userId, Short section) {
		return queryFactory.select(Projections.constructor(
					Friend.class, user.id, user.name, user.profile.profileImage, user.profile.cohort, user.profile.campus.section, JPAExpressions.select(follow.isNotNull())
							.from(follow)
							.where(follow.followUser.id.eq(userId), follow.followingUser.id.eq(user.id)),
						user.profile.campus.section.eq(section)
				)).from(user)
				.leftJoin(user.profile, profile)
				.leftJoin(user.profile.campus, campus)
				.leftJoin(user.followings, follow)
				.where(user.profile.campus.in(
					JPAExpressions.select(user.profile.campus)
						.from(user)
						.where(user.id.eq(userId))
				))
				.where(user.id.ne(userId))
			.where(user.id.notIn(
				JPAExpressions.select(userBan.toUser.id)
					.from(userBan)
					.where((userBan.fromUser.id.eq(userId)))
			))
				.fetch();
	}



	private List<Friend> findUserByFollow(Long userId, Short section) {
		return queryFactory.select(Projections.constructor(
						Friend.class, user.id, user.name, user.profile.profileImage, user.profile.cohort, user.profile.campus.section, JPAExpressions.select(follow.isNotNull())
								.from(follow)
								.where(follow.followUser.id.eq(userId), follow.followingUser.id.eq(user.id)),
						user.profile.campus.section.eq(section)
				))
			.from(follow)
			.leftJoin(follow.followingUser, user)
			.leftJoin(user.profile, profile)
			.leftJoin(user.profile.campus, campus)
			.where(follow.followUser.id.eq(userId)
				.and(
					user.id.notIn(
						JPAExpressions.select(userBan.toUser.id)
							.from(userBan)
							.where(userBan.fromUser.id.eq(userId))
					)
				)

			)

			.fetch();
	}
}
