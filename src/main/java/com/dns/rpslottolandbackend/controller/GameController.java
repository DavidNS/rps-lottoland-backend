package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.dto.GameScoreOut;
import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.service.GameService;
import com.dns.rpslottolandbackend.service.ScoreService;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/games/players")
public class GameController {

	private final GameService gameService;

	private final ScoreService scoreService;

	@GetMapping
	public GameScoreOut newGame(@NotBlank(message = "x-device-id headder cannot be empty or null") @RequestHeader(name = "x-device-id") String userId) {
		GameOut gameOut = gameService.newGame();
		ScoreOut playerScoreOut = scoreService.saveNewPlayerScore(userId, gameOut.getGameResult());
		scoreService.saveNewTotalScore(gameOut.getGameResult());
		return toGameScoreOut(gameOut, playerScoreOut);
	}

	private GameScoreOut toGameScoreOut(GameOut gameOut, ScoreOut playerScoreOut) {
		GameScoreOut gameScoreOut = new GameScoreOut();
		gameScoreOut.setGameOut(gameOut);
		gameScoreOut.setScoreOut(playerScoreOut);
		return gameScoreOut;
	}

}
