package com.ssapick.server.domain.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.entity.UserBan;

public interface UserBanRepository extends JpaRepository<UserBan, Long> {


	List<User> findByFromUser(User user);
}
