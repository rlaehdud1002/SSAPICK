package com.ssapick.server.core.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "perspectiveApi", url = "${spring.comment-analyzer.base-url}" + "${spring.comment-analyzer.api-key}")
public interface CommentAnalyzer {
    @PostMapping
    PerspectiveData.Response analyzeComment(@RequestBody String request);
}
