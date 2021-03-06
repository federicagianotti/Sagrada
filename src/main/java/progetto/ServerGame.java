package progetto;

import progetto.controller.EndTurnAction;
import progetto.controller.SetSeedAction;
import progetto.controller.StartGameAction;
import progetto.model.*;
import progetto.network.IEnforce;
import progetto.network.ISync;
import progetto.network.proxy.*;
import progetto.utils.Callback;
import progetto.utils.IObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A server game is a game structure that will send action to the underlying game and will provide to the room
 * the list of enforce that must be sent to the players
 */
public class ServerGame extends GameSync implements  ISync
{
	private final class DirtyTracker
	{
		private final IEnforce enforce;
		private final int origin;

		private DirtyTracker(IEnforce enforce, int origin)
		{
			this.enforce = enforce;
			this.origin = origin;
		}
	}



	private long lastTimer = -1;
	private List<DirtyTracker> dirtyDataItems = new ArrayList<>();

	private Callback<IEnforce> enforceCallback = new Callback<>();
	private IObserver<RoundTrackData> rtdObs;
	private IObserver<MainBoardData> mainObs;
	private IObserver<PlayerBoardData>[] plbObs = new IObserver[Model.MAX_NUM_PLAYERS];
	private IObserver<CommandQueueData> cmqObs;
	private IObserver<PickedDicesSlotData>[] pikcObs = new IObserver[Model.MAX_NUM_PLAYERS];
	private IObserver<DicePlacedFrameData>[] pldObs = new IObserver[Model.MAX_NUM_PLAYERS];
	private IObserver<ExtractedDicesData> extObs;
	private IObserver<RoundInformationData> inObs;
	private static final int PLAYER_BOARD_OFFSET = 1000;
	private int lastPlayerSaw = -1;
	private long allertTime = -1;

	/**
	 * Send s to the game, process it and call the enforce callback
	 * @param s must be a game action, otherwise it will fail.
	 */
	@Override
	public void sendItem(Serializable s)
	{
		dirtyDataItems.clear();
		super.sendItem(s);

		for (DirtyTracker f : dirtyDataItems)
			enforceCallback.call(f.enforce);

		enforceCallback.call(new DoneEnforce());
	}

	/**
	 * check if the enforce that must be added is already present, if it is erases the last one.
	 * @param enforce the enforce to be sent
	 * @param origin  the id of the enforces that must replace
	 */
	private void addItemEnforce(IEnforce enforce, int origin) {

		for (int a = dirtyDataItems.size() - 1; a > 0; a--)
			if (dirtyDataItems.get(a).origin == origin)
				dirtyDataItems.remove(a);

		dirtyDataItems.add(new DirtyTracker(enforce, origin));
	}

	/**
	 * creates a default server game
	 */
	public ServerGame()
	{

		rtdObs = ogg -> addItemEnforce(new RoundTrackEnforce(ogg), -1);
		mainObs = ogg -> addItemEnforce(new MainBoardReplacementEnforce(ogg), 0);
		cmqObs = ogg -> addItemEnforce(new CommandQueueEnforce(ogg), 1);
		extObs  = ogg -> addItemEnforce(new ExtractedDicesEnforce(ogg), 2);
		inObs = ogg -> addItemEnforce(new RoundInformationEnforce(ogg), -2);

		for (int a = 0; a < Model.MAX_NUM_PLAYERS; a++)
		{
			final int c = a;
			int offset = (1 + c)  * PLAYER_BOARD_OFFSET;
			plbObs[a] = ogg -> addItemEnforce(new PlayerBoardReplacementEnforce(ogg, c), offset);
			pikcObs[a] = ogg -> addItemEnforce(new PickedDicesSlotEnforce(ogg, c), offset + 1);
			pldObs[a] = ogg -> addItemEnforce(new DicePlacedFrameEnforce(ogg, c), offset + 2);
		}
		clear();
	}

	/**
	 * reset this server game
	 */
	public void clear()
	{
		if (getGame() != null)
			detachObservers();

		super.clear();
		attachObservers();

		Random random = new Random();
		sendItem(new SetSeedAction(random.nextInt()));

		List<WindowFrameCouple> list = WindowFrameCoupleArray.getInstance().getList();

		for (WindowFrameCouple l : list)
			sendItem(new AddWindowFrameCoupleAction(l));
	}

