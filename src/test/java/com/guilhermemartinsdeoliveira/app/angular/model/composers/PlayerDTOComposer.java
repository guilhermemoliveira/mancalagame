package com.guilhermemartinsdeoliveira.app.angular.model.composers;

import java.util.ArrayList;
import java.util.List;

import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PlayerDTO;

public class PlayerDTOComposer {

	public static PlayerDTO getSingleCompletePlayerDTOWithSevenPits() {

		List<PitDTO> pits = new ArrayList<>();

		for (int i = 0; i < 7; i++) {
			pits.add(new PitDTO(i));

		}
		return new PlayerDTO(pits);
	}

	public static String getSingleCompletePlayerDTOJsonWithSevenPits() {
		return "{\"pits\":[{\"stones\":0},{\"stones\":1},{\"stones\":2},"
				+ "{\"stones\":3},{\"stones\":4},{\"stones\":5},{\"stones\":6}]}";
	}
}
