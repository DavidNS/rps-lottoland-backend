package com.dns.rpslottolandbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.RockPaperScissors;
import com.dns.rpslottolandbackend.service.GameService;

class GameControllerTest {

	private GameController testInstance;

	private GameService gameService;

	@BeforeEach
	public void createInstance() {
		gameService = new GameService();
		testInstance = new GameController(gameService);
	}

	@Test
	void testAllInputOkReturnsNotNull() {
		String id = "1";
		GameOut result = testInstance.newGame(id);
		assertNotNull(result);
	}

	@Test
	void testAllInputOkPlayer2IsAlwaysRock() {
		String id = "1";
		int retries = 100;
		RockPaperScissors expectedPlayer2 = RockPaperScissors.ROCK;
		for (int i = 0; i < retries; i++) {
			GameOut result = testInstance.newGame(id);
			assertEquals(expectedPlayer2, result.getPlayer2Result());
		}
	}

	@Test
	void testAllInputOkPlayer1IsRockPaperOrScissors() {
		String id = "1";
		int retries = 100;
		List<RockPaperScissors> possibleResults = Arrays.asList(RockPaperScissors.values());
		for (int i = 0; i < retries; i++) {
			GameOut result = testInstance.newGame(id);
			assertTrue(possibleResults.contains(result.getPlayer1Result()));
		}
	}

	@Test
	void testPlayer1WinsIfPaper() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.PAPER;
		GameResult expected = GameResult.WIN_PLAYER_1;
		GameOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameResult());
	}

	@Test
	void testPlayer1LossIfScissors() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.SCISSORS;
		GameResult expected = GameResult.WIN_PLAYER_2;
		GameOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameResult());
	}

	@Test
	void testPlayer1DrawIfRock() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.ROCK;
		GameResult expected = GameResult.DRAW;
		GameOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameResult());
	}

}
