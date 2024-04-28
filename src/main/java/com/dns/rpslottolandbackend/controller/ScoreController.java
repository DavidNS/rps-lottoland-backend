package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.service.ScoreService;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1")
public class ScoreController {

	private final ScoreService scoreService;

	@GetMapping("/scores/totals")
	public ScoreOut getTotalScore() {
		return scoreService.getTotalScore();
	}

	@GetMapping("/scores/players")
	public ScoreOut getPlayerScore(@NotBlank(message = "x-device-id headder cannot be empty or null") @RequestHeader(name = "x-device-id") String userId) {
		return scoreService.getPlayerScore(userId);
	}

	@DeleteMapping("/scores/players")
	public ScoreOut resetPlayerScore(@NotBlank(message = "x-device-id headder cannot be empty or null") @RequestHeader(name = "x-device-id") String userId) {
		return scoreService.resetPlayerScore(userId);
	}
}
