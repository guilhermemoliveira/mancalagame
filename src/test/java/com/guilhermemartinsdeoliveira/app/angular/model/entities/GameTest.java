package com.guilhermemartinsdeoliveira.app.angular.model.entities;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.guilhermemoliveira.app.angular.model.entities.Game;

@SpringBootTest
public class GameTest {

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreateGameWithPlayersNull() {
		new Game(null);
	}
	
	@Test
	public void shouldPassWhenCreateGameWithEmptyListOfPlayers() {
		new Game(new ArrayList<>());
	}
}
