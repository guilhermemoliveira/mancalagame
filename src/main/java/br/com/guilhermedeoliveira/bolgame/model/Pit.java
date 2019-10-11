package br.com.guilhermedeoliveira.bolgame.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"player"})
public class Pit {
	
	private List<Stone> stones;
	private Integer position;
	private boolean isBigPit;
	private Player player;
	
	public Pit() {
		
	}
	
	public Pit(Integer position) {
		this.stones = new ArrayList<>();
		this.position = position;
	}
	
	public void addStone() {
		Stone stone = new Stone();
		this.stones.add(stone);
	}
	
}
