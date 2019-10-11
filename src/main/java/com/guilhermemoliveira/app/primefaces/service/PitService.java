package com.guilhermemoliveira.app.primefaces.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.guilhermemoliveira.app.primefaces.exception.BusinessException;
import com.guilhermemoliveira.app.primefaces.model.Game;
import com.guilhermemoliveira.app.primefaces.model.Pit;
import com.guilhermemoliveira.app.primefaces.model.Player;
import com.guilhermemoliveira.app.primefaces.model.Stone;
import com.guilhermemoliveira.app.primefaces.model.Turn;
import com.guilhermemoliveira.app.primefaces.model.Enum.WhichPlayer;

@Service
public class PitService {

	/**
	 * Gets the pit of a player, given a position.
	 * 
	 * @param player
	 *  The player to get the pit.
	 *  
	 * @param positionToBeSearched
	 * 	The position to get the pit, starts with 0.
	 * 
	 * @return
	 *  Returns the pit found.
	 */
	public Pit getPitByPlayerAndPosition(Player player, Integer positionToBeSearched) throws BusinessException {

		if (player == null || positionToBeSearched == null) {
			throw new BusinessException("Parameters cannot be null!");
		}

		Pit pit = player.getPits().stream().filter(p -> p.getPosition() == positionToBeSearched).findFirst()
				.orElse(null);

		if (pit == null) {
			throw new BusinessException("Pit not found in specified position!");
		}

		return pit;
	}
	
	/**
	 * Gets the big pit of a player.
	 * 
	 * @param player
	 *  The player to get the pit.
	 *  
	 * @return
	 *  Returns the pit found.
	 */
	public Pit getBigPitByPlayer(Player player) throws BusinessException {

		if (player == null) {
			throw new BusinessException("Player cannot be null!");
		}

		Pit pit = player.getPits().stream().filter(p -> p.isBigPit()).findFirst().orElse(null);

		if (pit == null) {
			throw new BusinessException("Big pit not found for " + player.getWhichPlayer() + "!");
		}

		return pit;
	}

	/**
	 * Main method of the game. Uses the pit selected to sow the stones in it to other pits.
	 * 
	 * @param pitSelected
	 *  The pit to be processed.
	 *  
	 * @param game
	 * 	The game in which the pit is.
	 * 
	 * @return
	 *  Returns a Turn that contains who should play next and a message.
	 */
	public Turn selectPit(Pit pitSelected, Game game) throws BusinessException {
		
		if (pitSelected == null || game == null) {
			throw new BusinessException("Could not select pit. Pit or game cannot be null.");
		}

		List<Stone> stonesToSow = new ArrayList<>(pitSelected.getStones());
		pitSelected.setStones(new ArrayList<>());

		ListIterator<Pit> it = null;

		Turn nextTurn = new Turn();
		
		// If it is player one, then it is going to read his pits on backwards
		if (pitSelected.getPlayer().getWhichPlayer().equals(WhichPlayer.PLAYER_ONE)) {

			nextTurn.setWhichPlayer(WhichPlayer.PLAYER_TWO);
			nextTurn.setMessage("Player 2's turn");

			it = pitSelected.getPlayer().getPits().listIterator(pitSelected.getPosition());
			
			while (it.hasPrevious() && CollectionUtils.isNotEmpty(stonesToSow)) {
				Pit nextPit = it.previous();

				nextPit.addStone();
				stonesToSow.remove(0);

				if (CollectionUtils.isEmpty(stonesToSow)) {
					// If the last stone was sowed in a big pit, then the player will play again
					if (nextPit.isBigPit()) {

						nextTurn.setWhichPlayer(WhichPlayer.PLAYER_ONE);
						nextTurn.setMessage("The last stone was sowed in player 1's big pit, so he gets another turn!");
						
					// If the last stone was sowed in a small pit that was empty,
					// then the player will capture his own stone and the opposite pit's stones and put them into his own big pit.
					} else if (nextPit.getStones().size() == 1) {

						nextTurn.setWhichPlayer(WhichPlayer.PLAYER_TWO);
						nextTurn.setMessage("Player 1 got all of player 2's [" + (nextPit.getPosition())
								+ "]º pit stones! It's player 2's turn now.");

						this.getPlayerStones(game.getPlayer2(), nextPit, game.getPlayer1());

					}
				}

				if (!it.hasPrevious()) {
					it = pitSelected.getPlayer().getPits().listIterator(pitSelected.getPlayer().getPits().size());
				}
			}
		} else {

			nextTurn.setWhichPlayer(WhichPlayer.PLAYER_ONE);
			nextTurn.setMessage("Player 1's turn");

			it = pitSelected.getPlayer().getPits().listIterator(pitSelected.getPosition() + 1);

			while (it.hasNext() && CollectionUtils.isNotEmpty(stonesToSow)) {
				Pit nextPit = it.next();

				nextPit.addStone();
				stonesToSow.remove(0);

				if (CollectionUtils.isEmpty(stonesToSow)) {
					// If the last stone was sowed in a big pit, then the player will play again
					if (nextPit.isBigPit()) {
						nextTurn.setWhichPlayer(WhichPlayer.PLAYER_TWO);
						nextTurn.setMessage("The last stone was sowed in player 2's big pit, so he gets another turn!");
						
					// If the last stone was sowed in a small pit that was empty,
					// then the player will capture his own stone and the opposite pit's stones and put them into his own big pit.
					} else if (nextPit.getStones().size() == 1) {
						nextTurn.setWhichPlayer(WhichPlayer.PLAYER_ONE);
						nextTurn.setMessage("Player 2 got all of player 1's [" + (nextPit.getPosition() + 1)
								+ "] pit stones! It's player 1's turn now.");

						this.getPlayerStones(game.getPlayer1(), nextPit, game.getPlayer2());

					}
				}

				if (!it.hasNext()) {
					it = pitSelected.getPlayer().getPits().listIterator();
				}
			}
		}

		return nextTurn;
	}
	