	/**
	 * check timers and triggers time outs
	 */
	@Override
	public String update() {
		if (lastTimer == -1)
		{
			lastTimer = System.currentTimeMillis();
			return "";
		}

		long estimatedElapsed = System.currentTimeMillis() - lastTimer;
		if (getGame().getModel().getMainBoard().getData().getGameState().getClass() == PreGameState.class)
		{
			if (estimatedElapsed > Settings.getSettings().getGameStartTimeOut())
			{
				sendItem(new StartGameAction());
				lastTimer = System.currentTimeMillis();
				return "Tempo scaduto, inizia la partita\n";
			}
		}
		else
		{
			if (lastPlayerSaw == getGame().getModel().getRoundInformation().getData().getCurrentPlayer())
			{
				if (estimatedElapsed > Settings.getSettings().getGameStartTimeOut()) {
					sendItem(new EndTurnAction(lastPlayerSaw));
					lastTimer = System.currentTimeMillis();
					return "Tempo scaduto, nuovo turno\n";
				}
			}
			else
			{
				lastPlayerSaw = getGame().getModel().getRoundInformation().getData().getCurrentPlayer();
				lastTimer = System.currentTimeMillis();
			}
		}

		if (System.currentTimeMillis() - allertTime > Settings.getSettings().getAllertTime())
		{
			allertTime = System.currentTimeMillis();
			return "";
		}
		return "";
	}

	/**
	 *
	 * @return the callback that is called every time something changes in the model.
	 */
	@Override
	public Callback<IEnforce> getEnforceCallback() {
		return enforceCallback;
	}

	/**
	 * detach of the observer from the current model
	 */
	private void detachObservers()
	{
		for (int a = 0; a < Model.MAX_NUM_PLAYERS; a++)
		{
			getGame().getModel().getPlayerBoard(a).removeObserver(plbObs[a]);
			getGame().getModel().getPlayerBoard(a).getDicePlacedFrame().removeObserver(pldObs[a]);
			getGame().getModel().getPlayerBoard(a).getPickedDicesSlot().removeObserver(pikcObs[a]);
		}

		getGame().getModel().getMainBoard().removeObserver(mainObs);
		getGame().getModel().getMainBoard().getExtractedDices().removeObserver(extObs);
		getGame().getModel().getCommandQueue().removeObserver(cmqObs);
		getGame().getModel().getRoundInformation().removeObserver(inObs);
		getGame().getModel().getRoundTrack().removeObserver(rtdObs);
	}

	/**
	 * attach the observers to the current model
	 */
	private void attachObservers()
	{
		for (int a = 0; a < Model.MAX_NUM_PLAYERS; a++)
		{
			getGame().getModel().getPlayerBoard(a).addObserver(plbObs[a]);
			getGame().getModel().getPlayerBoard(a).getDicePlacedFrame().addObserver(pldObs[a]);
			getGame().getModel().getPlayerBoard(a).getPickedDicesSlot().addObserver(pikcObs[a]);
		}

		getGame().getModel().getMainBoard().addObserver(mainObs);
		getGame().getModel().getMainBoard().getExtractedDices().addObserver(extObs);
		getGame().getModel().getCommandQueue().addObserver(cmqObs);
		getGame().getModel().getRoundTrack().addObserver(rtdObs);
		getGame().getModel().getRoundInformation().addObserver(inObs);
	}

	/**
	 *
	 * @return the list of enforces that must be sent to the new players
	 */
	public List<IEnforce> getNewPlayerEnforces()
	{
		IModel model = getGame().getModel();
		List<IEnforce> enforces = new ArrayList<>();
		enforces.add(new MainBoardReplacementEnforce(model.getMainBoard().getData()));
		enforces.add(new ExtractedDicesEnforce(model.getMainBoard().getExtractedDices().getData()));
		enforces.add(new CommandQueueEnforce(model.getCommandQueue().getData()));
		enforces.add(new RoundTrackEnforce(model.getRoundTrack().getData()));
		enforces.add(new RoundInformationEnforce(model.getRoundInformation().getData()));
		for (int a = 0; a < Model.MAX_NUM_PLAYERS; a++)
		{
			enforces.add(new PlayerBoardReplacementEnforce(model.getPlayerBoard(a).getData(), a));
			enforces.add(new DicePlacedFrameEnforce(model.getPlayerBoard(a).getDicePlacedFrame().getData(), a));
			enforces.add(new PickedDicesSlotEnforce(model.getPlayerBoard(a).getPickedDicesSlot().getData(), a));
		}
		enforces.add(new DoneEnforce());
		return enforces;
	}
}
