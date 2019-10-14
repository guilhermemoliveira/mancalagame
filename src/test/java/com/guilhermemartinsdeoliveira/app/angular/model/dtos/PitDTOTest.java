package com.guilhermemartinsdeoliveira.app.angular.model.dtos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;
import com.guilhermemartinsdeoliveira.app.angular.model.composers.PitDTOComposer;
import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;

@SpringBootTest
public class PitDTOTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PitDTOTest.class);

	@Test
	public void testSerialization() {
		PitDTO pitDTO = PitDTOComposer.getSingleCompletePitDTOWithThreeStones();
		String serializedPitDTO = new Gson().toJson(pitDTO);
		String pitDTOJson = "{\"stones\":3}";
		
		assertEquals(serializedPitDTO, pitDTOJson);

		LOGGER.debug(serializedPitDTO);
	}
	
	@Test
	public void testDeserialization() {
		String pitDTOJson = PitDTOComposer.getSingleCompletePitDTOJsonWithThreeStones();
		PitDTO deserializedPitDTO = new Gson().fromJson(pitDTOJson, PitDTO.class);
		
		PitDTO pitDTO = new PitDTO(3);
		
		assertEquals(PitDTO.class, deserializedPitDTO.getClass());
		assertEquals(deserializedPitDTO, pitDTO);
		
		LOGGER.debug(deserializedPitDTO.toString());
	}
}
