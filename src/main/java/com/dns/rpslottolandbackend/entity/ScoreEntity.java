package com.dns.rpslottolandbackend.entity;

import lombok.Data;

@Data
public class ScoreEntity {

	private Integer player1Wins = 0;

	private Integer player2Wins = 0;

	private Integer draws = 0;
}
