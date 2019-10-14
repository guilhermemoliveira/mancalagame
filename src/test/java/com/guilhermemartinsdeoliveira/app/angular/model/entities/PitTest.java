package com.guilhermemartinsdeoliveira.app.angular.model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.guilhermemoliveira.app.angular.model.entities.Pit;

@SpringBootTest
public class PitTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PitTest.class);

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreatePitWithStonesNull() {
		new Pit(null);
	}
	
	@Test
	public void shouldSuccessWhenCreatePitWithZeroStones() {
		Pit pit1 = new Pit(0);
		assertEquals(Integer.valueOf(0), pit1.getStones());
		
		LOGGER.debug(pit1.toString());
	}
	
	@Test
	public void shouldSuccessWhenCreatePitWithStones() {
		Pit pit1 = new Pit(0);
		Pit pit2 = new Pit(7);
		assertNotEquals(pit1, pit2);
		assertEquals(Integer.valueOf(7), pit2.getStones());
		LOGGER.debug(pit2.toString());
	}
}
