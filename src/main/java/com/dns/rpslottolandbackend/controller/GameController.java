package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.dto.GameScoreOut;
import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.service.GameService;
import com.dns.rpslottolandbackend.service.ScoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class GameController {

	private final GameService gameService;

	private final ScoreService scoreService;

	public GameScoreOut newGame(String id) {
		GameOut gameOut = gameService.newGame();
		ScoreOut playerScoreOut = scoreService.saveNewPlayerScore(id, gameOut.getGameResult());
		scoreService.saveNewTotalScore(gameOut.getGameResult());
		GameScoreOut gameScoreOut = new GameScoreOut();
		gameScoreOut.setGameOut(gameOut);
		gameScoreOut.setScoreOut(playerScoreOut);
		return gameScoreOut;
	}

}
