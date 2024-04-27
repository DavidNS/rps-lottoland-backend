package com.dns.rpslottolandbackend.dto;

import java.util.Random;

public enum RockPaperScissors {

	ROCK,
	PAPER,
	SCISSORS;
	
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	public static final RockPaperScissors nextRandomResult() {
		RockPaperScissors[] values = RockPaperScissors.values();
		return values[RANDOM.nextInt(0, values.length)];
	}
}
