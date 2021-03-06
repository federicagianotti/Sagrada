package progetto.network.socket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command sent to send a message.
 */
class MessageCommand implements INetworkCommand<AbstractSocket> {

	private static final Logger LOGGER = Logger.getLogger(AbstractSocket.class.getName());
	private String message;

	MessageCommand(String m) {
		message = m;
	}

	/**
	 * sends a message to the other side
	 * @param mng the manager that will receive this command
	 */
	public void execute(AbstractSocket mng) {
		LOGGER.log(Level.FINE, message);
		mng.getMessageCallback().call(message);
	}
}
