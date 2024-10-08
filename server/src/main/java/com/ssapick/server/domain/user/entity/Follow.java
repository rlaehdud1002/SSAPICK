package com.ssapick.server.domain.user.entity;

import com.ssapick.server.core.entity.TimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Table(
    name = "follow",
    uniqueConstraints = {
        @UniqueConstraint(name= "unique_follow", columnNames = {"follower_id", "following_id"})
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follower_id", nullable = false ,foreignKey = @ForeignKey(name = "foreign_key_follow_follower_id"))
    private User followUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "following_id", nullable = false ,foreignKey = @ForeignKey(name = "foreign_key_follow_following_id"))
    private User followingUser;

    public static Follow follow(User followUser, User followingUser) {
        Follow follow = new Follow();
        follow.followUser = followUser;
        follow.followingUser = followingUser;
        return follow;
    }
}
