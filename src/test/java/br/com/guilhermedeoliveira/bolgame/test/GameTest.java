package br.com.guilhermedeoliveira.bolgame.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.guilhermedeoliveira.bolgame.exception.BusinessException;
import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import br.com.guilhermedeoliveira.bolgame.model.Game;
import br.com.guilhermedeoliveira.bolgame.model.Pit;
import br.com.guilhermedeoliveira.bolgame.model.Stone;
import br.com.guilhermedeoliveira.bolgame.service.GameService;
import br.com.guilhermedeoliveira.bolgame.service.PitService;
import br.com.guilhermedeoliveira.bolgame.service.PlayerService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameTest {

	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PitService pitService;
	

	@Test
	public void testGame() throws BusinessException {
		
		assertNotNull(this.playerService.initPlayer(WhichPlayer.PLAYER_ONE, 12, 5));
		assertNotNull(this.playerService.initPlayer(WhichPlayer.PLAYER_TWO, 8, 15));
		
		Game game = gameService.startGame(7, 6, null, null);

		assertNotNull(game);

		assertEquals(game.getPlayer1().getPits().size(), game.getPlayer2().getPits().size());
		assertNotEquals(game.getPlayer1().getWhichPlayer(), game.getPlayer2().getWhichPlayer());

		assertNotNull(this.pitService.getPitByPlayerAndPosition(game.getPlayer1(), 5));
		this.pitService.getPitByPlayerAndPosition(game.getPlayer2(), 3);

		assertEquals(this.pitService.getBigPitByPlayer(game.getPlayer1()),
				this.pitService.getPitByPlayerAndPosition(game.getPlayer1(), 0));

		assertEquals(this.pitService.getBigPitByPlayer(game.getPlayer2()),
				this.pitService.getPitByPlayerAndPosition(game.getPlayer2(), game.getPlayer2().getPits().size() - 1));

		assertEquals(WhichPlayer.PLAYER_ONE,
				this.pitService.selectPit(game.getPlayer1().getPits().get(6), game).getWhichPlayer());
		assertEquals(WhichPlayer.PLAYER_TWO,
				this.pitService.selectPit(game.getPlayer1().getPits().get(5), game).getWhichPlayer());

		Integer totalStonesP1 = 0;
		for (Pit pit : game.getPlayer1().getPits()) {
			for (@SuppressWarnings("unused")
			Stone stone : pit.getStones()) {
				totalStonesP1++;
			}
		}

		assertEquals(totalStonesP1, this.pitService.putStonesInBigPitByPlayer(game.getPlayer1()));

		assertEquals(true, this.pitService.checkIfAllNonBigPitsAreEmptiedByPlayer(game.getPlayer1()));
		assertEquals(false, this.pitService.checkIfAllNonBigPitsAreEmptiedByPlayer(game.getPlayer2()));
		
		assertEquals(true, this.gameService.gameHasEnded(game));
		
		game.getPlayer1().setWins(10);
		game.getPlayer2().setWins(3);
		
		game = this.gameService.startGame(4, 2, game.getPlayer1().getWins(), game.getPlayer2().getWins());
		
		assertEquals(new Integer(10), game.getPlayer1().getWins());
		assertEquals(new Integer(3), game.getPlayer2().getWins());
		
	}
}
