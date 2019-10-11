package com.guilhermemartinsdeoliveira.app.angular.model.entities;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.guilhermemoliveira.app.angular.model.entities.Player;

@SpringBootTest
public class PlayerTest {

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreatePlayerWithPitsNull() {
		new Player(null);
	}
	
	@Test
	public void shouldPassWhenCreatePlayerWithEmptyListOfPits() {
		new Player(new ArrayList<>());
	}
}
