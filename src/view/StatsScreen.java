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
		// add user info
		Integer easyScore = findBest(Game.Difficulty.EASY);
		Integer medScore = findBest(Game.Difficulty.MEDIUM);
		Integer hardScore = findBest(Game.Difficulty.HARD);
		Integer easyAvgScore = findBest(Game.Difficulty.EASY);
		Integer medAvgScore = findBest(Game.Difficulty.MEDIUM);
		Integer hardAvgScore = findBest(Game.Difficulty.HARD);
		Integer numPlayed = accountManager.getLoggedInAccount().getHistory().size();
		bestEasyScore = new Label("Best easy score: " + easyScore);
		bestMediumScore = new Label("Best medium score: " + medScore);
		bestHardScore = new Label("Best hard score: " + hardScore);
		averageEasyScore = new Label("Average easy score: " + easyAvgScore);
		averageMediumScore = new Label("Average medium score: " + medAvgScore);
		averageHardScore = new Label("Average hard score: " + hardAvgScore);
		gamesPlayed = new Label("Games played: " + numPlayed );
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
	private Integer findAverage(Game.Difficulty dif) {
		Map<String, Account> accounts = accountManager.getAccounts();
		Integer score = 0;
		Integer entries = 0;
		for(Map.Entry<String, Account> entry : accounts.entrySet()) {
			Account a = entry.getValue();
			if(!(a.getHistory().containsKey(dif))) {
				continue;
			}
			else {
				for(GameStats g: a.getHistory().get(dif)) {
					score += g.getNumClicks();
					entries += 1;	
				}
			}
		}
		return (score / entries);
				}
	
	private Integer findUserAverage(Game.Difficulty dif) {
		Account activeAccount = accountManager.getLoggedInAccount();
		Integer score = 0;
		Integer numGames = 0;
		if(activeAccount.getHistory().containsKey(dif)) {
			List<GameStats> stats = activeAccount.getHistory().get(dif);
			for(GameStats g: stats) {
				score += g.getNumClicks();
				numGames++;
			}
		}
		if(score == 0) {
			return -1;
		}
		else {
			return score / numGames;
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
		if(activeAccount.getHistory().containsKey(dif)) {
			List<GameStats> stats = activeAccount.getHistory().get(dif);
			Integer min = Integer.MAX_VALUE;
			for(GameStats g: stats) {
				if(g.getNumClicks() < min) {
					min = g.getNumClicks();
				}
			}
			return min;
		}
		else {
			return -1;
		}
		
	}
	/**
	 * Takes in a difficulty enum and returns the most recent 5 game scores for this 
	 * user and difficulty
	 * @param d		the enum difficulty to search for
	 * @return		5 most recent scores, -1 initialized if does not exist.
	 */
	private List<Integer> getRecent(Game.Difficulty dif){
		List<Integer> recent = new ArrayList<Integer>(Collections.nCopies(5, -1));
		Account activeAccount = accountManager.getLoggedInAccount();
		if(activeAccount.getHistory().containsKey(dif)) {
			List<GameStats> stats = activeAccount.getHistory().get(dif);
			for(int i = 0; i < Math.min(5, stats.size()); i++) {
				recent.set(i, stats.get(i).getNumClicks());
			}
		}
		return recent;
		
	}
}
