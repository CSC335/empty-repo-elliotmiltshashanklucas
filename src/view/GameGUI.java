package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.AccountManager;
import model.Game;
import model.Theme;

public class GameGUI extends Application {

	private BorderPane all;
	private StartScreen start;
	private LoginScreen login;
	private StatsScreen stats;
	private GameView game;
	private SettingsPane settings;
	private AccountManager accounts;
	private Button settingsButton = new Button("Settings");
	private Button previousPane = new Button("Close Settings");

	public void start(Stage primaryStage) throws Exception {
		all = new BorderPane();
		all.setMinHeight(700);
		all.setMinWidth(600);
		start = new StartScreen(650, 560);
		login = new LoginScreen(accounts, primaryStage);
		// TODO use web images for testing to not rely on uploading images to git
		start = new StartScreen(start.getWidth(), start.getHeight());
		stats = new StatsScreen(start.getWidth(), start.getHeight());
		game = new GameView(Game.makeGame(Game.difficulty.MEDIUM));
		stats.getStylesheets().add("styles.css");
		all.setTop(settingsButton);
		all.setCenter(start);
		Scene scene = new Scene(all);
		primaryStage.setScene(scene);
		primaryStage.show();
		settingsButton.setOnAction(e -> {
			Node currentCenter = all.getCenter();
			previousPane.setOnAction(event -> all.setCenter(currentCenter));
			all.setCenter(settings);
		});
		game.setOnGameEnd(() -> all.setCenter(start));
		start.setOnClickPlay(() -> {
			all.setCenter(game);
			game.newGame();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}