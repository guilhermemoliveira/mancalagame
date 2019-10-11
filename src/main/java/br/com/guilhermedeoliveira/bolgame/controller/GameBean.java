package br.com.guilhermedeoliveira.bolgame.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.guilhermedeoliveira.bolgame.exception.BusinessException;
import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import br.com.guilhermedeoliveira.bolgame.model.Game;
import br.com.guilhermedeoliveira.bolgame.model.Pit;
import br.com.guilhermedeoliveira.bolgame.model.Player;
import br.com.guilhermedeoliveira.bolgame.model.Turn;
import br.com.guilhermedeoliveira.bolgame.service.GameService;
import br.com.guilhermedeoliveira.bolgame.service.PitService;
import br.com.guilhermedeoliveira.bolgame.service.PlayerService;
import br.com.guilhermedeoliveira.bolgame.util.Utilities;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@ViewScoped
@Named
@Setter
@Getter
public class GameBean implements Serializable {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static Logger logger = LoggerFactory.getLogger(GameBean.class);

	private Game game;
	private Turn nextTurn;
	private Player winner;
	private boolean gameHasEnded;

	@Autowired
	private GameService gameService;

	@Autowired
	private PitService pitService;

	@Autowired
	private PlayerService playerService;

	@PostConstruct
	public void init() {
		try {
			this.game = this.gameService.startGame(7, 6, null, null);
			this.resetProperties();
		} catch (BusinessException e) {
			Utilities.showMessage(FacesMessage.SEVERITY_ERROR, "Attention", e.getMessage());
			logger.error(e.getMessage(), e.getCause());
		}

	}
	
	/**
	 * Restarts the game without losing the players's win history.
	 */
	public void restartGame() throws BusinessException {

		try {
			this.game = this.gameService.startGame(7, 6, this.game.getPlayer1().getWins(),
					this.game.getPlayer2().getWins());
			this.resetProperties();
		} catch (BusinessException e) {
			Utilities.showMessage(FacesMessage.SEVERITY_ERROR, "Attention", e.getMessage());
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	public void resetProperties() {
		this.nextTurn = new Turn();
		this.nextTurn.setWhichPlayer(WhichPlayer.PLAYER_ONE);
		this.nextTurn.setMessage("Player 1's turn");
		this.winner = null;
		this.gameHasEnded = false;
	}
	
	/**
	 * Picks the pit selected and processes its stones according to the game rules. 
	 */
	public void selectPit(Pit pitSelected) {

		// These parameters are needed to check if this method is being invoked by an user action
		// or by the render response phase from the JSF lifecycle
		String pitPositionParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("pitPositionParam");
		String playerParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("playerParam");

		if (StringUtils.isNotEmpty(pitPositionParam) && StringUtils.isNotEmpty(playerParam)
				&& Integer.parseInt(pitPositionParam) == pitSelected.getPosition()
				&& playerParam.equals(pitSelected.getPlayer().getWhichPlayer().toString())) {

			try {
				this.nextTurn = this.pitService.selectPit(pitSelected, this.game);
				this.checkIfGameHasEnded();
			} catch (BusinessException e) {
				Utilities.showMessage(FacesMessage.SEVERITY_ERROR, "Attention", e.getMessage());
				logger.error(e.getMessage(), e.getCause());
			}
		}
	}

	/**
	 * This method is invoked everytime a pit is selected, to check if the game was over.
	 * If it ended, then a pop-up will show the winner.
	 */
	private void checkIfGameHasEnded() {

		try {
			this.gameHasEnded = this.gameService.gameHasEnded(this.game);
			if (this.gameHasEnded) {
				this.showWinner();
			}
		} catch (BusinessException e) {
			Utilities.showMessage(FacesMessage.SEVERITY_ERROR, "Attention", e.getMessage());
			logger.error(e.getMessage(), e.getCause());
		}
	}
	
	/**
	 * Shows the winner in a pop-up.
	 */
	private void showWinner() throws BusinessException {

		Integer player1Stones = this.pitService.putStonesInBigPitByPlayer(this.game.getPlayer1());
		Integer player2Stones = this.pitService.putStonesInBigPitByPlayer(this.game.getPlayer2());

		this.winner = this.gameService.whoWon(this.game, player1Stones, player2Stones);

		if (this.winner != null) {
			Utilities.showMessage(FacesMessage.SEVERITY_INFO, "Done!",
					"Congratulations! <br /><br />" + this.winner.getWhichPlayer() + " won!"
							+ "<br /><br /><br />Player 1's stones: " + player1Stones + "<br />Player 2's stones: "
							+ player2Stones);
		} else {
			Utilities.showMessage(FacesMessage.SEVERITY_INFO, "Done!",
					"It was a tie! " + "<br /><br /><br />Player 1's stones: " + player1Stones
							+ "<br />Player 2's stones: " + player2Stones);
		}

		this.nextTurn.setMessage("Restart the game.");
	}

}
