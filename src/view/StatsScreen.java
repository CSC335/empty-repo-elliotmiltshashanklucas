package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Leaderboard;
import model.account.Account;
import model.account.Account.Stats;
import model.account.AccountManager;
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
	private Label blankScore;
	private VBox leaderBoardBox;
	private Label leaders;

	private ComboBox<String> selectDifficulty;

	private VBox labels;

	private BarChart<String, Number> barChart;

	private LineChart<Number, Number> lineChart;

	private TableView<Leaderboard> leaderboardTable;

	public StatsScreen(double width, double height, AccountManager a) {
		this.setWidth(width);
		this.setHeight(height);
		this.accountManager = a;
		selectDifficulty = new ComboBox<>();
		selectDifficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
		selectDifficulty.setValue("EASY");

	    this.add(selectDifficulty, 4, 1);
	    addEventHandlers();
	    initBarChart();
	    initLineChart();
	    compToAverage(Game.Difficulty.valueOf("EASY"));
	    showBest();
	    plotRecent(Game.Difficulty.valueOf("EASY"));
	    initLeaderboardTable();
	    updateLeaderboard(Game.Difficulty.valueOf(selectDifficulty.getValue()));
	}

	private void addEventHandlers() {
		selectDifficulty.setOnAction((e)->{
	        Game.Difficulty selectedDifficulty = Game.Difficulty.valueOf(selectDifficulty.getValue());
			compToAverage(selectedDifficulty);
			plotRecent(selectedDifficulty);
			updateLeaderboard(selectedDifficulty);
		});
	}

	/**
	 * Compares the player's average performance against other players and displays
	 * it using a bar chart. Assumes the existence of an Account object with
	 * relevant statistics.
	 *
	 * @param Game.Difficulty d
	 */
	public void compToAverage(Game.Difficulty d) {
		barChart.getData().clear();
		XYChart.Series<String, Number> series1 = new XYChart.Series<>();
		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		series1.getData().add(new XYChart.Data<>("You", findUserAverage(d)));
		series2.getData().add(new XYChart.Data<>("Them", findAverage(d)));
		series1.setName("You");
		series2.setName("Them");
		barChart.getData().addAll(series1, series2);
	}

	/**
	 * Takes in a difficulty value and finds the average number of clicks for all
	 * users on this difficulty
	 *
	 * @param difficulty the enum difficulty to search for
	 * @return average number of clicks it took a user to win on this difficulty
	 */
	private double findAverage(Game.Difficulty dif) {
		Map<String, Account> accounts = accountManager.getAccounts();
		double totalAverage = 0;
		int accountCount = 0;
		for (Account account : accounts.values()) {
			Stats stats = account.getStats(dif);
			if (stats.getGamesPlayed() > 0) {
				totalAverage += stats.getAverageGuesses();
				accountCount++;
			}
		}
		return accountCount > 0 ? totalAverage / accountCount : 0.0;
	}
	/**
	 * Takes in a difficulty enum and returns the lowest score achieved by the user
	 * for this difficulty
	 *
	 * @param d the enum difficulty to search for
	 * @return lowest score if exists else -1
	 */
	private Integer findBest(Game.Difficulty dif) {
		Account activeAccount = accountManager.getLoggedInAccount();
		Stats stats = activeAccount.getStats(dif);
		return stats.getBestGuesses();
	}

	/**
	 * Finds the average number of clicks (guesses) for the currently logged-in user
	 * for a specified difficulty.
	 *
	 * @param difficulty the difficulty level to fetch the average guesses for.
	 * @return the average number of guesses or -1 if no games have been played at
	 *         this difficulty.
	 */
	private double findUserAverage(Game.Difficulty difficulty) {
		Account loggedInAccount = accountManager.getLoggedInAccount();
		Stats stats = loggedInAccount.getStats(difficulty);

		if (stats.getGamesPlayed() > 0) {
			return stats.getAverageGuesses();
		} else {
			return -1.0;
		}
	}

	private List<Map.Entry<String, Integer>> getLeaderboard(Game.Difficulty difficulty){
	    Map<String, Integer> leaderboard = new HashMap<>();
	    for (Account account : accountManager.getAccounts().values()) {
	        Stats stats = account.getStats(difficulty);
	        if (stats.getGamesPlayed() > 0) {
	            leaderboard.put(account.getUserName(), stats.getBestGuesses());
	        }
	    }
	    List<Map.Entry<String, Integer>> sortedLeaderboard = new ArrayList<>(leaderboard.entrySet());
	    sortedLeaderboard.sort(Map.Entry.comparingByValue());
	    return sortedLeaderboard;
	}

	/**
	 * Takes in a difficulty enum and returns the most recent 5 game scores for this
	 * user and difficulty
	 *
	 * @param d the enum difficulty to search for
	 * @return 5 most recent scores, -1 initialized if does not exist.
	 */
	private List<Integer> getRecent(Game.Difficulty dif) {
		Account activateAccount = accountManager.getLoggedInAccount();
		Stats stats = activateAccount.getStats(dif);

		List<Integer> recentGuesses = stats.getRecentGuesses();
		while (recentGuesses.size() < 5) {
			recentGuesses.add(0, -1);
		}
		return recentGuesses;
	}

	/**
	 * Creates bar chart
	 */
	private void initBarChart() {
	    CategoryAxis x = new CategoryAxis();
	    x.setLabel("Player");
	    NumberAxis y = new NumberAxis();
	    y.setLabel("Average attempts");
	    barChart = new BarChart<>(x, y);
	    barChart.setBarGap(0);
	    GridPane.setConstraints(barChart, 1, 1, 2, 2);
	    this.getChildren().add(barChart);
	}

	private void initLeaderboardTable() {
	    leaderboardTable = new TableView<>();

	    TableColumn<Leaderboard, String> nameColumn = new TableColumn<>("Player Name");
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
	    nameColumn.setPrefWidth(190);

	    TableColumn<Leaderboard, Integer> scoreColumn = new TableColumn<>("Score");
	    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
	    scoreColumn.setPrefWidth(60);
	    leaders = new Label("Leaderboard");
	    leaderboardTable.getColumns().addAll(nameColumn, scoreColumn);
	    leaderBoardBox = new VBox();
	    leaderBoardBox.getChildren().addAll(leaders, leaderboardTable);
	    leaderBoardBox.setSpacing(10);
	    GridPane.setConstraints(leaderBoardBox, 4, 3);
	    this.getChildren().add(leaderBoardBox);
	    leaderBoardBox.setStyle("-fx-table-cell-border-color: transparent;");
	}

	/**
	 * Creates line chart
	 */
	private void initLineChart() {
	    NumberAxis x = new NumberAxis();
	    NumberAxis y = new NumberAxis();
	    x.setLabel("Past Games");
	    y.setLabel("Attempts");
	    lineChart = new LineChart<>(x, y);
	    lineChart.setTitle("Recent Performance");
	    GridPane.setConstraints(lineChart, 1, 3, 3, 1);
	    this.getChildren().add(lineChart);

	}

	/**
	 * Retrieves and displays recent performance data in a line chart format.
	 *
	 * @param Game difficulty to display stats by
	 *
	 */
	public void plotRecent(Game.Difficulty d) {
		lineChart.getData().clear();
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Player performance");
		List<Integer> recents = getRecent(d);
		for (int i = 0; i < recents.size(); i++) {
			series.getData().add(new XYChart.Data<>(i, recents.get(i)));
		}
		lineChart.getData().add(series);
	}

	/**
	 * Displays the best scores and average scores for different game modes.
	 *
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
		blankScore = new Label(" ");

		gamesPlayed = new Label("Games played: " + (easyStats.getGamesPlayed() + mediumStats.getGamesPlayed() + hardStats.getGamesPlayed()));

		labels.getChildren().addAll(bestEasyScore, bestMediumScore, bestHardScore, averageEasyScore, averageMediumScore,
			averageHardScore, gamesPlayed, blankScore);
		labels.getChildren().stream().map(x -> x.getStyleClass().add("stats-label"));
		applyCss();
		GridPane.setConstraints(labels, 4, 2);
		labels.setPadding(new Insets(50, 0, 0, 0));
		this.getChildren().add(labels);

	}
	private void updateLeaderboard(Game.Difficulty difficulty) {
	    List<Leaderboard> leaderboardEntries = getLeaderboard(difficulty).stream()
	        .map(e -> new Leaderboard(e.getKey(), e.getValue()))
	        .collect(Collectors.toList());

	    ObservableList<Leaderboard> data = FXCollections.observableArrayList(leaderboardEntries);
	    leaderboardTable.setItems(data);
	}
}
