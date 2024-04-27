package com.dns.rpslottolandbackend.repository;

import java.util.HashMap;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.entity.ScoreEntity;

@Component
public class ScoreUpdateStrategy {

	private static final HashMap<GameResult, Consumer<ScoreEntity>> SCORE_UPDATE_STRATEGY = buildScoreUpdateStrategy();

	private static HashMap<GameResult, Consumer<ScoreEntity>> buildScoreUpdateStrategy() {
		HashMap<GameResult, Consumer<ScoreEntity>> strategyMap = new HashMap<>();
		strategyMap.put(GameResult.WIN_PLAYER_1, (s) -> s.setPlayer1Wins(s.getPlayer1Wins() + 1));
		strategyMap.put(GameResult.WIN_PLAYER_2, (s) -> s.setPlayer2Wins(s.getPlayer2Wins() + 1));
		strategyMap.put(GameResult.DRAW, (s) -> s.setDraws(s.getDraws() + 1));
		return strategyMap;
	}

	public void updateScore(GameResult gameResult, ScoreEntity scoreEntity) {
		SCORE_UPDATE_STRATEGY.get(gameResult).accept(scoreEntity);
	}
}
