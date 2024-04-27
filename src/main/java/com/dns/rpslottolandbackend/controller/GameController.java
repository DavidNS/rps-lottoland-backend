package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.GameOut;

@RestController
public class GameController {

	public GameOut newGame(String id) {
		return new GameOut();
	}

}
