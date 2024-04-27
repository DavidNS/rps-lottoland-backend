package com.dns.rpslottolandbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.GameScoreOut;
import com.dns.rpslottolandbackend.dto.RockPaperScissors;
import com.dns.rpslottolandbackend.repository.PlayerScoreRepository;
import com.dns.rpslottolandbackend.repository.ScoreUpdateStrategy;
import com.dns.rpslottolandbackend.repository.TotalScoreRepository;
import com.dns.rpslottolandbackend.service.GameService;
import com.dns.rpslottolandbackend.service.ScoreService;

class GameControllerTest {

	private ScoreUpdateStrategy scoreUpdateStrategy;

	private PlayerScoreRepository playerScoreRepository;

	private TotalScoreRepository totalScoreRepository;

	private GameController testInstance;

	private GameService gameService;

	private ScoreService scoreService;

	@BeforeEach
	public void createInstance() {
		scoreUpdateStrategy = new ScoreUpdateStrategy();
		playerScoreRepository = new PlayerScoreRepository(scoreUpdateStrategy);
		totalScoreRepository = new TotalScoreRepository(scoreUpdateStrategy);
		gameService = new GameService();
		scoreService = new ScoreService(playerScoreRepository, totalScoreRepository);
		testInstance = new GameController(gameService, scoreService);
	}

	@Test
	void testAllInputOkReturnsNotNull() {
		String id = "1";
		GameScoreOut result = testInstance.newGame(id);
		assertNotNull(result);
	}

	@Test
	void testAllInputOkPlayer2IsAlwaysRock() {
		String id = "1";
		int retries = 100;
		RockPaperScissors expectedPlayer2 = RockPaperScissors.ROCK;
		for (int i = 0; i < retries; i++) {
			GameScoreOut result = testInstance.newGame(id);
			assertEquals(expectedPlayer2, result.getGameOut().getPlayer2Result());
		}
	}

	@Test
	void testAllInputOkPlayer1IsRockPaperOrScissors() {
		String id = "1";
		int retries = 100;
		List<RockPaperScissors> possibleResults = Arrays.asList(RockPaperScissors.values());
		for (int i = 0; i < retries; i++) {
			GameScoreOut result = testInstance.newGame(id);
			assertTrue(possibleResults.contains(result.getGameOut().getPlayer1Result()));
		}
	}

	@Test
	void testPlayer1WinsIfPaper() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.PAPER;
		GameResult expected = GameResult.WIN_PLAYER_1;
		GameScoreOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getGameOut().getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameOut().getGameResult());
	}

	@Test
	void testPlayer1LossIfScissors() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.SCISSORS;
		GameResult expected = GameResult.WIN_PLAYER_2;
		GameScoreOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getGameOut().getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameOut().getGameResult());
	}

	@Test
	void testPlayer1DrawIfRock() {
		String id = "1";
		RockPaperScissors resultPlayer1Condition = RockPaperScissors.ROCK;
		GameResult expected = GameResult.DRAW;
		GameScoreOut result = testInstance.newGame(id);
		while (!resultPlayer1Condition.equals(result.getGameOut().getPlayer1Result())) {
			result = testInstance.newGame(id);
		}
		assertEquals(expected, result.getGameOut().getGameResult());
	}

	@Test
	void testScoreIsSaved() {
		String id = "1";
		Integer expectedTotalGames = 10;
		for (int i = 0; i < expectedTotalGames; i++) {
			GameScoreOut result = testInstance.newGame(id);
			assertNotNull(result);
		}
		assertEquals(expectedTotalGames, scoreService.getTotalScore().getTotalGames());
	}
}
