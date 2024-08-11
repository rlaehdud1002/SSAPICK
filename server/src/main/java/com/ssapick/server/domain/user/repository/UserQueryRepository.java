package com.ssapick.server.domain.user.repository;

import com.amazonaws.SdkBaseException;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserQueryRepository {
    List<ProfileData.Friend> findFollowUserByUserId(Long userId);
    Page<ProfileData.Friend> searchUserByKeyword(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);
}
