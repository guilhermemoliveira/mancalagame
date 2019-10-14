package com.guilhermemartinsdeoliveira.app.angular.model.composers;

import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;

public class PitDTOComposer {
	
	public static String getSingleCompletePitDTOJsonWithThreeStones() {
		return "{\"stones\":3}";
	}
	
	public static PitDTO getSingleCompletePitDTOWithThreeStones() {
		return new PitDTO(3);
	}
}
