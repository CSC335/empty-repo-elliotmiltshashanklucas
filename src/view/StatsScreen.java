package view;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Account;

/**
 * A customized grid layout for displaying statistical information related to a
 * player's game performance.
 * 
 * @author Lucas Liang
 */

public class StatsScreen extends GridPane {

	private Account account;

	private Label bestEasyScore;

	private Label bestMediumScore;

	private Label bestHardScore;

	private Label averageEasyScore;

	private Label averageMediumScore;

	private Label averageHardScore;

	private Label gamesPlayed;

	private VBox labels;

	// Account a
	public StatsScreen(double width, double height) {
		this.setWidth(width);
		this.setHeight(height);
		// this.account = a;
		compToAverage();
		showBest();
		getRecent();
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
		// XYChart.Data<> you = account.getAverage();
		// XYChart.Data<> you = account[map].getAverage();
		XYChart.Data<String, Number> you = new XYChart.Data<>("", 10);
		XYChart.Data<String, Number> them = new XYChart.Data<>("", 15);
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
		bestEasyScore = new Label("Best easy score: ");
		bestMediumScore = new Label("Best medium score: ");
		bestHardScore = new Label("Best hard score: ");
		averageEasyScore = new Label("Average easy score: ");
		averageMediumScore = new Label("Average medium score: ");
		averageHardScore = new Label("Average hard score: ");
		gamesPlayed = new Label("Games played: ");
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
	public void getRecent() {
		NumberAxis x = new NumberAxis();
		NumberAxis y = new NumberAxis();
		x.setLabel("Past Games");
		y.setLabel("Number of Attempts");
		LineChart<Number, Number> lineChart = new LineChart<>(x, y);
		lineChart.setTitle("Recent Performance for --- mode");

		XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
		series1.setName("Player performance");
		// List = account.getRecent(5);
		series1.getData().add(new XYChart.Data<>(1, 10));
		series1.getData().add(new XYChart.Data<>(2, 20));
		series1.getData().add(new XYChart.Data<>(3, 15));
		series1.getData().add(new XYChart.Data<>(4, 25));
		series1.getData().add(new XYChart.Data<>(5, 14));

		lineChart.getData().add(series1);
		lineChart.setPrefSize(500, 250);
		this.setConstraints(lineChart, 1, 3, 3, 1);
		this.getChildren().add(lineChart);

	}

}
