package com.ssapick.server.domain.pick.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MessageRepositoryTest {

	@Autowired
	MessageRepository messageRepository;


	@Test
	void Test_name() throws Exception {
		//given

		//when
		messageRepository.findAllByfromUserId(1L);

		messageRepository.findAllBytoUserId(1L);


	    //then

	}
}