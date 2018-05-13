package progetto.game;


import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Current state of the game
 */
public final class Game implements IExecuibleGame {

	private static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	private static final int MAX_NUM_PLAYERS = 4;

	private final CommandQueue commandQueue = new CommandQueue();

	private final RoundTrack roundTrack = new RoundTrack();

	private final PlayerBoard[] playerBoard = new PlayerBoard[MAX_NUM_PLAYERS];

	private final MainBoard mainBoard = new MainBoard();

	private final DiceBag diceBag = new DiceBag();

	private final RNGenerator rnGenerator = new RNGenerator(0);

	private ArrayList<Integer> pastHashCodes = new ArrayList<>();


	/**
	 * Constructor
	 */
	public Game()
	{
		pastHashCodes.add(hashCode());
		for(int i=0; i<MAX_NUM_PLAYERS; i++)
		{
			playerBoard[i] = new PlayerBoard();
		}
	}

	/**
	 * Get commandQueue
	 * @return commandQueue
	 */
	public AbstractProcessor<AbstractGameAction> getCommandQueue()
	{
		return commandQueue;
	}

	/**
	 * Get roundTrack
	 * @return roundTrack
	 */
	public RoundTrack getRoundTrack()
	{
		return roundTrack;
	}

	/**
	 * Get playerBoard of player index
	 * @param index player number
	 * @return playerBoard[index]
	 */
	public PlayerBoard getPlayerBoard(int index)
	{
		return playerBoard[index];
	}

	/**
	 * Get mainBoard
	 * @return mainBoard
	 */
	public MainBoard getMainBoard()
	{
		return mainBoard;
	}

	/**
	 * Get diceBag
	 * @return diceBag
	 */
	public DiceBag getDiceBag()
	{
		return diceBag;
	}

	/**
	 * Get RNGenerator
	 * @return rnGenerator
	 */
	public RNGenerator getRNGenerator()
	{
		return rnGenerator;
	}

	/**
	 * Set seet of RNGenerator
	 * @param seed new value to set
	 */
	void setSeed(long seed)
	{
		rnGenerator.setSeed(seed);
	}

	/**
	 * Set game state, execute state operations
	 * @param gameState new state to set
	 */
	void setState(AbstractGameState gameState)
	{
		mainBoard.setGameState(gameState);
		gameState.apply(this);
	}

	/**
	 * Get seed
	 * @return RNGenerator seed
	 */
	long getSeed()
	{
		return rnGenerator.getSeed();
	}

	/**
	 *
	 * @param action the action that must be added to the command queue
	 */
	public void sendAction(AbstractGameAction action)
	{
		commandQueue.offer(action);
	}

	/**
	 * processes all action currently in the queue
	 * NOT THREAD SAFE
	 */
	public void processAllPendingAction()
	{
		while (commandQueue.peekPending() != null)
			processAction();
	}

	/**
	 * process the oldest pending action
	 * NOT THREAD SAFE
	 */
	public void processAction()
	{
		AbstractGameAction action = commandQueue.pollPending();
		if (action != null && action.canBeExecuted(this))
		{
			LOGGER.log(Level.FINE, "trying to execute a action");
			action.execute(this);
			pastHashCodes.add(hashCode());
		}
		else
		{
			LOGGER.log(Level.FINE, "A action could not be executed.");
		}
	}

	@Override
	public boolean equals(Object other)
	{
		if (other == null)
			return false;
		if (other.getClass() != getClass())
			return false;
		Game game = (Game) other;
		return hashCode() == game.hashCode();
	}

	@Override
	public int hashCode()
	{
		return 0;
	}

	public int getHash(int index)
	{
		return pastHashCodes.get(index);
	}



}
