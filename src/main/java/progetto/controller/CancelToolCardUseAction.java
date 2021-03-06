package progetto.controller;

import progetto.model.*;

/**
 * Action to cancel the use of a tool card
 * @author Michele
 */
public class CancelToolCardUseAction extends AbstractExecutibleGameAction
{

	/**
	 * Constructor with no parameters
	 */
	public CancelToolCardUseAction(){

		super(-1);

	}

	/**
	 * Constructor with caller ID
	 * @param callerID ID of the caller
	 */
	public CancelToolCardUseAction(int callerID)
	{
		super(callerID);
	}

	/**
	 * Verify if action can be executed
	 * @param game the model in which this command should be executed
	 * @return result of the check
	 */
	@Override
	public boolean canBeExecuted(IModel game)
	{
		return game.getMainBoard().getData().getGameState().getClass() == ToolCardState.class &&
				getCallerID() == game.getRoundInformation().getData().getCurrentPlayer();
	}

	/**
	 * Execute action
	 * @param game the model in which we want to execute this command
	 */
	@Override
	public void execute(Model game)
	{
		Dice dice = game.getRoundInformation().getData().getToolCardParameters().getDice();
		if(dice != null)
		{
			game.getDiceBag().add(dice.getGameColor());
		}

		game.getRoundInformation().delParamToolCard();

		game.setState(new RoundState());

	}
}

