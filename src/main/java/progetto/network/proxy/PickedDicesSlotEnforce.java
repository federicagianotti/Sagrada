package progetto.network.proxy;

import progetto.model.PickedDicesSlotData;
import progetto.network.ClientConnection;
import progetto.network.IEnforce;

/**
 * @author Massimo
 */
public class PickedDicesSlotEnforce implements IEnforce
{
	private final int target;
	private final PickedDicesSlotData data;

	public PickedDicesSlotEnforce(PickedDicesSlotData data, int target)
	{
		this.data = data;
		this.target = target;
	}


	@Override
	public void execute(ClientConnection c) {
		c.getProxy().getPlayerBoard(target).getPickedDicesSlot().setData(data);
	}
}
