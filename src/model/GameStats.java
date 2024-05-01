package model;

import model.game.Game;

/**
 * Represents the statistics of a single game
 */
public class GameStats {
	private int numClicks;
	private double time;
	private Game.Difficulty dif;
	private int numCards;

	/**
	 * Makes a new GameStats object with the specified parameters
	 *
	 * @param numGuesses    - The total number of guesses
	 * @param numSeconds    - The total time taken to beat the game
	 * @param difficulty    - The game's difficulty
	 * @param numberOfCards - The number of cards
	 */
	public GameStats(int numGuesses, double numSeconds, Game.Difficulty difficulty, int numberOfCards) {
		this.numClicks = numGuesses;
		this.time = numSeconds;
		this.dif = difficulty;
		this.numCards = numberOfCards;
	}

	/**
	 * @return The game difficulty
	 */
	public Game.Difficulty getDifficulty() {
		return dif;
	}

	/**
	 * @return The number of cards
	 */
	public int getNumCards() {
		return numCards;
	}

	/**
	 * @return The total number of guesses
	 */
	public int getNumClicks() {
		return numClicks;
	}

	/**
	 * @return The total time taken
	 */
	public double getTime() {
		return time;
	}

}
