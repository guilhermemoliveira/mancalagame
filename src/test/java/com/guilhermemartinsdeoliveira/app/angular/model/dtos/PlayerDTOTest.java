package com.guilhermemartinsdeoliveira.app.angular.model.dtos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.guilhermemartinsdeoliveira.app.angular.model.composers.PlayerDTOComposer;
import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PlayerDTO;

@SpringBootTest
public class PlayerDTOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDTOTest.class);

	@Test
	public void testSerialization() {
		PlayerDTO playerDTO = PlayerDTOComposer.getSingleCompletePlayerDTOWithSevenPits();
		String serializedPlayerDTO = new Gson().toJson(playerDTO);
		
		String playerDTOJson = "{\"pits\":[{\"stones\":0},{\"stones\":1},{\"stones\":2},{\"stones\":3},{\"stones\":4},{\"stones\":5},{\"stones\":6}]}";
		
		assertEquals(serializedPlayerDTO, playerDTOJson);
		
		LOGGER.debug(serializedPlayerDTO);
		
	}
	
	@Test
	public void testDeserialization() {
		
		String playerDTOJson = PlayerDTOComposer.getSingleCompletePlayerDTOJsonWithSevenPits();
		PlayerDTO deserializedPlayerDTO = new Gson().fromJson(playerDTOJson, PlayerDTO.class);
		
		
		List<PitDTO> pits = new ArrayList<>();
		pits.add(new PitDTO(0));
		pits.add(new PitDTO(1));
		pits.add(new PitDTO(2));
		pits.add(new PitDTO(3));
		pits.add(new PitDTO(4));
		pits.add(new PitDTO(5));
		pits.add(new PitDTO(6));
		
		PlayerDTO playerDTO = new PlayerDTO(pits);
		
		assertEquals(PlayerDTO.class, deserializedPlayerDTO.getClass());
		assertEquals(deserializedPlayerDTO, playerDTO);
		
		LOGGER.debug(deserializedPlayerDTO.toString());
	}
}
