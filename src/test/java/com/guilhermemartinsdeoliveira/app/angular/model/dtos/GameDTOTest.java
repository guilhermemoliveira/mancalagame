package com.guilhermemartinsdeoliveira.app.angular.model.dtos;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.guilhermemartinsdeoliveira.app.angular.model.composers.GameDTOComposer;
import com.guilhermemoliveira.app.angular.model.dtos.GameDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PlayerDTO;

@SpringBootTest
public class GameDTOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameDTOTest.class);

	@Test
	public void testSerialization() {
		GameDTO gameDTO = GameDTOComposer.getCompleteGameWithTwoPlayersGameDTO();
		String serializedGameDTO = new Gson().toJson(gameDTO);
		String gameDTOJson = "{\"players\":[{\"pits\":[{\"stones\":1},{\"stones\":2}]},{\"pits\":[{\"stones\":3},{\"stones\":4}]}]}";

		assertEquals(serializedGameDTO, gameDTOJson);

		LOGGER.debug(serializedGameDTO);
	}
	
	@Test
	public void testDeserialization() {
		String gameDTOJson = GameDTOComposer.getCompleteGameWithTwoPlayersGameDTOJson();
		GameDTO deserializedGameDTO = new Gson().fromJson(gameDTOJson, GameDTO.class);
		
		PitDTO pitDTO1 = new PitDTO(1);
		PitDTO pitDTO2 = new PitDTO(2);
		
		List<PitDTO> pits1 = Stream.of(pitDTO1, pitDTO2).collect(Collectors.toList());
		
		PlayerDTO playerDTO1 = new PlayerDTO(pits1);
		
		PitDTO pitDTO3 = new PitDTO(3);
		PitDTO pitDTO4 = new PitDTO(4);
		
		List<PitDTO> pits2 = Stream.of(pitDTO3, pitDTO4).collect(Collectors.toList());
		
		PlayerDTO playerDTO2 = new PlayerDTO(pits2);
		
		List<PlayerDTO> players = Stream.of(playerDTO1, playerDTO2).collect(Collectors.toList());
		
		GameDTO gameDTO = new GameDTO(players);
		
		assertEquals(GameDTO.class, deserializedGameDTO.getClass());
		assertEquals(deserializedGameDTO, gameDTO);

		LOGGER.debug(deserializedGameDTO.toString());
	}
}
