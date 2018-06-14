package progetto.controller;

import progetto.model.*;

import java.util.Map;

/**
 * Action to execute tool card 4
 */
public class ExecuteToolCard4Action extends AbstractExecutibleGameAction{

	private static final String XPOS = "XPlacedDice";
	private static final String YPOS = "YPlacedDice";
	private static final String XPOS2 = "XPlacedDice2";
	private static final String YPOS2 = "YPlacedDice2";
	private static final int INDEX = 4;

	/**
	 * Constructor without parameters
	 */
	public ExecuteToolCard4Action()
	{
		super();
	}

	/**
	 * Constructor to set callerID
	 * @param nPlayer
	 */
	public ExecuteToolCard4Action(int nPlayer)
	{
		super(nPlayer);
	}

	/**
	 * Verify if action can be executed
	 * @param game the model in which this command should be executed
	 * @return result of the check
	 */
	@Override
	public boolean canBeExecuted(IModel game)
	{
		Map<String, Integer> map = game.getMainBoard().getData().getParamToolCard();
		int currentPlayer = game.getRoundInformation().getData().getCurrentPlayer();

		if(currentPlayer != getCallerID() || !map.containsKey(XPOS) || !map.containsKey(YPOS)
				|| !map.containsKey(XPOS2) || !map.containsKey(YPOS2) ||
				game.getMainBoard().getData().getGameState().getClass() != ToolCardState.class)
		{
			return false;
		}

		int xPos = map.get(XPOS);
		int yPos = map.get(YPOS);

		int xPos2 = map.get(XPOS2);
		int yPos2 = map.get(YPOS2);

		ToolCardState cardState = (ToolCardState)game.getMainBoard().getData().getGameState();

		return cardState.getIndex() == INDEX &&
				game.getPlayerBoard(getCallerID()).getDicePlacedFrame().getData().getDice(yPos, xPos) != null &&
				game.getPlayerBoard(getCallerID()).getDicePlacedFrame().getData().getDice(yPos2, xPos2) != null;

	}

	/**
	 * Execute action
	 * @param game the model in which we want to execute this command
	 */
	@Override
	public void execute(Model game)
	{
		Map<String, Integer> map = game.getMainBoard().getData().getParamToolCard();
		int xPos = map.get(XPOS);
		int yPos = map.get(YPOS);

		int xPos2 = map.get(XPOS2);
		int yPos2 = map.get(YPOS2);

		PlayerBoard playerBoard = game.getPlayerBoard(getCallerID());

		Dice dice = playerBoard.getDicePlacedFrame().removeDice(yPos, xPos);

		playerBoard.getPickedDicesSlot().add(dice, false, false, false);

		dice = playerBoard.getDicePlacedFrame().removeDice(yPos2, xPos2);

		playerBoard.getPickedDicesSlot().add(dice, false, false, false);

		game.getMainBoard().delParamToolCard();

		game.setState(new RoundState());

	}

}
