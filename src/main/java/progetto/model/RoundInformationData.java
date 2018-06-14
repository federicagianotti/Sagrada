package progetto.model;

import java.io.Serializable;
import java.util.*;

/**
 * Immutable class with data of RoundInformation
 */
public final class RoundInformationData implements Serializable {

	private final int currentFirstPlayer;
	private final int currentPlayer;
	private final int currentRound;

	private List<Integer> playerQueue;

	/**
	 * Constructor
	 */
	public RoundInformationData()
	{
		currentFirstPlayer = 0;
		currentPlayer = 0;
		currentRound = 1;

		ArrayList<Integer> tempQ = new ArrayList<>();
		playerQueue = Collections.unmodifiableList(tempQ);

	}

	/**
	 * Constructor to set PlayerQueue List
	 * @param roundInformationData previous roundInformationData
	 * @param playerQueue list to set
	 */
	private RoundInformationData(RoundInformationData roundInformationData, List<Integer> playerQueue)
	{
		this.playerQueue = Collections.unmodifiableList(playerQueue);

		this.currentFirstPlayer = roundInformationData.currentFirstPlayer;
		this.currentPlayer = roundInformationData.currentPlayer;
		this.currentRound = roundInformationData.currentRound;

	}

	/**
	 * Constructor to set a new value for different attributes
	 * @param roundInformationData previous roundInformationData
	 * @param currentFirstPlayer new currentFirstPlayer value
	 * @param currentPlayer new currentPlayer value
	 * @param currentRound new currentRound value
	 */
	private RoundInformationData(RoundInformationData roundInformationData, int currentFirstPlayer, 
	                             int currentPlayer, int currentRound)
	{
		this.currentFirstPlayer = currentFirstPlayer;
		this.currentPlayer = currentPlayer;
		this.currentRound = currentRound;
		
		ArrayList<Integer> tempQ = new ArrayList<>(roundInformationData.playerQueue);
		playerQueue = Collections.unmodifiableList(tempQ);

	}

	/**
	 * Get current first player
	 * @return current first player
	 */
	public int getCurrentFirstPlayer()
	{
		return currentFirstPlayer;
	}

	/**
	 * Get current player
	 * @return current player
	 */
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}

	/**
	 * Get current round
	 * @return current round
	 */
	public int getCurrentRound()
	{
		return currentRound;
	}

	/**
	 * Set current first player
	 * @param currentFirstPlayer to set
	 * @return new roundInformationData with modified currentFirstPlayer
	 */
	 RoundInformationData setCurrentFirstPlayer(int currentFirstPlayer)
	{
		return new RoundInformationData(this, currentFirstPlayer, currentPlayer , currentRound);
	}

	/**
	 * Set current player
	 * @param currentPlayer to set
	 * @return new  RoundInformationData with modified currentPlayer
	 */
	 RoundInformationData setCurrentPlayer(int currentPlayer)
	{
		return new  RoundInformationData(this, currentFirstPlayer, currentPlayer , currentRound);
	}

	/**
	 * Set current round
	 * @param currentRound to set
	 * @return new  RoundInformationData with modified currentRound
	 */
	 RoundInformationData setCurrentRound(int currentRound)
	{
		return new  RoundInformationData(this, currentFirstPlayer, currentPlayer , currentRound);
	}

	/**
	 * Get next player
	 * @return next player
	 */
	public Integer getNextPlayer()
	{
		if(!playerQueue.isEmpty())
		{
			return playerQueue.get(0);
		}
		return -1;
	}

	/**
	 * Add a player in the queue of the round
	 * @param tempQ player list
	 * @return new  RoundInformationData with modified player queue
	 */
	public  RoundInformationData setPlayerQueue(List<Integer> tempQ)
	{
		return new  RoundInformationData(this, tempQ);
	}

	/**
	 * Get a list of players will play before finish the round
	 * @return list
	 */
	public List<Integer> getRoundPlayerList()
	{
		return new ArrayList<>(playerQueue);
	}


}
