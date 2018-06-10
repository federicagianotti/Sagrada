package progetto.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Extraction of private cards
 *  Extraction of public cards
 *  Extraction of tool cards
 *  Extraction of window frames
 */
public class FrameSelectionState extends AbstractGameState {

	public FrameSelectionState() {
		super("Frame selection");
	}

	@Override
	void apply(Model game) {

		extractPrivateCards(game);

		extractPublicCards(game);

		extractToolCards(game);

		extractWindowFrame(game);

	}

	/**
	 * Extract private objective cards
	 * @param game
	 */
	private void extractPrivateCards(Model game)
	{
		ArrayList<Color> privateCards = new ArrayList<>();
		privateCards.add(Color.YELLOW);
		privateCards.add(Color.RED);
		privateCards.add(Color.BLUE);
		privateCards.add(Color.GREEN);
		privateCards.add(Color.PURPLE);
		int nPlayer = game.getMainBoard().getData().getPlayerCount();

		if(nPlayer==1)
		{
			game.getPlayerBoard(0).addPrivateObjectiveCard(new ColorShadesPrivateObjectiveCard(
				privateCards.remove(game.getRNGenerator().getNextInt(privateCards.size()))));

			game.getPlayerBoard(0).addPrivateObjectiveCard(new ColorShadesPrivateObjectiveCard(
					privateCards.remove(game.getRNGenerator().getNextInt(privateCards.size()))));

		}
		else
		{
			for(int i=0; i<nPlayer; i++)
			{
				game.getPlayerBoard(i).addPrivateObjectiveCard(new ColorShadesPrivateObjectiveCard(
						privateCards.remove(game.getRNGenerator().getNextInt(privateCards.size()))));
			}
		}
	}

	/**
	 * Extract public objective cards
	 * @param game
	 */
	private void extractPublicCards(Model game)
	{
		ArrayList<AbstractPublicObjectiveCard> publicCards = new ArrayList<>();
		publicCards.add(new RowsDifferentColorsPublicObjectiveCard());
		publicCards.add(new ColumnsDifferentColorsPublicObjectiveCard());
		publicCards.add(new RowsDifferentValuesPublicObjectiveCard());
		publicCards.add(new ColumnsDifferentValuesPublicObjectiveCard());
		publicCards.add(new ClearShadesPublicObjectiveCard());
		publicCards.add(new MediumShadesPublicObjectiveCard());
		publicCards.add(new DarkShadesPublicObjectiveCard());
		publicCards.add(new DifferentShadesPublicObjectiveCard());
		publicCards.add(new DifferentColorsPublicObjectiveCard());
		publicCards.add(new ColoredDiagonalsPublicObjectiveCard());

		int nPlayer = game.getMainBoard().getData().getPlayerCount();

		game.getMainBoard().addPublicObjectiveCards(publicCards.remove(game.getRNGenerator().getNextInt(publicCards.size())));
		game.getMainBoard().addPublicObjectiveCards(publicCards.remove(game.getRNGenerator().getNextInt(publicCards.size())));


		if(nPlayer != 1)
		{
			game.getMainBoard().addPublicObjectiveCards(publicCards.remove(game.getRNGenerator().getNextInt(publicCards.size())));

		}

	}

	/**
	 * Extract window frame for every player
	 * @param game
	 */
	private void extractWindowFrame(Model game)
	{
		List<WindowFrameCouple> windowFrameCouples = game.getMainBoard().getData().getWindowFrameCouples();
		WindowFrameCouple[] couples;
		RNGenerator rnGenerator = game.getRNGenerator();
		int nPlayer = game.getMainBoard().getData().getPlayerCount();

		if(windowFrameCouples.size()>2*nPlayer)
		{
			for(int i=0; i<nPlayer; i++)
			{
				couples = new WindowFrameCouple[2];
				couples[0]=windowFrameCouples.remove(rnGenerator.getNextInt(windowFrameCouples.size()));
				couples[1]=windowFrameCouples.remove(rnGenerator.getNextInt(windowFrameCouples.size()));
				game.getPlayerBoard(i).setExtractedWindowFrameCouples(couples);
			}
		}
		else
		{
			for(int i=0; i<nPlayer; i++)
			{
				game.getPlayerBoard(i).setEmptyWindowFrame();
			}
			game.setState(new GameStartedState());
		}
	}

