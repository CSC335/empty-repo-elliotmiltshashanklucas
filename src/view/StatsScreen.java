package view;

import javafx.scene.layout.GridPane;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.Account;

public class StatsScreen extends GridPane{
	
	private Account account;
									// Account a
	public StatsScreen(double width, double height){
		this.setWidth(width);
		this.setHeight(height);
		// this.account = a;
		compToAverage();
	}
	
	public void compToAverage() {
		CategoryAxis x = new CategoryAxis();
		x.setLabel("Player");
		NumberAxis y = new NumberAxis();
		y.setLabel("Average attempts per game");
		BarChart<String, Number> barChart = new BarChart<>(x,y);
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
		this.setConstraints(barChart, 1, 1,2,2);
		this.getChildren().add(barChart);
		
	}
	
	public void showBest() {
		
	}
	
	public void getRecent() {
		
	}
	
	
	
	
	
}