	/**
	 * Gets all of a player pit's stones and sows them to the opposite pit.
	 * 
	 * @param playerToLoseTheStones
	 *  The player that will lose the stones.
	 *  
	 * @param referencePitToGetTheStones
	 *  The pit that caused the action. Will be used to know what opposite pit should have its stones grabbed.
	 *  
	 * @param playerToGetTheStones
	 *  The player which will get the stones to himself.
	 *  
	 */
	private void getPlayerStones(Player playerToLoseTheStones, Pit referencePitToGetTheStones, Player playerToGetTheStones)
			throws BusinessException {
		
		if (playerToLoseTheStones == null || referencePitToGetTheStones == null || playerToGetTheStones == null) {
			throw new BusinessException("Parameters cannot be null!");
		}
		
		Integer position;
		
		// If it is player one, then his small pits go from 1 to 6,
		// if it is player two, then his small pits go from 0 to 5,
		// so to retrieve the correct opposite pit, it must add or subtract a position index.
		if (playerToGetTheStones.getWhichPlayer().equals(WhichPlayer.PLAYER_ONE)) {
			position = referencePitToGetTheStones.getPosition() - 1;
		} else {
			position = referencePitToGetTheStones.getPosition() + 1;
		}
		
		Pit pitLosingStones = this.getPitByPlayerAndPosition(playerToLoseTheStones, position);
		Pit bigPitEarningStones = this.getBigPitByPlayer(playerToGetTheStones);

		referencePitToGetTheStones.setStones(new ArrayList<>());

		bigPitEarningStones.getStones().addAll(pitLosingStones.getStones());
		bigPitEarningStones.addStone();

		pitLosingStones.setStones(new ArrayList<>());
	}
	
	/**
	 * Checks a player's small pits if all of them are empty.
	 * 
	 * @param player
	 *  The player to be checked.
	 *  
	 * @return true if all small pits are empty, false if not.
	 *  
	 */
	public boolean checkIfAllNonBigPitsAreEmptiedByPlayer(Player player) throws BusinessException {
		if (player == null || player.getPits() == null) {
			throw new BusinessException("Player and his pits cannot be null!");
		}
		
		Pit pitStillWithStones = player.getPits()
				.stream()
				.filter(p -> !p.isBigPit() && CollectionUtils.isNotEmpty(p.getStones()))
				.findFirst()
				.orElse(null);
		
		return pitStillWithStones == null;
	}
	
	/**
	 * Picks all of a player's stones and puts them into his own big pit.
	 * 
	 * @param player
	 *  The player to be processed.
	 *  
	 * @return The quantity of stones inside the big pit.
	 */ 
	public Integer putStonesInBigPitByPlayer(Player player) throws BusinessException {
		if (player == null || player.getPits() == null) {
			throw new BusinessException("Player and his pits cannot be null!");
		}
		
		List<Stone> stonesToBigPit = new ArrayList<>();
		
		for (Pit pit : player.getPits()) {
			if (CollectionUtils.isNotEmpty(pit.getStones()) && !pit.isBigPit()) {
				stonesToBigPit.addAll(pit.getStones());
				pit.setStones(new ArrayList<>());
			}
		}
		
		Pit bigPit = new PitService().getBigPitByPlayer(player);
		stonesToBigPit.addAll(bigPit.getStones());
		bigPit.setStones(stonesToBigPit);
		
		return new Integer(stonesToBigPit.size());
	}
	
}
