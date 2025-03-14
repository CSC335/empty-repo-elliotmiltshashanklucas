package model.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import model.game.Game;

/**
 * A serializable account class which stores user information along with stats
 *
 * @author Shashank Raj Pitla
 */
public class Account implements Serializable {
	public static class Stats implements Serializable {
		private static final long serialVersionUID = 1L;
		private int totalGuesses;
		private double totalTime;
		private int gamesPlayed;
		private int totalCards;

		private Queue<Integer> recentGuesses = new LinkedList<>();

		private int bestGuesses;
		private double bestTime;
		private int bestCards;

		public Stats() {
			this.totalGuesses = 0;
			this.totalTime = 0;
			this.gamesPlayed = 0;
			this.totalCards = 0;

			this.bestGuesses = Integer.MAX_VALUE;
			this.bestTime = Double.MAX_VALUE;
			this.bestCards = Integer.MAX_VALUE;
		}

		public void addGame(int numGuesses, double numSeconds, int numCards) {
			this.totalGuesses += numGuesses;
			this.totalTime += numSeconds;
			this.totalCards += numCards;
			this.gamesPlayed++;

			if (recentGuesses.size() >= 5) {
				recentGuesses.poll();
			}
			recentGuesses.offer(numGuesses);

			if (numGuesses < bestGuesses) {
				this.bestGuesses = numGuesses;
			}
			if (numSeconds < bestTime) {
				this.bestTime = numSeconds;
			}
			if (numCards < bestCards) {
				this.bestCards = numCards;
			}
		}

		public double getAverage() {
			return gamesPlayed > 0 ? totalTime / gamesPlayed : 0;
		}

		public double getAverageCards() {
			return gamesPlayed > 0 ? (double) totalCards / gamesPlayed : 0;
		}

		public double getAverageGuesses() {
			return gamesPlayed > 0 ? (double) totalGuesses / gamesPlayed : 0;
		}

		public double getAverageTime() {
			return gamesPlayed > 0 ? totalTime / gamesPlayed : 0;
		}

		public int getBestCards() {
			return bestCards == Integer.MAX_VALUE ? 0 : bestCards;
		}

		public int getBestGuesses() {
			return bestGuesses == Integer.MAX_VALUE ? 0 : bestGuesses;
		}

		public double getBestTime() {
			return bestGuesses == Integer.MAX_VALUE ? 0 : bestTime;
		}

		public int getGamesPlayed() {
			return gamesPlayed;
		}

		public List<Integer> getRecentGuesses() {
			// TODO Auto-generated method stub
			return new ArrayList<>(recentGuesses);
		}

		public int getTotalCards() {
			return totalCards;
		}

		public int getTotalGuesses() {
			return totalGuesses;
		}

		public double getTotalTime() {
			return totalTime;
		}
	}
	private static final long serialVersionUID = 5305138822007823784L;
	private String userName;
	private String password;
	private Settings prefferedSettings;

	private Map<Game.Difficulty, Stats> gameHistory;

	public Account(String userName, String password, Settings settings) {
		this.userName = userName;
		this.password = password;
		prefferedSettings = settings;
		this.gameHistory = new HashMap<>();
	}

	public void addGameData(int numGuesses, double numSeconds, Game.Difficulty difficulty, int numberOfCards) {
		Stats stats = gameHistory.computeIfAbsent(difficulty, k -> new Stats());
		stats.addGame(numGuesses, numSeconds, numberOfCards);
	}

	public boolean correctPassword(String inputPassword) {
		return (this.password.equals(inputPassword));
	}

	public Game.Difficulty getDifficulty() {
		return this.prefferedSettings.getDifficulty();
	}

	public Map<Game.Difficulty, Stats> getHistory() {
		return this.gameHistory;
	}

	public Settings getPrefferedSettings() {
		return prefferedSettings;
	}

	public Stats getStats(Game.Difficulty difficulty) {
		return gameHistory.computeIfAbsent(difficulty, k -> new Stats());
	}

	public String getUserName() {
		return userName;
	}

	/*
	 * For testing only
	 */

	public void makeHistory() {
		gameHistory = new HashMap<>();
	}

	public void setPrefferedSettings(Settings s) {
		prefferedSettings = s;
	}
}
