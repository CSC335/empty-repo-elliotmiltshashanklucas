import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Game {
	private int rows;
	private int cols;
	private int numSets;
	private int setSize;
	private int matchSize;
	private List<Card> cards = new ArrayList<>();
	private List<Card> curGuesses = new ArrayList<>();
	private int numClicked = 0; // num of guesses for cur set
	private int setsFound = 0;
	private int totalGuesses = 0;
	public enum state{
		ALREADY_FACE_UP,NOT_ENOUGH_CARDS,NOT_A_MATCH,MATCH,END_OF_GAME
	}

	public Game(int r, int c, int ss, int m) {
		rows = r;
		cols = c;
		setSize = ss;
		matchSize = m;
		numSets = rows * cols / setSize;
		setupGame();
	}

	public void setupGame() {
		List<String> cardIdentifiers = getCardIdentifiers(numSets);
		for (String id : cardIdentifiers) {
			for (int i = 0; i < setSize; i++) {
				cards.add(new Card(id));
			}
		}
		Collections.shuffle(cards);
	}

	private List<String> getCardIdentifiers(int pairCount) {
		List<String> identifiers = new ArrayList<>();
		for (int i = 1; i <= pairCount; i++) {
			identifiers.add(String.valueOf(i));
		}
		return identifiers;
	}

	public Card getCard(int r, int c) {
		return cards.get(r * cols + c);
	}

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
				boolean f = checkGuesses();
				numClicked = 0;
				curGuesses.clear();
				if (!f) {
					return state.NOT_A_MATCH;
				} else {
					setsFound++;
					if(setsFound < numSets) {
						return state.MATCH;
					} else {
						return state.END_OF_GAME;
					}
				}
			}
		}
	}

	private boolean checkGuesses() {
		for (int i = 0; i < matchSize - 1; i++) {
			if (curGuesses.get(i) != curGuesses.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

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

	public boolean isGameOver() {
		return numSets == setsFound;
	}
	
	public int totalGuesses() {
		return totalGuesses;
	}

	public int getNumSetsFound() {
		return setsFound;
	}
}