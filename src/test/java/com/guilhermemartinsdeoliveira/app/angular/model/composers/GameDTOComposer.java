package com.guilhermemartinsdeoliveira.app.angular.model.composers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.guilhermemoliveira.app.angular.model.dtos.GameDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PitDTO;
import com.guilhermemoliveira.app.angular.model.dtos.PlayerDTO;

public class GameDTOComposer {
	
	public static String getCompleteGameWithTwoPlayersGameDTOJson() {
		return "{\"players\":[{\"pits\":[{\"stones\":1},{\"stones\":2}]},{\"pits\":[{\"stones\":3},{\"stones\":4}]}]}";
	}
	
	public static GameDTO getCompleteGameWithTwoPlayersGameDTO() {
		PitDTO pitDTO1 = new PitDTO(1);
		PitDTO pitDTO2 = new PitDTO(2);
		
		List<PitDTO> pits1 = Stream.of(pitDTO1, pitDTO2).collect(Collectors.toList());
		
		PlayerDTO playerDTO1 = new PlayerDTO(pits1);
		
		PitDTO pitDTO3 = new PitDTO(3);
		PitDTO pitDTO4 = new PitDTO(4);
		
		List<PitDTO> pits2 = Stream.of(pitDTO3, pitDTO4).collect(Collectors.toList());
		
		PlayerDTO playerDTO2 = new PlayerDTO(pits2);
		
		List<PlayerDTO> players = Stream.of(playerDTO1, playerDTO2).collect(Collectors.toList());
		
		return new GameDTO(players);
	}
}
