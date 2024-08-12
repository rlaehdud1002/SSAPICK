package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserId(Long userId);
    @Query("SELECT p.profileImage FROM Profile p WHERE p.user.username = :username")
    Optional<String> findProfileImageByUsername(@Param("username") String username);
}
