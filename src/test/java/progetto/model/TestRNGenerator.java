package progetto.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test random generator
 */
public class TestRNGenerator extends TestCase {

	RNGenerator rnGenerator;

	@Before
	public void setUp()
	{
		rnGenerator = new RNGenerator(0);
	}

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor()
	{
		Assert.assertEquals(0, rnGenerator.getSeed());
	}

	/**
	 * Test get random values with seed
	 */
	@Test
	public void testRandomWithSeed()
	{
		RNGenerator rnGenerator1 = new RNGenerator(1234123412);
		RNGenerator rnGenerator2 = new RNGenerator(1234123412);

		Assert.assertEquals(1234123412, rnGenerator1.getSeed());
		Assert.assertEquals(1234123412, rnGenerator2.getSeed());

		for(int i=0; i<500; i++)
		{
			assertEquals(rnGenerator1.getNextInt(6), rnGenerator2.getNextInt(6));
		}

		rnGenerator1.setSeed(1212121212);
		rnGenerator2.setSeed(1212121212);

		Assert.assertEquals(1212121212, rnGenerator1.getSeed());
		Assert.assertEquals(1212121212, rnGenerator2.getSeed());

		for(int i=0; i<500; i++)
		{
			assertEquals(rnGenerator1.getNextInt(6), rnGenerator2.getNextInt(6));
		}

	}

	/**
	 * Test extract random value
	 */
	@Test
	public void testExtract()
	{
		RNGenerator rng1 = new RNGenerator(1010101010);
		RNGenerator rng2 = new RNGenerator(1010101010);
		DiceBag db1 = new DiceBag();
		DiceBag db2 = new DiceBag();
		Dice dice1;
		Dice dice2;

		for(int i=0; i<90; i++)
		{
			dice1 = rng1.extractDice(db1);
			dice2 = rng2.extractDice(db2);
			//System.out.println("Extracted dice: \n\tColor: " + dice1.getColor() + "\n\tValue: " + dice1.getValue());
			Assert.assertEquals(dice1.getGameColor(), dice2.getGameColor());
			Assert.assertEquals(dice1.getValue(), dice2.getValue());
		}
	}

	/**
	 * Test platform independent
	 */
	@Test
	public void testPlatformIndependent()
	{
		RNGenerator rng1 = new RNGenerator(432143214);
		DiceBag db = new DiceBag();
		Dice dice;

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.BLUE, dice.getGameColor());
		Assert.assertEquals(Value.TWO, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.BLUE, dice.getGameColor());
		Assert.assertEquals(Value.THREE, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.YELLOW, dice.getGameColor());
		Assert.assertEquals(Value.TWO, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.GREEN, dice.getGameColor());
		Assert.assertEquals(Value.FIVE, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.BLUE, dice.getGameColor());
		Assert.assertEquals(Value.TWO, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.GREEN, dice.getGameColor());
		Assert.assertEquals(Value.FIVE, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.YELLOW, dice.getGameColor());
		Assert.assertEquals(Value.FIVE, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.GREEN, dice.getGameColor());
		Assert.assertEquals(Value.FIVE, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.GREEN, dice.getGameColor());
		Assert.assertEquals(Value.SIX, dice.getValue());

		dice = rng1.extractDice(db);
		Assert.assertEquals(GameColor.RED, dice.getGameColor());
		Assert.assertEquals(Value.THREE, dice.getValue());

	}

	/**
	 * Test method to roll again a dice
	 */
	@Test
	public void testRollAgain()
	{
		RNGenerator rng = new RNGenerator(0);
		Dice dice = new Dice(Value.FOUR, GameColor.RED);


		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.ONE, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.FIVE, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.TWO, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.SIX, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.SIX, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.SIX, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.SIX, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.FOUR, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.FOUR, dice.getValue());

		dice=rng.rollAgain(dice);
		Assert.assertEquals(Value.THREE, dice.getValue());

	}
}
