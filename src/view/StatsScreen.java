package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Account;
import model.Account.Stats;
import model.AccountManager;
import model.Game;
import model.GameStats;

/**
 * A customized grid layout for displaying statistical information related to a
 * player's game performance.
 * 
 * @author Lucas Liang
 */

public class StatsScreen extends GridPane {

	private AccountManager accountManager;

	private Label bestEasyScore;

	private Label bestMediumScore;

	private Label bestHardScore;

	private Label averageEasyScore;

	private Label averageMediumScore;

	private Label averageHardScore;

	private Label gamesPlayed;

	private VBox labels;

	public StatsScreen(double width, double height, AccountManager a) {
		this.setWidth(width);
		this.setHeight(height);
		this.accountManager = a;
		compToAverage();
		showBest();
		plotRecent();
	}

	/**
	 * Compares the player's average performance against other players and displays
	 * it using a bar chart. Assumes the existence of an Account object with
	 * relevant statistics.
	 * 
	 * @param none
	 * @return none
	 */
	public void compToAverage() {
		CategoryAxis x = new CategoryAxis();
		x.setLabel("Player");
		NumberAxis y = new NumberAxis();
		// need to handle difficulties too -- maybe implement a dropdown select?
		y.setLabel("Average attempts per game");
		BarChart<String, Number> barChart = new BarChart<>(x, y);
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		XYChart.Data<String, Number> you = new XYChart.Data<>("", findUserAverage(accountManager.getLoggedInAccount().getDifficulty()));
		XYChart.Data<String, Number> them = new XYChart.Data<>("", findAverage(accountManager.getLoggedInAccount().getDifficulty()));
		series1.getData().add(you);
		series1.setName("You");
		series2.getData().add(them);
		series2.setName("Them");
		barChart.getData().addAll(series1, series2);
		barChart.setBarGap(0);
		this.setConstraints(barChart, 1, 1, 2, 2);
		this.getChildren().add(barChart);

	}

	/**
	 * Displays the best scores and average scores for different game modes.
	 * 
	 * @param none
	 * @return none
	 */
	public void showBest() {
		labels = new VBox(10);
		Account account = accountManager.getLoggedInAccount();
		Stats easyStats = account.getStats(Game.Difficulty.EASY);
		Stats mediumStats = account.getStats(Game.Difficulty.MEDIUM);
		Stats hardStats = account.getStats(Game.Difficulty.HARD);
		bestEasyScore = new Label("Best easy guesses: " + easyStats.getBestGuesses());
		bestMediumScore = new Label("Best medium guesses: " + mediumStats.getBestGuesses());
		bestHardScore = new Label("Best hard guesses: " + hardStats.getBestGuesses());
		
		averageEasyScore = new Label("Average easy guesses " + easyStats.getAverageGuesses());
		averageMediumScore = new Label("Average medium guesses " + mediumStats.getAverageGuesses());
		averageHardScore = new Label("Average hard guesses " + hardStats.getAverageGuesses());
		
		gamesPlayed = new Label("Games played: " + (easyStats.getGamesPlayed() + mediumStats.getGamesPlayed() + hardStats.getGamesPlayed()));
		
		labels.getChildren().addAll(bestEasyScore, bestMediumScore, bestHardScore, averageEasyScore, averageMediumScore,
				averageHardScore, gamesPlayed);
		this.setConstraints(labels, 3, 2, 1, 1);
		labels.setPadding(new Insets(50, 0, 0, 0));
		this.getChildren().add(labels);

	}

	/**
	 * Retrieves and displays recent performance data in a line chart format.
	 * 
	 * @param none
	 * @return none
	 */
	public void plotRecent() {
		NumberAxis x = new NumberAxis();
		NumberAxis y = new NumberAxis();
		x.setLabel("Past Games");
		y.setLabel("Number of Attempts");
		LineChart<Number, Number> lineChart = new LineChart<>(x, y);
		lineChart.setTitle("Recent Performance for " + accountManager.getLoggedInAccount().getDifficulty().name().toLowerCase() + " mode");

		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
		series1.setName("Player performance");

		List<Integer> recents = getRecent(accountManager.getLoggedInAccount().getDifficulty());
		for(int i = 0; i < recents.size(); i++) {
			series1.getData().add(new XYChart.Data<>(i, recents.get(i)));
		}

		lineChart.getData().add(series1);
		lineChart.setPrefSize(500, 250);
		this.setConstraints(lineChart, 1, 3, 3, 1);
		this.getChildren().add(lineChart);

	}
	
	/**
	 * Takes in a difficulty value and finds the average number of clicks for 
	 * all users on this difficulty
	 * @param difficulty	the enum difficulty to search for
	 * @return		average number of clicks it took a user to win on this difficulty
	 */
	private double findAverage(Game.Difficulty dif) {
		Map<String, Account> accounts = accountManager.getAccounts();
		double totalAverage = 0;
		int accountCount = 0;
		for (Account account : accounts.values()) {
			Stats stats = account.getStats(dif);
			if(stats.getGamesPlayed() > 0) {
				totalAverage += stats.getAverageGuesses();
				accountCount++;
			}
		}
		return accountCount > 0 ? totalAverage / accountCount : 0.0;
	}

	/**
	 * Finds the average number of clicks (guesses) for the currently logged-in user for a specified difficulty.
	 * @param difficulty the difficulty level to fetch the average guesses for.
	 * @return the average number of guesses or -1 if no games have been played at this difficulty.
	 */
	private double findUserAverage(Game.Difficulty difficulty) {
		Account loggedInAccount = accountManager.getLoggedInAccount();
		Stats stats = loggedInAccount.getStats(difficulty);
		
		if(stats.getGamesPlayed() > 0) {
			return stats.getAverageGuesses();
		}else {
			return -1.0;
		}
	}
	
	/**
	 * Takes in a difficulty enum and returns the lowest score achieved by the user for 
	 * this difficulty
	 * @param d		the enum difficulty to search for
	 * @return		lowest score if exists else -1
	 */
	private Integer findBest(Game.Difficulty dif) {
		Account activeAccount = accountManager.getLoggedInAccount();
		Stats stats = activeAccount.getStats(dif);
		return stats.getBestGuesses();	
	}
	/**
	 * Takes in a difficulty enum and returns the most recent 5 game scores for this 
	 * user and difficulty
	 * @param d		the enum difficulty to search for
	 * @return		5 most recent scores, -1 initialized if does not exist.
	 */
	private List<Integer> getRecent(Game.Difficulty dif){
		Account activateAccount = accountManager.getLoggedInAccount();
		Stats stats = activateAccount.getStats(dif);
		
		List<Integer> recentGuesses = stats.getRecentGuesses();
		while(recentGuesses.size() < 5) {
			recentGuesses.add(0, -1);
		}
		return recentGuesses;
	}
}
