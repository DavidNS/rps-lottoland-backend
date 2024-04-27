package com.dns.rpslottolandbackend.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.dto.GameResult;
import com.dns.rpslottolandbackend.dto.RockPaperScissors;
import com.dns.rpslottolandbackend.service.GameService;

class GameControllerTest {
	
	private GameController testInstance;
	
	@BeforeEach
	public void createInstance(){
		testInstance = new GameController();
	}
	
	@Test
	void testAllInputOkReturnsNotNull() {
		String id = "1";
		GameOut result = testInstance.newGame(id);
		assertNotNull(result);
	}
	
	@Test
	void testAllInputOkPlayer2IsAlwaysRock() {
		String id = "1";
		int retries = 100;
		RockPaperScissors expectedPlayer2 = RockPaperScissors.ROCK;
		for (int i = 0; i < retries; i++) {
			GameOut result = testInstance.newGame(id);
			assertEquals(expectedPlayer2, result.getPlayer2Result());
		}
	}
	
	
}
