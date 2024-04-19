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
import model.Settings;
import model.Theme;

public class GameGUI extends Application {
	private Canvas canvas;
	private BorderPane all;
	private StartScreen start;
	private LoginScreen login;
	private StatsScreen stats;
	private GameView game;
	private Settings settings;
	private SettingsPane settingsView;
	private AccountManager accounts;
	private Button settingsButton = new Button("Settings");
	private Button previousPane = new Button("Close Settings");
	
	private final double CENTER_WIDTH = 650;
	private final double CENTER_HEIGHT = 560;

	public void start(Stage primaryStage) throws Exception {
		accounts = new AccountManager();
		all = new BorderPane();
		all.setMinHeight(700);
		all.setMinWidth(600);
		start = new StartScreen(CENTER_WIDTH, CENTER_HEIGHT);
		login = new LoginScreen(accounts, primaryStage);
		// TODO use web images for testing to not rely on uploading images to git
		start = new StartScreen(CENTER_WIDTH, CENTER_HEIGHT);
		stats = new StatsScreen(CENTER_WIDTH, CENTER_HEIGHT);
		settings = new Settings();
		game = new GameView(
			Game.makeGame(settings)
		);
		
		settingsView = new SettingsPane(650, 560, settings);
		stats.getStylesheets().add("styles.css");
		all.setTop(settingsButton);
		all.setCenter(login);
		Scene scene = new Scene(all);
		primaryStage.setScene(scene);
		primaryStage.show();
		login.setOnLogin(() -> all.setCenter(start));
		settingsButton.setOnAction(e -> {
			Node currentCenter = all.getCenter();
			all.setTop(previousPane);
			if (!(currentCenter.equals(settingsView))) {
				previousPane.setOnAction(event -> {
					all.setTop(settingsButton);
					all.setCenter(currentCenter);
				});
			}
			all.setCenter(settingsView);
		});
		game.setOnGameEnd(() -> all.setCenter(start));
		settingsView.setOnChange(() -> game.newGame(Game.makeGame(settings)));
		start.setOnClickPlay(() -> {
			all.setCenter(game);
			game.newGame(Game.makeGame(settings));
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}