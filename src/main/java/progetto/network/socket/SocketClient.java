package progetto.network.socket;

import progetto.network.AbstractEnforce;
import progetto.network.AbstractRequest;
import progetto.network.INetworkClient;
import progetto.utils.Callback;

/**
 * A socket client is a implementation of INetwork client, it is used on the client side to communicate with the server
 */
public final class SocketClient extends AbstractSocket implements INetworkClient {

	private final Callback<AbstractEnforce> enforceCallback = new Callback<AbstractEnforce>();

	public SocketClient(String ip, int port) {
		super(ip, port);
	}

	public void sendRequest(AbstractRequest request) {
		sendCommand(new RequestCommand(request));
	}


	public Callback<AbstractEnforce> getEnforceCallback() {
		return enforceCallback;
	}


	/**
	 * called when the connection gets closed.
	 */
	protected final void onTearDown() {
		//noting to do on tear down
	}


}