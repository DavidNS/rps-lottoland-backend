package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.ScoreOut;

@RestController
public class ScoreController {

	public ScoreOut getScore(String id) {
		return new ScoreOut();
	}

}
