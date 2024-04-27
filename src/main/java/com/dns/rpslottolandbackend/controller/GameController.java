package com.dns.rpslottolandbackend.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dns.rpslottolandbackend.dto.GameOut;
import com.dns.rpslottolandbackend.service.GameService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class GameController {

	private final GameService gameService;

	public GameOut newGame(String id) {
		return gameService.newGame();
	}

}
