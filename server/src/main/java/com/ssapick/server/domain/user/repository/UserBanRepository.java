package com.ssapick.server.domain.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.entity.UserBan;

public interface UserBanRepository extends JpaRepository<UserBan, Long> {

	@Query("SELECT ub.toUser FROM UserBan ub JOIN FETCH ub.toUser.profile JOIN FETCH ub.toUser.alarm WHERE ub.fromUser = :user")
	List<User> findBanUsersByFromUser(User user);

	Optional<UserBan> findBanByFromUserAndToUser(User user, User findUser);
}