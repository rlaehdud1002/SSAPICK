package com.ssapick.server.domain.user.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import static com.ssapick.server.domain.user.entity.QCampus.campus;
import static com.ssapick.server.domain.user.entity.QFollow.follow;
import static com.ssapick.server.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findFollowUserByUserId(Long userId) {
        return Stream.concat(findUserByCampusId(userId).stream(), findUserByFollow(userId).stream())
                .distinct()
                .toList();
    }

    private List<User> findUserByCampusId(Long userId) {
        return queryFactory.select(user)
            .from(user)
            .where(user.profile.campus.in(
                JPAExpressions.select(user.profile.campus)
                    .from(user)
                    .where(user.id.eq(userId))
            ))
            .where(user.id.ne(userId))  // 현재 사용자 ID를 제외
            .fetch();
    }

    private List<User> findUserByFollow(Long userId) {
        // return queryFactory.select(user)
        //         .from(user)
        //         .join(user.followers, follow)
        //         .leftJoin(user.profile).fetchJoin()
        //         .where(user.id.eq(userId))
        //         .fetch();
        //
        return queryFactory.select(follow.followingUser)
                .from(follow)
                .where(follow.followUser.id.eq(userId))
                .fetch();
    }
}
