package com.ssapick.server.core.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fastApi", url = "${spring.localhost}")
public interface SentenceSimilarityAnalyzer {
	@GetMapping("/v1/similarity")
	String analyzeSentenceSimilarity(@RequestParam("text") String text);
}
