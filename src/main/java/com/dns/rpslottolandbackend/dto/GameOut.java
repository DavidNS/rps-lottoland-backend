package com.dns.rpslottolandbackend.dto;

import lombok.Data;

@Data
public class GameOut {

	private GameResult gameResult;
	
	private RockPaperScissors player1Result;
	
	private RockPaperScissors player2Result = RockPaperScissors.ROCK;
}
