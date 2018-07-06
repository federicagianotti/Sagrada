package progetto.network.rmi;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of IRemoteLogger
 * @author Massimo
 */
final class RMIRemoteLogger implements IRemoteLogger {
	private static final Logger LOGGER = Logger.getLogger(RMIRemoteLogger.class.getName());
	private final RMIModule server;

	RMIRemoteLogger(RMIModule server) {
		this.server = server;
	}

	public IRemoteServerSession login(final IRemoteClientSession remote) throws RemoteException {

		LOGGER.fine("Player tries to log in");
		final RMIRemoteServerSession local = new RMIRemoteServerSession();

		new Thread(() -> {
			LOGGER.log(Level.FINE, "trying to add player");
			RMIHandler handler = new RMIHandler(remote, local);
			server.getPlayerJoinedCallback().call(handler);
		}).start();

		return local;
	}

}
