package progetto.network.rmi;

import progetto.Settings;
import progetto.network.INetworkHandler;
import progetto.network.INetworkModule;
import progetto.utils.Callback;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of INetowrkModule
 * @author Massimo
 */
public final class RMIModule implements INetworkModule {

	private static final Logger LOGGER = Logger.getLogger(RMIModule.class.getName());
	private static Registry registry = null;
	private final RMIRemoteLogger rmilogger = new RMIRemoteLogger(this);
	private final Callback<INetworkHandler> palyerJoinedCallback = new Callback<>();
	private boolean isRunning = false;
	private final int port;


	/**
	 * creates a new module on the indicated port
	 * @param port the port where the logger will be registered
	 */
	public RMIModule(int port)
	{
		System.setProperty("java.rmi.server.hostname", Settings.getSettings().getMyIP());
		this.port = port;
	}

	/**
	 * the registry is a resource, since there can be only one, therefore is treated as a singleton
	 * @return the registry
	 * @throws RemoteException all exceptions are thrown
	 */
	private synchronized Registry getRegistry() throws RemoteException {
		if (registry == null) {
			registry = LocateRegistry.createRegistry(port);
		}
		return registry;
	}

	/**
	 *	stop the module
	 */
	public synchronized void stop() {

		if (!isRunning) {
			return;
		}
		isRunning = false;
		try {
			getRegistry().unbind("test");
			UnicastRemoteObject.unexportObject(rmilogger, true);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "failed to unbind, are we already running {0}", e.getMessage());
		}
	}

	/**
	 *	start the module
	 */
	public synchronized void start() {
		if (isRunning)
			return;

		isRunning = true;
		try {
			IRemoteLogger stub = (IRemoteLogger) UnicastRemoteObject.exportObject(rmilogger, port);
			getRegistry().rebind("test", stub);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "FAILED TO START RMI NETWORK MODULE: {0}", e.getMessage());
		}
	}

	/**
	 *
	 * @return if the module is still open
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 *
	 * @return the callback that is called when a player joins the model
	 */
	public Callback<INetworkHandler> getPlayerJoinedCallback() {
		return palyerJoinedCallback;
	}
}
