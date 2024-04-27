package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.service.ScoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ScoreController {

	private final ScoreService scoreService;

	public ScoreOut getTotalScore() {
		return scoreService.getTotalScore();
	}

}
