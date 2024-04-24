package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.account.AccountManager;
import model.account.Settings;
import model.game.Game;
import model.game.Theme;

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
	
	private final double CENTER_WIDTH = 900;
	private final double CENTER_HEIGHT = 600;

	public void start(Stage primaryStage) throws Exception {
		accounts = new AccountManager();
		all = new BorderPane();
		all.setMinHeight(CENTER_HEIGHT + 50);
		all.setMinWidth(CENTER_WIDTH + 50);
		start = new StartScreen(CENTER_WIDTH, CENTER_HEIGHT);
		login = new LoginScreen(accounts, primaryStage);
		/*
		 * need to change when this gets instantiated (account null)
		stats = new StatsScreen(CENTER_WIDTH, CENTER_HEIGHT,accounts);
		stats.getStylesheets().add("styles.css");
		*/
		all.setCenter(login);
		Scene scene = new Scene(all);
		scene.getStylesheets().add("logincss.css");
		login.getStyleClass().add("login-pane");
		primaryStage.setScene(scene);
		primaryStage.show();
		setEventHandlers();
	}

	private void setEventHandlers() {
		login.setOnLogin(() -> {
			all.setCenter(start);
			settings = accounts.getLoggedInAccount().getPrefferedSettings();
			Theme.setTheme(settings.getPrefferedTheme());
			game = new GameView(Game.makeGame(settings));
			game.setOnGameEnd(() -> {
				
			System.out.println(game.getEndTime() + "\n");
			Platform.runLater(()->{
				
			all.setCenter(start);
			});
			});
			settingsView = new SettingsPane(CENTER_WIDTH, CENTER_HEIGHT, settings);
			settingsView.setOnChange(() -> {
				game.newGame(Game.makeGame(settings));
				System.out.println(accounts.getLoggedInAccount().getPrefferedSettings().getDifficulty());
				
				});
			all.setTop(settingsButton);
		});
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


		start.setOnClickPlay(() -> {
			all.setCenter(game);
			game.newGame(Game.makeGame(settings));
			accounts.getLoggedInAccount().setPrefferedSettings(settings);
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}