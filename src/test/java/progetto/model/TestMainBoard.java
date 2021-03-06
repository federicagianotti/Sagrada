package progetto.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test MainBoard (+data) and RoundInformation (+data)
 */
public class TestMainBoard extends TestCase {

	MainBoard mainBoard;
	RoundInformation roundInformation;

	@Before
	public void setUp()
	{
		mainBoard = new MainBoard();
		roundInformation = new RoundInformation();
	}

	/**
	 * Test - set and get current first player
	 */
	@Test
	public void testSetGetCurrentFirstPlayer()
	{
		roundInformation.setCurrentFirstPlayer(1);

		Assert.assertEquals(1, roundInformation.getData().getCurrentFirstPlayer());

		roundInformation.setCurrentFirstPlayer(2);

		Assert.assertEquals(2, roundInformation.getData().getCurrentFirstPlayer());

	}

	/**
	 * Test - set and get current player
	 */
	@Test
	public void testSetGetCurrentPlayer()
	{
		roundInformation.setCurrentPlayer(0);

		Assert.assertEquals(0, roundInformation.getData().getCurrentPlayer());

		roundInformation.setCurrentPlayer(1);

		Assert.assertEquals(1, roundInformation.getData().getCurrentPlayer());

	}

	/**
	 * Test - set and get player count (number of players)
	 */
	@Test
	public void testSetGetPlayerCount()
	{
		mainBoard.setPlayerCount(3);

		Assert.assertEquals(3, mainBoard.getData().getPlayerCount());

		mainBoard.setPlayerCount(2);

		Assert.assertEquals(2, mainBoard.getData().getPlayerCount());

	}

	/**
	 * Test - set and get current round
	 */
	@Test
	public void testSetGetCurrentRound()
	{
		roundInformation.setCurrentRound(2);

		Assert.assertEquals(2, roundInformation.getData().getCurrentRound());

		roundInformation.setCurrentRound(3);

		Assert.assertEquals(3, roundInformation.getData().getCurrentRound());

	}

	/**
	 * Test - Set and get game state
	 */
	@Test
	public void testSetGetGameState()
	{
		mainBoard.setGameState(new PreGameState());

		Assert.assertEquals("Pre model", mainBoard.getData().getGameState().getName());

		mainBoard.setGameState(new StartRoundState());

		Assert.assertEquals("Start round", mainBoard.getData().getGameState().getName());

	}

	/**
	 * Test - set and get Player queue (queue of next players)
	 */
	@Test
	public void testPlayerQueue()
	{
		roundInformation.addPlayerQueue(1);
		roundInformation.addPlayerQueue(2);
		roundInformation.addPlayerQueue(3);
		roundInformation.addPlayerQueue(0);
		roundInformation.addPlayerQueue(0);
		roundInformation.addPlayerQueue(3);
		roundInformation.addPlayerQueue(2);
		roundInformation.addPlayerQueue(1);

		Assert.assertEquals(1, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(2, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(3, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(0, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(0, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(3, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(2, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();
		Assert.assertEquals(1, (int)roundInformation.getData().getNextPlayer());
		roundInformation.removeNextPlayer();

	}

	/**
	 * Test - player queue - fail - empty queue
	 */
	@Test
	public void testPlayerQueueFail()
	{
		roundInformation.addPlayerQueue(0);
		roundInformation.addPlayerQueue(1);
		roundInformation.addPlayerQueue(2);
		roundInformation.addPlayerQueue(2);
		roundInformation.addPlayerQueue(1);
		roundInformation.addPlayerQueue(0);

		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();
		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();
		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();
		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();
		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();
		roundInformation.getData().getNextPlayer();
		roundInformation.removeNextPlayer();

		Assert.assertEquals(-1, (int)roundInformation.getData().getNextPlayer());

	}

	/**
	 * Test - set and get window frame couples
	 */
	@Test
	public void testSetGetWindowFrameCouples()
	{
		List<WindowFrameCouple> windowFrameCouples = WindowFrameCoupleArray.getInstance().getList();

		WindowFrameCouple windowFrameCouple1 = windowFrameCouples.get(1);
		mainBoard.addWindowFrameCouple(windowFrameCouple1);

		WindowFrameCouple windowFrameCouple2 = windowFrameCouples.get(1);
		mainBoard.addWindowFrameCouple(windowFrameCouple2);

		assertEquals(windowFrameCouple1, mainBoard.getData().getWindowFrame(0));
		assertEquals(windowFrameCouple2, mainBoard.getData().getWindowFrame(1));
	}

	/**
	 * Test get and set extracted dices
	 */
	@Test
	public void testGetSetExtractedDices()
	{
		Dice dice1 = new Dice(Value.ONE, GameColor.YELLOW);
		mainBoard.getExtractedDices().addDice(dice1);

		Dice dice2 = new Dice(Value.TWO, GameColor.BLUE);
		mainBoard.getExtractedDices().addDice(dice2);

		Assert.assertEquals(dice1, mainBoard.getExtractedDices().getData().getDice(0));
		Assert.assertEquals(dice2, mainBoard.getExtractedDices().getData().getDice(1));

		Assert.assertEquals(2, mainBoard.getExtractedDices().getData().getNumberOfDices());

		Dice dice3 = new Dice(Value.THREE, GameColor.GREEN);

		mainBoard.getExtractedDices().changeDice(1, dice3);

		assertEquals(dice3, mainBoard.getExtractedDices().getData().getDice(1));

		assertEquals(2, mainBoard.getExtractedDices().getData().getNumberOfDices());

	}

	/**
	 * Test get number of call of tool cards
	 */
	@Test
	public void testNCallToolCards()
	{
		mainBoard.addToolCard(new ToolCard("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1", GameColor.PURPLE ,1));
		Assert.assertEquals(0, (int)mainBoard.getData().getNCallToolCard(0));

		mainBoard.incNCallToolCard(0);
		Assert.assertEquals(1, (int)mainBoard.getData().getNCallToolCard(0));
	}

	/**
	 * Test tool cards in mainBoard
	 */
	@Test
	public void testToolCards()
	{
		mainBoard.addToolCard(new ToolCard("", "", GameColor.YELLOW, 1));
		Assert.assertEquals(1, mainBoard.getData().getToolCards().size());

		mainBoard.removeToolCard(0);
		Assert.assertEquals(0, mainBoard.getData().getToolCards().size());

	}

	/**
	 * Test - get number of call of tool cards - wrong index
	 */
	@Test
	public void testNCallToolCardFail()
	{
		mainBoard.addToolCard(new ToolCard("", "", GameColor.YELLOW, 1));

		mainBoard.incNCallToolCard(-1);

		Assert.assertEquals(0, (int)mainBoard.getData().getNCallToolCard(0));

		Assert.assertEquals(0, (int)mainBoard.getData().getNCallToolCard(-1));

	}

	/**
	 * Test - get public objective card ID (from main board)
	 */
	@Test
	public void testCardID()
	{
		mainBoard.addPublicObjectiveCards(new ColoredDiagonalsPublicObjectiveCard());
		mainBoard.addPublicObjectiveCards(new ColumnsDifferentColorsPublicObjectiveCard());

		Assert.assertEquals(8, mainBoard.getData().getPublicObjectiveCards().get(0).getCardID());
		Assert.assertEquals(1, mainBoard.getData().getPublicObjectiveCards().get(1).getCardID());

	}

}
