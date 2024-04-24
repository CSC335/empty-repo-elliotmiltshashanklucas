package model;

import model.game.Game;

public class GameStats {
	private int numClicks;
	private double time;
	private Game.Difficulty dif;
	private int numCards;
	
	public GameStats(int numGuesses, double numSeconds, Game.Difficulty difficulty, int numberOfCards) {
		this.numClicks = numGuesses;
		this.time = numSeconds;
		this.dif = difficulty;
		this.numCards = numberOfCards;
	}
	
	public int getNumClicks() {
		return numClicks;
	}
	
	public double getTime() {
		return time;
	}
	
	public Game.Difficulty getDifficulty() {
		return dif;
	}
	
	public int getNumCards() {
		return numCards;
	}

}
