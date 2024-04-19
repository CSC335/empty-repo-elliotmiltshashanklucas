package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import view.Observer;

/**
 * The Game class represents the logic and state of a game.
 */
public class Game implements Observer{
	private int rows;
	public int getRows() {
		return rows;
	}

	private int cols;
	public int getCols() {
		return cols;
	}

	private int numSets;
	private int setSize;
	private int matchSize;
	protected Theme theme = Theme.getTheme();
	private List<Card> cards = new ArrayList<>();
	private List<Card> curGuesses = new ArrayList<>();
	private int numClicked = 0;
	private int setsFound = 0;
	private int totalGuesses = 0;
	static final public int DEFAULT_COLS = 4;
	static final public int DEFAULT_ROWS = 3;
	
	/**
     * Enumeration of game states.
     */
	public static enum state {
		ALREADY_FACE_UP, NOT_ENOUGH_CARDS, NOT_A_MATCH, MATCH, END_OF_GAME
	}

	/**
     * Enumeration of game difficulties.
     */
	public static enum Difficulty {
		EASY, MEDIUM, HARD
	}
	
	/**
     * Creates a new game with the specified difficulty.
     *
     * @param gameMode - the difficulty of the game
     * @return a new Game object with the specified difficulty
     */
	public static Game makeGame(Settings settings) {
		int rows = settings.getRows();
		int cols = settings.getColumns();
		switch (settings.getDifficulty()) {
		case EASY:
			return new Game(rows, cols, 4, 2);
		case HARD:
			return new Game(rows, cols, 3, 3);
		default:
			return new Game(rows, cols, 2, 2);
		}
	}
	/**
     * Constructs a Game object with the specified parameters.
     *
     * @param r - the number of rows
     * @param c - the number of columns
     * @param ss - the size of a set
     * @param m - the size of a match
     */
    protected Game(int r, int c, int ss, int m) {
        Theme.addObserver(this);
        rows = r;
        cols = c;
        setSize = ss;
        matchSize = m;
        numSets = rows * cols / setSize;
        newGame();
    }

    /**
     * Constructs a Game object with the specified parameters and theme.
     *
     * @param r - the number of rows
     * @param c - the number of columns
     * @param ss - the size of a set
     * @param m - the size of a match
     * @param t - the theme of the game
     */
	protected Game(int r, int c, int ss, int m, Theme t) {
		Theme.addObserver(this);
		rows = r;
		cols = c;
		setSize = ss;
		matchSize = m;
		numSets = rows * cols / setSize;
		theme = t;
		newGame();
	}

	/**
     * Starts a new game by resetting game parameters and shuffling cards.
     */
	public void newGame() {
		numClicked = 0;
		setsFound = 0;
		totalGuesses = 0;
		// List<String> cardIdentifiers = getCardIdentifiers(numSets);
		cards.clear();
		List<String> cardFaces = theme.getImageStrings(numSets);
		for (String id : cardFaces) {
			for (int i = 0; i < setSize; i++) {
				cards.add(new Card(id));
			}
		}
		Collections.shuffle(cards);
	}
	
	/**
     * Retrieves the card at the specified row and column.
     *
     * @param r - the row index
     * @param c - the column index
     * @return the card at the specified row and column
     */
	public Card getCard(int r, int c) {
		return cards.get(r * cols + c);
	}

	/**
     * Attempts to guess the card at the specified row and column.
     *
     * @param r - the row index
     * @param c - the column index
     * @return the state of the guess
     */
	public state guessCard(int r, int c) {
		Card guess = cards.get(r * cols + c);
		if (guess.isFlipped()) {
			return state.ALREADY_FACE_UP;
		} else {
			guess.flipCard();
			totalGuesses++;
			numClicked++;
			curGuesses.add(guess);
			if (numClicked < matchSize) {
				return state.NOT_ENOUGH_CARDS;
			} else {
				if (!checkGuesses()) {
					numClicked = 0;
					for (Card card : curGuesses) {
						card.flipCard();
					}
					curGuesses.clear();
					return state.NOT_A_MATCH;
				} else {
					numClicked = 0;
					curGuesses.clear();
					setsFound++;
					if (setsFound < numSets) {
						return state.MATCH;
					} else {
						return state.END_OF_GAME;
					}
				}
			}
		}
	}

	/**
     * Checks if the current guesses form a match.
     *
     * @return true if the guesses form a match, false otherwise
     */
	private boolean checkGuesses() {
		if (curGuesses.isEmpty()) {
			return false;
		}
		Card firstCard = curGuesses.get(0);
		for (Card card : curGuesses) {
			if (!firstCard.equals(card)) {
				return false;
			}
		}
		return true;
	}

	/**
     * Flips the card at the specified index.
     *
     * @param cardIndex - the index of the card to flip
     * @return the flipped card
     */
	public Card flipCard(int cardIndex) {
		if (cardIndex >= 0 && cardIndex < cards.size()) {
			Card selectedCard = cards.get(cardIndex);
			selectedCard.flipCard();
			return selectedCard;
		} else {
			System.out.println("Invalid Card Size");
			return null;
		}
	}

	/**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
	public boolean isGameOver() {
		return numSets == setsFound;
	}
	
	/**
     * Retrieves the total number of guesses made.
     *
     * @return the total number of guesses made
     */
	public int totalGuesses() {
		return totalGuesses;
	}

	/**
     * Retrieves the number of sets found.
     *
     * @return the number of sets found
     */
	public int getNumSetsFound() {
		return setsFound;
	}

	/**
     * Sets the cards for testing purposes.
     *
     * @param testCards - the list of cards to set
     */
	public void setCards(List<Card> testCards) {
		this.cards = testCards;
		this.numClicked = 0;
		this.setsFound = 0;
		this.totalGuesses = 0;

	}

	/**
	 * Updates the game theme when theme changes.
	 */
	@Override
	public void update() {
		theme = Theme.getTheme();
	}

}