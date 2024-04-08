package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Game {
	private int rows;
	private int cols;
	private int numSets;
	private int setSize;
	private int matchSize;
	private Theme theme;
	private List<Card> cards = new ArrayList<>();
	private List<Card> curGuesses = new ArrayList<>();
	private int numClicked = 0;
	private int setsFound = 0;
	private int totalGuesses = 0;

	public enum state {
		ALREADY_FACE_UP, NOT_ENOUGH_CARDS, NOT_A_MATCH, MATCH, END_OF_GAME
	}

	public Game(int r, int c, int ss, int m, Theme t) {
		// CHANGE LATER
		rows = 3;
		cols = 4;
		setSize = ss;
		matchSize = m;
		theme = t;
		numSets = rows * cols / setSize;
		newGame();
	}

	public void newGame() {
		numClicked = 0;
		setsFound = 0;
		totalGuesses = 0;
		//List<String> cardIdentifiers = getCardIdentifiers(numSets);
		List<String> cardFaces = theme.getImageStrings(numSets);
		for (String id : cardFaces) {
			for (int i = 0; i < setSize; i++) {
				cards.add(new Card(id, theme.getCardBack()));
			}
		}
		Collections.shuffle(cards);
	}

	/*
	private List<String> getCardIdentifiers(int pairCount) {
		List<String> identifiers = new ArrayList<>();
		theme.getImageStrings(numSets);
		for (int i = 1; i <= pairCount; i++) {
			identifiers.add(String.valueOf(i));
		}
		return identifiers;
	}
*/
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

	//Changed the logic of the function
	private boolean checkGuesses() {
		if(curGuesses.isEmpty()) {
			return false;
		}
		Card firstCard = curGuesses.get(0);
		for(Card card: curGuesses) {
			if(!firstCard.equals(card)) {
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

	public void setCards(List<Card> testCards){
	this.cards = testCards;
	this.numClicked = 0;
	this.setsFound = 0;
	this.totalGuesses = 0;
	
}


}