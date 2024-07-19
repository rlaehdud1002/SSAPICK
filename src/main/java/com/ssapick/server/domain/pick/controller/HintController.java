package com.ssapick.server.domain.pick.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssapick.server.domain.pick.dto.HintData;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hint")
public class HintController {



	@GetMapping("")
	public List<HintData.Id> getHintByPickId(Long pickId) {

		return null;
	}

}
