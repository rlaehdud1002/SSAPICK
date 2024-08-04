package com.ssapick.server.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssapick.server.core.support.UserSupport;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.entity.UserBan;
import com.ssapick.server.domain.user.repository.UserBanRepository;

@DisplayName("유저 차단 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class UserBanserviceTest extends UserSupport {
	@InjectMocks
	private UserBanservice userBanservice;

	@Mock
	private UserBanRepository userBanRepository;


	@Test
	@DisplayName("차단한_유저들_조회_테스트")
	void 차단한_유저들_조회_테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
		User userA = this.createUser("userA");
		User userB = this.createUser("userB");
		User userD = this.createUser("userD");

		when(userBanRepository.findBanUsersByFromUser(userA)).thenReturn(
			List.of(userB, userD)
		);


	    // * WHEN: 이걸 실행하면
		List<ProfileData.Search> searches = userBanservice.searchBanUsers(userA);


	    // * THEN: 이런 결과가 나와야 한다
		Assertions.assertThat(searches).hasSize(2);
		Assertions.assertThat(searches).containsExactlyInAnyOrder(
			ProfileData.Search.fromEntity(userB.getProfile()),
			ProfileData.Search.fromEntity(userD.getProfile())
		);
	}
}