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

import com.guilhermemoliveira.app.angular.model.entities.Game;
import com.guilhermemoliveira.app.angular.model.entities.Player;

@SpringBootTest
public class GameTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameTest.class);

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreateGameWithPlayersNull() {
		new Game(null);
	}
	
	@Test
	public void shouldSuccessWhenCreateGameWithEmptyListOfPlayers() {
		Game game1 = new Game(new ArrayList<>());
		assertThat(game1.getPlayers(), hasSize(0));
		LOGGER.debug(game1.toString());
	}
	
	@Test
	public void shouldSuccessWhenCreateGameWithPlayers() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(new ArrayList<>()));
		players.add(new Player(new ArrayList<>()));
		
		Game game1 = new Game(players);
		Game game2 = new Game(new ArrayList<>());
		
		assertThat(game1.getPlayers(), hasSize(2));
		assertNotEquals(game2, game1);
		
		LOGGER.debug(game1.toString());
	}
}
