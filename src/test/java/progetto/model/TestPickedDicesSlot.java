package progetto.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test PickedDicesSlot class
 */
public class TestPickedDicesSlot extends TestCase {

	PickedDicesSlot pickedDicesSlot;

	@Before
	public void setUp()
	{
		pickedDicesSlot = new PickedDicesSlot();
	}

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor()
	{
		Assert.assertEquals(0, pickedDicesSlot.getNDices());
		Assert.assertNull(pickedDicesSlot.getData().getDicePlacementCondition(0));
		Assert.assertNull(pickedDicesSlot.getData().getDicePlacementCondition(1));
	}

	/**
	 * Test get number of picked dices
	 */
	@Test
	public void testGetNDices()
	{
		Assert.assertEquals(0, pickedDicesSlot.getNDices());

		pickedDicesSlot.add(new Dice(Value.ONE, GameColor.YELLOW), false, false, false);
		Assert.assertEquals(1, pickedDicesSlot.getNDices());

		pickedDicesSlot.add(new Dice(Value.ONE, GameColor.YELLOW), false, false, false);
		Assert.assertEquals(2, pickedDicesSlot.getNDices());

		pickedDicesSlot.remove(0);
		Assert.assertEquals(1, pickedDicesSlot.getNDices());

	}

	/**
	 * Test remove dice
	 */
	@Test
	public void testRemove()
	{
		Dice dice = new Dice(Value.ONE, GameColor.YELLOW);
		Dice dice1 = new Dice(Value.TWO, GameColor.YELLOW);

		pickedDicesSlot.add(dice, false, false, false);
		Assert.assertEquals(dice, pickedDicesSlot.remove(0).getDice());
		Assert.assertEquals(0, pickedDicesSlot.getNDices());

		pickedDicesSlot.add(dice, false, false, false);
		pickedDicesSlot.add(dice1, false, false, false);
		Assert.assertEquals(dice1, pickedDicesSlot.remove(1).getDice());
		Assert.assertEquals(1, pickedDicesSlot.getNDices());
		Assert.assertEquals(dice, pickedDicesSlot.remove(0).getDice());
		Assert.assertEquals(0, pickedDicesSlot.getNDices());

	}

	/**
	 * Test remove dice - fail - dice not added
	 */
	@Test
	public void testRemoveFail()
	{
		Assert.assertNull(pickedDicesSlot.remove(0));

		pickedDicesSlot.add(new Dice(Value.ONE, GameColor.YELLOW), false, false, false);

		Assert.assertNull(pickedDicesSlot.remove(1));

	}

	/**
	 * Test set ignore color/value/adjacent to a picked dice
	 */
	@Test
	public void testSetIgnore()
	{
		pickedDicesSlot.add(new Dice(Value.ONE, GameColor.YELLOW), false, true, false);

		pickedDicesSlot.setIgnoreColor(0, true);
		Assert.assertTrue(pickedDicesSlot.getData().getDicePlacementCondition(0).getIgnoreColor());

		pickedDicesSlot.setIgnoreValue(0, false);
		Assert.assertFalse(pickedDicesSlot.getData().getDicePlacementCondition(0).getIgnoreValue());

		pickedDicesSlot.setIgnoreAdjacent(0, true);
		Assert.assertTrue(pickedDicesSlot.getData().getDicePlacementCondition(0).getIgnoreAdjacent());

	}

	/**
	 * Test set ignore color/value/adjacent to a picked dice - fail - dice non added
	 */
	@Test
	public void testSetIgnoreFail()
	{
		PickedDicesSlotData pickedDicesSlotData = pickedDicesSlot.getData();

		pickedDicesSlot.setIgnoreColor(0, true);
		Assert.assertEquals(pickedDicesSlotData, pickedDicesSlot.getData());

		pickedDicesSlot.setIgnoreValue(0, true);
		Assert.assertEquals(pickedDicesSlotData, pickedDicesSlot.getData());

		pickedDicesSlot.setIgnoreAdjacent(0, true);
		Assert.assertEquals(pickedDicesSlotData, pickedDicesSlot.getData());

	}

	/**
	 * Test change picked dice
	 */
	@Test
	public void testChangeDice()
	{
		pickedDicesSlot.add(new Dice(Value.ONE, GameColor.YELLOW), false, false, false);

		pickedDicesSlot.changeDice(0, new Dice(Value.THREE, GameColor.PURPLE));
		Assert.assertEquals(Value.THREE, pickedDicesSlot.getData().getDicePlacementCondition(0).getDice().getValue());
		Assert.assertEquals(GameColor.PURPLE, pickedDicesSlot.getData().getDicePlacementCondition(0).getDice().getGameColor());

	}

}
