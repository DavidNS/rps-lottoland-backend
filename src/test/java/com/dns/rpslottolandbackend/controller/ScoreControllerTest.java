package com.dns.rpslottolandbackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.entity.ScoreEntity;
import com.dns.rpslottolandbackend.repository.PlayerScoreRepository;
import com.dns.rpslottolandbackend.repository.ScoreUpdateStrategy;
import com.dns.rpslottolandbackend.repository.TotalScoreRepository;
import com.dns.rpslottolandbackend.service.ScoreService;

class ScoreControllerTest {

	private ScoreUpdateStrategy scoreUpdateStrategy;
	
	private PlayerScoreRepository playerScoreRepository;
	
	private TotalScoreRepository totalScoreRepository;

	private ScoreService testInstance;

	@BeforeEach
	public void createInstance() {
		scoreUpdateStrategy = new ScoreUpdateStrategy();
		playerScoreRepository = new PlayerScoreRepository(scoreUpdateStrategy);
		totalScoreRepository = new TotalScoreRepository(scoreUpdateStrategy);
		testInstance = new ScoreService(playerScoreRepository, totalScoreRepository);
	}
	
	@Test
	void testAllInputOkReturnsNotNull() {
		ScoreOut result = testInstance.getTotalScore();
		assertNotNull(result);
	}

	
	@Test
	void testPlayerScoresAreThreadSafe() throws InterruptedException {
		String id1 = "1";
		String id2 = "2";
		int iterationsWin1 = 1000;
		int iterationsLoss1 = 1001;
		int iterationsDraw1 = 1002;
		int iterationsWin2 = 1003;
		int iterationsLoss2 = 1004;
		int iterationsDraw2 = 1005;
		
		Runnable player1Win = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsWin1; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id1, GameResult.WIN_PLAYER_1);
			}
			assertEquals(iterationsWin1, lastScoreEntity.getPlayer1Wins());
		};

		Runnable player1Loss = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsLoss1; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id1, GameResult.WIN_PLAYER_2);
			}
			assertEquals(iterationsLoss1, lastScoreEntity.getPlayer2Wins());
		};
		
		Runnable player1Draw = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsDraw1; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id1, GameResult.DRAW);
			}
			assertEquals(iterationsDraw1, lastScoreEntity.getDraws());
		};
		
		Runnable player2Win = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsWin2; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id2, GameResult.WIN_PLAYER_1);
			}
			assertEquals(iterationsWin2, lastScoreEntity.getPlayer1Wins());
		};

		Runnable player2Loss = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsLoss2; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id2, GameResult.WIN_PLAYER_2);
			}
			assertEquals(iterationsLoss2, lastScoreEntity.getPlayer2Wins());
		};
		
		Runnable player2Draw = () -> {
			ScoreEntity lastScoreEntity = null;
			for (int i = 0; i < iterationsDraw2; i++) {
				lastScoreEntity = playerScoreRepository.updatePlayerScore(id2, GameResult.DRAW);
			}
			assertEquals(iterationsDraw2, lastScoreEntity.getDraws());
		};
		
		Thread t1 = new Thread(player1Win, "Win1");
		Thread t2 = new Thread(player1Loss, "Loss1");
		Thread t3 = new Thread(player1Draw, "Draw1");
		Thread t4 = new Thread(player2Win, "Win2");
		Thread t5 = new Thread(player2Loss, "Loss2");
		Thread t6 = new Thread(player2Draw, "Draw2");
		
		List<Thread> allThreads = Arrays.asList(t1, t2, t3, t4, t5, t6);
		
		for (Thread thread : allThreads) {
			thread.start();
		}
		
		for (Thread thread : allThreads) {
			thread.join();
		}
	}
	
	@Test
	void testTotalScoresAreThreadSafe() throws InterruptedException {
		int iterationsWin = 2000;
		int iterationsLoss = 2001;
		int iterationsDraw = 2002;
		
		Runnable player1Win = () -> {
			for (int i = 0; i < iterationsWin; i++) {
				totalScoreRepository.updateTotalScore(GameResult.WIN_PLAYER_1);
			}
		};

		Runnable player1Loss = () -> {
			for (int i = 0; i < iterationsLoss; i++) {
				totalScoreRepository.updateTotalScore(GameResult.WIN_PLAYER_2);
			}
		};
		
		Runnable player1Draw = () -> {
			for (int i = 0; i < iterationsDraw; i++) {
				totalScoreRepository.updateTotalScore(GameResult.DRAW);
			}
		};
		
		Thread t1 = new Thread(player1Win, "Win");
		Thread t2 = new Thread(player1Loss, "Loss");
		Thread t3 = new Thread(player1Draw, "Draw");
		
		List<Thread> allThreads = Arrays.asList(t1, t2, t3);
		
		for (Thread thread : allThreads) {
			thread.start();
		}
		for (Thread thread : allThreads) {
			thread.join();
		}
		
		assertEquals(iterationsWin, totalScoreRepository.getTotalScore().getPlayer1Wins());
		assertEquals(iterationsLoss, totalScoreRepository.getTotalScore().getPlayer2Wins());
		assertEquals(iterationsDraw, totalScoreRepository.getTotalScore().getDraws());
	}
}
