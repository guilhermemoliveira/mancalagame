package br.com.guilhermedeoliveira.bolgame.model;

import java.util.ArrayList;
import java.util.List;

import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import lombok.Data;

@Data
public class Player {
	
	private List<Pit> pits;
	private Integer wins;
	private WhichPlayer whichPlayer;
	
	public Player() {
		this.setPits(new ArrayList<>());
		this.setWins(0);
	}
}
