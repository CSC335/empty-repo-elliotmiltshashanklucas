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
import model.GameStats;
import model.account.Account;
import model.account.AccountManager;
import model.account.Account.Stats;
import model.game.Game;

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
	
	private BarChart<String, Number> barChart;
	
	private LineChart<Number, Number> lineChart;

	public StatsScreen(double width, double height, AccountManager a) {
	    this.setWidth(width);
	    this.setHeight(height);
	    this.accountManager = a;
	    initBarChart();
	    initLineChart();
	    compToAverage();
	    showBest();
	    plotRecent();
	}

	private void initBarChart() {
	    CategoryAxis x = new CategoryAxis();
	    x.setLabel("Player");
	    NumberAxis y = new NumberAxis();
	    y.setLabel("Average attempts per game");
	    barChart = new BarChart<>(x, y);
	    barChart.setBarGap(0);
	    this.setConstraints(barChart, 1, 1, 2, 2);
	    this.getChildren().add(barChart);
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
	    barChart.getData().clear();
	    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
	    series1.getData().add(new XYChart.Data<>("You", findUserAverage(accountManager.getLoggedInAccount().getDifficulty())));
	    series2.getData().add(new XYChart.Data<>("Them", findAverage(accountManager.getLoggedInAccount().getDifficulty())));
	    series1.setName("You");
	    series2.setName("Them");
	    barChart.getData().addAll(series1, series2);
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

	private void initLineChart() {
	    NumberAxis x = new NumberAxis();
	    NumberAxis y = new NumberAxis();
	    x.setLabel("Past Games");
	    y.setLabel("Number of Attempts");
	    lineChart = new LineChart<>(x, y);
	    lineChart.setTitle("Recent Performance");
	    this.setConstraints(lineChart, 1, 3, 3, 1);
	    this.getChildren().add(lineChart);
	}
	
	/**
	 * Retrieves and displays recent performance data in a line chart format.
	 * 
	 * @param none
	 * @return none
	 */
	public void plotRecent() {
	    lineChart.getData().clear();
	    XYChart.Series<Number, Number> series = new XYChart.Series<>();
	    series.setName("Player performance");
	    List<Integer> recents = getRecent(accountManager.getLoggedInAccount().getDifficulty());
	    for (int i = 0; i < recents.size(); i++) {
	        series.getData().add(new XYChart.Data<>(i, recents.get(i)));
	    }
	    lineChart.getData().add(series);
	}

	public void refreshStats() {
	    Account account = accountManager.getLoggedInAccount();
	    if (account == null) 
	    	return;  

	    updateLabels(account);
	    updateCharts(account);
	}

	private void updateLabels(Account account) {
	    Stats easyStats = account.getStats(Game.Difficulty.EASY);
	    Stats mediumStats = account.getStats(Game.Difficulty.MEDIUM);
	    Stats hardStats = account.getStats(Game.Difficulty.HARD);

	    bestEasyScore.setText("Best easy guesses: " + easyStats.getBestGuesses());
	    bestMediumScore.setText("Best medium guesses: " + mediumStats.getBestGuesses());
	    bestHardScore.setText("Best hard guesses: " + hardStats.getBestGuesses());
	    
	    averageEasyScore.setText("Average easy guesses: " + easyStats.getAverageGuesses());
	    averageMediumScore.setText("Average medium guesses: " + mediumStats.getAverageGuesses());
	    averageHardScore.setText("Average hard guesses: " + hardStats.getAverageGuesses());
	    
	    gamesPlayed.setText("Games played: " + (easyStats.getGamesPlayed() + mediumStats.getGamesPlayed() + hardStats.getGamesPlayed()));
	}

	private void updateCharts(Account account) {
	    updateBarChart(account);
	    updateLineChart(account);
	}

	private void updateBarChart(Account account) {
	    barChart.getData().clear();
	    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
	    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
	    series1.getData().add(new XYChart.Data<>("You", findUserAverage(account.getDifficulty())));
	    series2.getData().add(new XYChart.Data<>("Them", findAverage(account.getDifficulty())));
	    series1.setName("You");
	    series2.setName("Them");
	    barChart.getData().addAll(series1, series2);
	}

	private void updateLineChart(Account account) {
	    lineChart.getData().clear();
	    XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
	    series1.setName("Player performance");
	    List<Integer> recents = getRecent(account.getDifficulty());
	    for (int i = 0; i < recents.size(); i++) {
	        series1.getData().add(new XYChart.Data<>(i, recents.get(i)));
	    }
	    lineChart.getData().add(series1);
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
