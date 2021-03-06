package progetto.network;

/**
 * forces the player to check if his sync object is still in sync
 * @author Massimo
 */
final class SyncCheckEnforce implements IEnforce {

	private int state;
	private int h;

	/**
	 * builds a new enforce
	 * @param stateIndex the state that must be checked for synchronization
	 * @param hash the hash that the server evaluated
	 */
	SyncCheckEnforce(int stateIndex, int hash) {
		state = stateIndex;
		h = hash;
	}

	/**
	 * forces the client connection to check the synchronization
	 * @param mng the client connection that received this enforce.
	 */
	public void execute(ClientConnection mng) {
		mng.checkSync(state, h);
	}
}
