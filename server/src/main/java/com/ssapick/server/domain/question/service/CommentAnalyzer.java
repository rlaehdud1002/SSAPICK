package com.ssapick.server.domain.question.service;

import com.ssapick.server.domain.question.dto.PerspectiveData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "perspectiveApi", url = "${spring.perspective.url}")
public interface CommentAnalyzer {
    @PostMapping
    PerspectiveData.Response analyzeComment(@RequestHeader("Authorization") String apiKey, @RequestBody PerspectiveData.Request request);
}
