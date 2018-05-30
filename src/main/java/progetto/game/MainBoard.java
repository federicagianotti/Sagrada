package progetto.game;

import java.util.ArrayList;

/**
 * Main gaming table
 */
public final class MainBoard extends DataContainer<MainBoardData> implements IMainBoard
{
	MainBoard()
	{
		super(new MainBoardData());
	}

	private final ExtractedDices extractedDices = new ExtractedDices();

	private ArrayList<Integer> playerQueue = new ArrayList<>();


	/**
	 * Get extracted dices
	 * @return extractedDices
	 */
	public ExtractedDices getExtractedDices()
	{
		return extractedDices;
	}

	/**
	 * Add window frame couple
	 * @param windowFrameCouple to add
	 */
	void addWindowFrameCouple(WindowFrameCouple windowFrameCouple)
	{
		setData(getData().addWindowFrameCouple(windowFrameCouple));
	}

	/**
	 * Set number of player
	 * @param playerCount the new player count
	 */
	void setPlayerCount(int playerCount)
	{
		setData(getData().setPlayerCount(playerCount));
	}

	/**
	 * Set gameState
	 * @param state the new state of the game
	 */
	void setGameState(AbstractGameState state)
	{
		setData(getData().setGameState(state));
	}

	/**
	 * Set current first player
	 * @param currentFirstPlayer new current first player
	 */
	void setCurrentFirstPlayer(int currentFirstPlayer)
	{
		setData(getData().setCurrentFirstPlayer(currentFirstPlayer));
	}

	/**
	 * Set current player
	 * @param currentPlayer new current player
	 */
	void setCurrentPlayer(int currentPlayer)
	{
		setData(getData().setCurrentPlayer(currentPlayer));
	}

	/**
	 * Set current round
	 * @param currentRound new current round
	 */
	void setCurrentRound(int currentRound)
	{
		setData(getData().setCurrentRound(currentRound));
	}

	/**
	 * Add a player in the queue of the round
	 * @param i next player in round player queue
	 */
	void addPlayerQueue(Integer i)
	{
		playerQueue.add(i);
	}

	/**
	 * Get the index of the next player
	 * @return next player to play
	 */
	Integer getNextPlayer()
	{
		if(!playerQueue.isEmpty())
			return playerQueue.remove(0);
		return -1;
	}

	/**
	 * Add a public objective card
	 * @param publicObjectiveCard to add
	 */
	void addPublicObjectiveCards(AbstractPublicObjectiveCard publicObjectiveCard)
	{
		setData(getData().addPublicObjectiveCard(publicObjectiveCard));
	}

	/**
	 * Add one tool card
	 * @param toolCard to add
	 */
	void addToolCard(AbstractToolCard toolCard)
	{
		setData(getData().addToolCard(toolCard));
	}

	/**
	 * Set difficulty
	 * @param difficulty to set
	 */
	void setDifficulty(int difficulty)
	{
		setData(getData().setDifficulty(difficulty));
	}

	void setParamToolCard(String param, Integer val)
	{
		setData(getData().setParamToolCard(param, val));
	}

	void delParamToolCard()
	{
		setData(getData().delParamToolCard());
	}

}
