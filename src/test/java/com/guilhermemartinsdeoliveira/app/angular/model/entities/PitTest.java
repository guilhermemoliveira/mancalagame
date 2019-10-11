package com.guilhermemartinsdeoliveira.app.angular.model.entities;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.guilhermemoliveira.app.angular.model.entities.Pit;

@SpringBootTest
public class PitTest {

	@Test(expected = NullPointerException.class)
	public void shouldFailWhenCreatePitWithStonesNull() {
		new Pit(null);
	}
	
	@Test
	public void shouldPassWhenCreatePitWithStones() {
		new Pit(23);
	}
}
