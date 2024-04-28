package com.dns.rpslottolandbackend.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.entity.ScoreEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlayerScoreRepository {

	private final ScoreUpdateStrategy scoreUpdateStrategy;

	private final ConcurrentHashMap<String, ScoreEntity> userIdVsScore = new ConcurrentHashMap<>();

	public ScoreEntity updatePlayerScore(String userId, GameResult gameResult) {
		return userIdVsScore.compute(userId, (k, v) -> {
			ScoreEntity scoreEntity = new ScoreEntity();
			if (v != null) {
				scoreEntity = v;
			}
			scoreUpdateStrategy.updateScore(gameResult, scoreEntity);
			return scoreEntity;
		});
	}

	public ScoreEntity getPlayerScore(String userId) {
		return userIdVsScore.getOrDefault(userId, new ScoreEntity());
	}

	public ScoreEntity resetPlayerScore(String userId) {
		ScoreEntity resetScoreEntity = new ScoreEntity();
		userIdVsScore.put(userId, resetScoreEntity);
		return resetScoreEntity;
	}

}
