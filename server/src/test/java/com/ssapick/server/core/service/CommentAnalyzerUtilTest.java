package com.ssapick.server.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentAnalyzerUtilTest {

	@Autowired
	private CommentAnalyzerService commentAnalyzerService;

	@Autowired
	private SentenceSimilarityAnalyzerService sentenceSimilarityAnalyzerService;
	@Test
	@DisplayName("오픈페인테스트")
	void 오픈페인테스트() throws Exception {
		// * WHEN: 이걸 실행하면
		System.out.println("====================start===================");
		boolean result = commentAnalyzerService.isCommentOffensive("시발련아 죽어라!!!!!!");
		System.out.println("====================end===================");

	}

	@Test
	@DisplayName("페스트에이피아이테스트")
	void 페스트에이피아이테스트() throws Exception {
	    // * GIVEN: 이런게 주어졌을 때
		SentenceSimilarityResponse test = sentenceSimilarityAnalyzerService.analyzeSentenceSimilarity("test");

		System.out.println(test);
		// * WHEN: 이걸 실행하면

	    // * THEN: 이런 결과가 나와야 한다

	}
}