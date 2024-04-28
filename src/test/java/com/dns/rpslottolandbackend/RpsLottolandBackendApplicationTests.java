package com.dns.rpslottolandbackend;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RpsLottolandBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Description("GET /v1/games/players returns success and value")
	void getGamesPlayersAllInputOk() throws Exception {
		this.mockMvc.perform(get("/v1/games/players").header("x-device-id", "1")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("gameResult")))
				.andExpect(content().string(containsString("totalGames")));
	}
	
	@Test
	@Description("GET /v1/games/players controlled exception. Missing required header")
	void getGamesPlayersAllNoDeviceId() throws Exception {
		this.mockMvc.perform(get("/v1/games/players")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("Required request header")));
	}
	
	@Test
	@Description("GET /v1/games/players controlled exception. Validation exception")
	void getGamesPlayersAllDeviceIdEmpty() throws Exception {
		this.mockMvc.perform(get("/v1/games/players").header("x-device-id", "")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("x-device-id headder cannot be empty or null")));
	}
	
	@Test
	@Description("GET /v1/scores/players returns success and value")
	void getScoresPlayersAllInputOk() throws Exception {
		this.mockMvc.perform(get("/v1/scores/players").header("x-device-id", "1")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("totalGames")));
	}
	
	@Test
	@Description("GET /v1/scores/players controlled exception. Missing required header")
	void getScoresPlayersAllNoDeviceId() throws Exception {
		this.mockMvc.perform(get("/v1/scores/players")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("Required request header")));
	}
	
	@Test
	@Description("GET /v1/scores/players controlled exception. Validation exception")
	void getScoresPlayersAllDeviceIdEmpty() throws Exception {
		this.mockMvc.perform(get("/v1/scores/players").header("x-device-id", "")).andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("x-device-id headder cannot be empty or null")));
	}
	
	@Test
	@Description("GET /v1/scores/totals returns success and value")
	void getScoresTotalsAllInputOk() throws Exception {
		this.mockMvc.perform(get("/v1/scores/totals")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string(containsString("totalGames")));
	}

}
