package com.guilhermemartinsdeoliveira.app.angular.model.entities;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.guilhermemoliveira.app.angular.model.entities.Pit;
import com.guilhermemoliveira.app.angular.model.entities.Player;

@SpringBootTest
public class PlayerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerTest.class);
	
	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreatePlayerWithPitsNull() {
		new Player(null);
	}

	@Test
	public void shouldSuccessWhenCreatePlayerWithEmptyListOfPits() {
		List<Pit> pits1 = new ArrayList<>();
		Player player1 = new Player(pits1);

		assertThat(player1.getPits(), hasSize(0));
		
		LOGGER.debug(player1.toString());
	}

	@Test
	public void shouldSuccessWhenCreatePlayerWithPits() {
		List<Pit> pits1 = new ArrayList<>();
		Player player1 = new Player(pits1);

		List<Pit> pits2 = new ArrayList<>();
		pits2.add(new Pit(3));
		pits2.add(new Pit(4));
		pits2.add(new Pit(5));
		Player player2 = new Player(pits2);

		assertThat(player2.getPits(), hasSize(3));
		assertNotEquals(player2, player1);
		
		LOGGER.debug(player2.toString());
	}
}
