package com.dns.rpslottolandbackend.service;

import org.springframework.stereotype.Service;

import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.ScoreOut;
import com.dns.rpslottolandbackend.entity.ScoreEntity;
import com.dns.rpslottolandbackend.repository.PlayerScoreRepository;
import com.dns.rpslottolandbackend.repository.TotalScoreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScoreService {

	private final PlayerScoreRepository playerScoreRepository;

	private final TotalScoreRepository totalScoreRepository;

	public ScoreOut getTotalScore() {
		return toScoreOut(totalScoreRepository.getTotalScore());
	}

	public ScoreOut saveNewPlayerScore(String id, GameResult gameResult) {
		ScoreEntity savedScoreEntity = playerScoreRepository.updatePlayerScore(id, gameResult);
		return toScoreOut(savedScoreEntity);
	}

	public ScoreOut saveNewTotalScore(GameResult gameResult) {
		ScoreEntity scoreEntity = totalScoreRepository.updateTotalScore(gameResult);
		return toScoreOut(scoreEntity);
	}
	
	public ScoreOut resetPlayerScore(String id) {
		ScoreEntity scoreEntity = playerScoreRepository.resetPlayerScore(id);
		return toScoreOut(scoreEntity);
	}

	private ScoreOut toScoreOut(ScoreEntity scoreEntity) {
		ScoreOut scoreOut = new ScoreOut();
		int player1Wins = scoreEntity.getPlayer1Wins();
		int player2Wins = scoreEntity.getPlayer2Wins();
		int draws = scoreEntity.getDraws();
		int total = player1Wins + player2Wins + draws;
		scoreOut.setPlayer1Wins(player1Wins);
		scoreOut.setPlayer2Wins(player2Wins);
		scoreOut.setDraws(draws);
		scoreOut.setTotalGames(total);
		return scoreOut;
	}




}
