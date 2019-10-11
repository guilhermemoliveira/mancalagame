package br.com.guilhermedeoliveira.bolgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.guilhermedeoliveira.bolgame.exception.BusinessException;
import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import br.com.guilhermedeoliveira.bolgame.model.Game;
import br.com.guilhermedeoliveira.bolgame.model.Player;

@Service
public class GameService {

	@Autowired
	private PitService pitService;

	@Autowired
	private PlayerService playerService;

	/**
	 * Starts the game.
	 * 
	 * @param quantityOfPits
	 * 	Quantity of pits to be created. Must include the big pit.
	 * 
	 * @param quantityOfStones
	 *  Quantity of stones to be sowed in each pit
	 *  
	 * @param winsP1
	 * 	Quantity of wins from player 1, if exists. Used to preserve the information after several consecutive games.
	 * 
	 *  @param winsP2
	 * 	Quantity of wins from player 2, if exists. Used to preserve the information after several consecutive games.
	 * 
	 * @return
	 * 	The game ready to be played.
	 */
	public Game startGame(Integer quantityOfPits, Integer quantityOfStones, Integer winsP1, Integer winsP2) throws BusinessException {

		Game game = new Game();

		Player player1 = this.playerService.initPlayer(WhichPlayer.PLAYER_ONE, quantityOfPits, quantityOfStones);
		Player player2 = this.playerService.initPlayer(WhichPlayer.PLAYER_TWO, quantityOfPits, quantityOfStones);
		
		if (winsP1 != null) {
			player1.setWins(winsP1);
		}
		
		if (winsP2 != null) {
			player2.setWins(winsP2);
		}

		game.setPlayer1(player1);
		game.setPlayer2(player2);

		return game;
	}
	
	/**
	 * Checks if the game has ended, by checking each player's pits.
	 * If there are no small pit with stones from one of players, then the game must end.
	 * 
	 * @param game
	 *  The game to be checked.
	 *  
	 * @return
	 *  true if it is ended, false if isn't.
	 */
	public boolean gameHasEnded(Game game) throws BusinessException {

		if (this.pitService.checkIfAllNonBigPitsAreEmptiedByPlayer(game.getPlayer1())
				|| this.pitService.checkIfAllNonBigPitsAreEmptiedByPlayer(game.getPlayer2())) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks who have won the game by comparing the players' stones sum.
	 * 
	 * @param game
	 *  The game to be checked.
	 *  
	 * @param player1Stones
	 * 	Player 1 total quantity of stones.
	 * 
	 * @param player2Stones
	 * 	Player 2 total quantity of stones.
	 *  
	 * @return
	 *  Returns the player with more stones in his pits, or return null if it is a tie.
	 */
	public Player whoWon(Game game, Integer player1Stones, Integer player2Stones) throws BusinessException {
		
		if (game == null || player1Stones == null || player2Stones == null) {
			throw new BusinessException("Parameters cannot be null.");
		}
		
		if (player1Stones > player2Stones) {
			game.getPlayer1().setWins(game.getPlayer1().getWins() + 1);
			return game.getPlayer1();
		} else if (player1Stones < player2Stones) {
			game.getPlayer2().setWins(game.getPlayer2().getWins() + 1);
			return game.getPlayer2();
		} else {
			return null;
		}
	}

}
