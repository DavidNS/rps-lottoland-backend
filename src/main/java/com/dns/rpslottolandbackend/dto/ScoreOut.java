package com.dns.rpslottolandbackend.dto;

import lombok.Data;

@Data
public class ScoreOut {

	private Integer totalGames = 0;

	private Integer player1Wins = 0;

	private Integer player2Wins = 0;

	private Integer draws = 0;

}