	/**
	 * Extract tool cards
	 * @param game
	 */
	private void extractToolCards(Model game)
	{
		ArrayList<ToolCard> toolCards = new ArrayList<>();
		int i=1;
		toolCards.add(new ToolCard("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1", Color.PURPLE ,i));
		i++;
		toolCards.add(new ToolCard("Pennello per Eglomise", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di colore", Color.BLUE,i));
		i++;
		toolCards.add(new ToolCard("Alesatore per lamina di rame", "Muovi un qualsiasi dado nella tua vetrata ignorando le restrizioni di valore", Color.RED, i));
		i++;
		toolCards.add(new ToolCard("Lathekin", "Muovi esattamente due dadi, rispettando tutte le restrizioni di piazzamento", Color.YELLOW, i));
		i++;
		toolCards.add(new ToolCard("Taglierina circolare", "Dopo aver scelto un dado, scambia quel dado con un dado sul Tracciato dei Round", Color.GREEN, i));
		i++;
		toolCards.add(new ToolCard("Pennello per Pasta Salda", "Dopo aver scelto un dado, tira nuovamente quel dado\n" + "Se non puoi piazzarlo, riponilo nella Riserva", Color.PURPLE, i));
		i++;
		toolCards.add(new ToolCard("Martelletto", "Tira nuovamente tutti i dadi della Riserva\n" + "Questa carta può essera usata solo durante il tuo secondo turno, prima di scegliere il secondo dado", Color.BLUE, i));
		i++;
		toolCards.add(new ToolCard("Tenaglia a Rotelle", "Dopo il tuo primo turno scegli immediatamente un altro dado\n" + "Salta il tuo secondo turno in questo round", Color.RED, i));
		i++;
		toolCards.add(new ToolCard("Riga in Sughero", "Dopo aver scelto un dado, piazzalo in una casella che non sia adiacente a un altro dado\n" + "Devi rispettare tutte le restrizioni di piazzamento", Color.YELLOW, i));
		i++;
		toolCards.add(new ToolCard("Tampone Diamantato", "Dopo aver scelto un dado, giralo sulla faccia opposta", Color.GREEN, i));
		i++;
		toolCards.add(new ToolCard("Diluente per Pasta Salda", "Dopo aver scelto un dado, riponilo nel Sacchetto, poi pescane uno dal Sacchetto\n" + "Scegli il valore del nuovo dado e piazzalo, rispettando tutte le restrizioni di piazzamento", Color.PURPLE, i));
		i++;
		toolCards.add(new ToolCard("Taglierina Manuale", "Muovi fino a due dadi dello stesso colore di un solo dado sul Tracciato dei Round\n" + "Devi rispettare tutte le restrizioni di piazzamento", Color.BLUE, i));

		int nPlayer = game.getMainBoard().getData().getPlayerCount();

		if(nPlayer == 1)
		{
			int difficulty = game.getMainBoard().getData().getDifficulty();

			for(i=0; i<difficulty; i++)
			{
				game.getMainBoard().addToolCard(toolCards.remove(game.getRNGenerator().getNextInt(toolCards.size())));
			}
		}
		else
		{
			game.getMainBoard().addToolCard(toolCards.remove(game.getRNGenerator().getNextInt(toolCards.size())));
			game.getMainBoard().addToolCard(toolCards.remove(game.getRNGenerator().getNextInt(toolCards.size())));
			game.getMainBoard().addToolCard(toolCards.remove(game.getRNGenerator().getNextInt(toolCards.size())));

		}

	}


}