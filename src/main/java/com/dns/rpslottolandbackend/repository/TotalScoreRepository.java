package com.dns.rpslottolandbackend.repository;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.entity.ScoreEntity;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TotalScoreRepository {

	private final ScoreUpdateStrategy scoreUpdateStrategy;

	private final AtomicReference<ScoreEntity> totalScoreEntity = intializeTotalScore();

	private static AtomicReference<ScoreEntity> intializeTotalScore() {
		AtomicReference<ScoreEntity> totalScoreEntity = new AtomicReference<>();
		totalScoreEntity.set(new ScoreEntity());
		return totalScoreEntity;
	}

	public ScoreEntity updateTotalScore(GameResult gameResult) {
		return totalScoreEntity.updateAndGet((s) -> {
			scoreUpdateStrategy.updateScore(gameResult, s);
			return s;
		});
	}

	public ScoreEntity getTotalScore() {
		return totalScoreEntity.get();
	}

}
