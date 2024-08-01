package com.ssapick.server.core.service;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentenceSimilarityAnalyzerService {

	private final SentenceSimilarityAnalyzer sentenceSimilarityAnalyzer;

	public SentenceSimilarityResponse analyzeSentenceSimilarity(String text) {
		String json = sentenceSimilarityAnalyzer.analyzeSentenceSimilarity(text);

		Gson gson = new Gson();

		// JSON 문자열을 JsonObject로 변환
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

		// "result" 배열을 가져와서 첫 번째 요소를 double로 변환
		JsonElement resultArray = jsonObject.get("result");
		if (resultArray != null && resultArray.isJsonArray()) {
			String firstElement = resultArray.getAsJsonArray().get(0).getAsString();
			String secondElement = resultArray.getAsJsonArray().get(1).getAsString();

			double value = Double.parseDouble(firstElement);
			String description = secondElement;

			// SentenceSimilarity 객체 생성 및 출력
			SentenceSimilarityResponse sentenceSimilarity = new SentenceSimilarityResponse(value, description);
			return sentenceSimilarity;
		}

		return null;
	}
}
