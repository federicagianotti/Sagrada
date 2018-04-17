package progetto.network.socket;

import progetto.network.INetworkClientHandler;
import progetto.network.INetworkModule;
import progetto.utils.Callback;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A socket server is a class that accept connections and redirect them on a new socket
 */
public final class SocketServer implements INetworkModule, Runnable {

	private static final Logger LOGGER = Logger.getLogger(SocketServer.class.getName());
	private final int localPort;
	private ServerSocket server = null;
	private Callback<INetworkClientHandler> playerJoinedCallback = new Callback<INetworkClientHandler>();

	public SocketServer(int port) {
		localPort = port;
	}

	/**
	 * closes the server
	 */
	public synchronized void stop() {
		if (!isRunning()) {
			LOGGER.log(Level.INFO, "Tried to stop a server that was not started or was already closed");
			return;
		}
		try {
			server.close();
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Failed to close server socket: " + e.getMessage());
		}
	}

	/**
	 * start the server, create a listening thread
	 */
	public void start() {
		if (server != null && !server.isClosed() && server.isBound()) {
			LOGGER.log(Level.INFO, "Tried to start a server that is already open and bounded");
			return;
		}
		try {
			server = new ServerSocket(localPort);
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Failed to start a server socket on " + localPort + " " + e.getMessage());
			return;
		}
		LOGGER.log(Level.INFO, "Opened a socket server");
		Thread serverThread;
		serverThread = new Thread(this);
		serverThread.start();
		LOGGER.info("Started a socket server");
	}

	/**
	 * @return true if the server is running, false otherwise
	 */
	public synchronized boolean isRunning() {
		return server != null && server.isBound() && !server.isClosed();
	}

	/**
	 * @return the callback that is called every time a new player joins the game
	 */
	public Callback<INetworkClientHandler> getPlayerJoinedCallback() {
		return playerJoinedCallback;
	}

	public void run() {
		LOGGER.log(Level.FINE, "Started a server");
		while (isRunning()) {
			acceptConnection();
		}
	}

	private void acceptConnection() {
		try {
			Socket s = server.accept();
			ClientHandler cl = new ClientHandler(s);

			playerJoinedCallback.call(cl);

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to accept: " + e.getMessage());
		}
	}

}