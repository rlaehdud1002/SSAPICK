package com.ssapick.server.core;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommentAnalyzer {

	@Value("${spring.comment-analyzer.api-key}")
	private static String API_KEY; // 실제 API 키로 대체해야 합니다.

	@Value("${spring.comment-analyzer.base-url}")
	private static String BASE_URL ;

	public static boolean isCommentOffensive(String content) {
		try {
			String requestUrl = BASE_URL + API_KEY;
			URL url = new URL(requestUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setDoOutput(true);

			// JSON 요청 본문 작성
			JSONObject jsonRequest = new JSONObject();
			JSONObject comment = new JSONObject();
			comment.put("text", content);
			jsonRequest.put("comment", comment);
			jsonRequest.put("requestedAttributes", new JSONObject()
				.put("INSULT", new JSONObject())
				.put("PROFANITY", new JSONObject()));

			try (BufferedWriter httpRequestBodyWriter = new BufferedWriter(
				new OutputStreamWriter(httpConnection.getOutputStream(), "UTF-8"))) {
				httpRequestBodyWriter.write(jsonRequest.toString());
				httpRequestBodyWriter.flush();
			}

			// 응답 코드 확인
			int responseCode = httpConnection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// 응답 내용 읽기
			StringBuilder response = new StringBuilder();
			try (BufferedReader responseBuffer = new BufferedReader(
				new InputStreamReader(httpConnection.getInputStream()))) {
				String output;
				while ((output = responseBuffer.readLine()) != null) {
					response.append(output);
				}
			}

			// JSON 응답 파싱
			JSONObject jsonResponse = new JSONObject(response.toString());

			double insultScore = jsonResponse.getJSONObject("attributeScores")
				.getJSONObject("INSULT")
				.getJSONObject("summaryScore")
				.getDouble("value");

			double profanityScore = jsonResponse.getJSONObject("attributeScores")
				.getJSONObject("PROFANITY")
				.getJSONObject("summaryScore")
				.getDouble("value");

			// 종합 점수는 예제 응답에서 제공되지 않았기 때문에 각 점수를 평균내어 사용
			double overallScore = (insultScore + profanityScore) / 2;

			System.out.println("INSULT Score: " + insultScore);
			System.out.println("PROFANITY Score: " + profanityScore);
			System.out.println("Overall Score: " + overallScore);

			if (overallScore > 0.7) {
				return true;
			}
			httpConnection.disconnect();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return false;
	}
}
