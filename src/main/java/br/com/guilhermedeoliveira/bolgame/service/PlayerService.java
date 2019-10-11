package br.com.guilhermedeoliveira.bolgame.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.guilhermedeoliveira.bolgame.exception.BusinessException;
import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import br.com.guilhermedeoliveira.bolgame.model.Pit;
import br.com.guilhermedeoliveira.bolgame.model.Player;

@Service
public class PlayerService {
	
	/**
	 * Creates a player with its pits and stones.
	 * 
	 * @param whichPlayer
	 *  Enumerated of what player will be created, 1 or 2.
	 *  
	 * @param quantityOfPits
	 * 	Quantity of pits for this player.
	 * 
	 * @param quantityOfStones
	 * 	Quantity of stones per pit for this player.
	 *  
	 * @return
	 *  Returns the player created.
	 */
	public Player initPlayer(WhichPlayer whichPlayer, Integer quantityOfPits, Integer quantityOfStones)
			throws BusinessException {

		if (whichPlayer == null || (quantityOfPits == null || quantityOfStones == null)) {
			throw new BusinessException("All parameters must be informed!");
		}

		if (quantityOfPits <= 2 || quantityOfStones <= 0) {
			throw new BusinessException("Pits must be greater than 2 and stones must be greater than 0!");
		}

		Player player = new Player();
		player.setWhichPlayer(whichPlayer);

		for (int i = 0; i < quantityOfPits; i++) {
			Pit pit = new Pit(i);
			pit.setPlayer(player);
			for (int j = 0; j < quantityOfStones; j++) {
				pit.addStone();
			}
			player.getPits().add(pit);
		}
		
		// If the player is player 1, the first pit must be a big pit, if not, the last must be.
		if (whichPlayer.equals(WhichPlayer.PLAYER_ONE)) {
			player.getPits().get(0).setStones(new ArrayList<>());
			player.getPits().get(0).setBigPit(true);
		} else {
			player.getPits().get(quantityOfPits - 1).setStones(new ArrayList<>());
			player.getPits().get(quantityOfPits - 1).setBigPit(true);
		}

		return player;
	}
	
}
