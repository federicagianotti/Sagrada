package progetto.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test support class Couple
 */
public class TestCouple extends TestCase{

	@Test
	public void testConstructorAndGetter()
	{
		Couple couple = new Couple(1, -1);

		Assert.assertEquals(1, couple.getDx());
		Assert.assertEquals(-1, couple.getDy());
	}
}
