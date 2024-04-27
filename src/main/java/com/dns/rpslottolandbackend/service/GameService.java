package com.dns.rpslottolandbackend.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.RockPaperScissors;

@Service
public class GameService {

	private static final HashMap<RockPaperScissors, GameResult> GAME_RESOLVER = buildGameResolver();

	private static HashMap<RockPaperScissors, GameResult> buildGameResolver() {
		HashMap<RockPaperScissors, GameResult> player1GameResolver = new HashMap<>();
		player1GameResolver.put(RockPaperScissors.ROCK, GameResult.DRAW);
		player1GameResolver.put(RockPaperScissors.PAPER, GameResult.WIN_PLAYER_1);
		player1GameResolver.put(RockPaperScissors.SCISSORS, GameResult.WIN_PLAYER_2);
		return player1GameResolver;
	}

	public GameOut newGame() {
		RockPaperScissors player1RockPaperScissors = RockPaperScissors.nextRandomResult();
		GameResult gameResult = GAME_RESOLVER.get(player1RockPaperScissors);
		return toGameOut(player1RockPaperScissors, gameResult);
	}

	private GameOut toGameOut(RockPaperScissors player1RockPaperScissors, GameResult gameResult) {
		GameOut gameOut = new GameOut();
		gameOut.setPlayer1Result(player1RockPaperScissors);
		gameOut.setGameResult(gameResult);
		return gameOut;
	}
}
