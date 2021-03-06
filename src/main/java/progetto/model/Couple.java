package progetto.model;

/**
 * Support class to find nearby locations in the window
 * @author Michele
 */
public final class Couple {
	private int dx;
	private int dy;

	/**
	 * Constructor to set dx, dy
	 * @param dx dx
	 * @param dy dy
	 */
	Couple(int dx, int dy)
	{
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Get dx
	 * @return dx
	 */
	int getDx()
	{
		return dx;
	}

	/**
	 * Get dy
	 * @return dy
	 */
	int getDy()
	{
		return dy;
	}
}
