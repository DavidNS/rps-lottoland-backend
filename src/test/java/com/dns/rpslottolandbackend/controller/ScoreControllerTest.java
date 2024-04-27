package com.dns.rpslottolandbackend.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dns.rpslottolandbackend.dto.ScoreOut;

class ScoreControllerTest {

	ScoreController testInstance;

	@BeforeEach
	public void createInstance() {
		testInstance = new ScoreController();
	}

	@Test
	void testAllInputOkReturnsNotNull() {
		String id = "1";
		ScoreOut result = testInstance.getScore(id);
		assertNotNull(result);
	}

}
