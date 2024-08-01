package com.ssapick.server.core.service;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.ssapick.server.core.exception.BaseException;
import com.ssapick.server.core.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentAnalyzerService {

	private final CommentAnalyzer commentAnalyzer;
	public boolean isCommentOffensive(String content) {

		if (content.isBlank()) {
			throw new BaseException(ErrorCode.INVALID_INPUT_VALUE);
        }

		// 요청 본문 작성
		PerspectiveData.Request request = new PerspectiveData.Request();
		request.setComment(new PerspectiveData.Comment(content));

		// RequestedAttributes 객체 생성 및 설정
		PerspectiveData.RequestedAttributes requestedAttributes = new PerspectiveData.RequestedAttributes();
		requestedAttributes.setINSULT(new Object());
		requestedAttributes.setPROFANITY(new Object());
		request.setRequestedAttributes(requestedAttributes);

		// Feign 클라이언트를 통해 API 호출
		PerspectiveData.Response response = commentAnalyzer.analyzeComment(new Gson().toJson(request));

		// 응답에서 점수 추출
		double insultScore = response.getAttributeScores()
			.get("INSULT")
			.getSummaryScore()
			.getValue();

		double profanityScore = response.getAttributeScores()
			.get("PROFANITY")
			.getSummaryScore()
			.getValue();

		// 공격성 여부 판단 (예: 0.7 이상이면 공격적이라고 판단)
		double overallScore = (insultScore + profanityScore) / 2;

		return overallScore > 0.7;
	}
}
