package progetto.network.socket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command sent to send a message.
 */
class MessageCommand extends AbstractNetworkCommand<AbstractSocket>
{

	private String message;
	private static final Logger LOGGER = Logger.getLogger( AbstractSocket.class.getName() );

	MessageCommand(String m) {
		message = m;
	}

	void execute(AbstractSocket mng) {
		LOGGER.log(Level.FINE, message);
		mng.getMessageCallback().call(message);
	}
}
