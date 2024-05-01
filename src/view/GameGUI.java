package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.account.AccountManager;
import model.account.Settings;
import model.game.Game;
import model.game.Theme;

public class GameGUI extends Application {
	private BorderPane all;
	private StartScreen start;
	private LoginScreen login;
	private StatsScreen stats;
	private GameView game;
	private Settings settings;
	private SettingsPane settingsView;
	private AccountManager accounts;
	private EndGameScreen endGameScreen;
	private Button settingsButton = new Button("Settings");
	private Button gameButton = new Button("Play");
	private Button statsButton = new Button("Statistics");

	
	private final double CENTER_WIDTH = 900;
	private final double CENTER_HEIGHT = 600;

	public void start(Stage primaryStage) throws Exception {
		accounts = new AccountManager();
		all = new BorderPane();
		all.setMinHeight(CENTER_HEIGHT + 50);
		all.setMinWidth(CENTER_WIDTH + 50);
		start = new StartScreen(CENTER_WIDTH, CENTER_HEIGHT);
		login = new LoginScreen(accounts, primaryStage);
		endGameScreen = new EndGameScreen(CENTER_WIDTH, CENTER_HEIGHT);
		all.setCenter(login);
		Scene scene = new Scene(all);
		scene.getStylesheets().add("logincss.css");
		login.getStyleClass().add("login-pane");
		primaryStage.setScene(scene);
		primaryStage.show();
		setEventHandlers();
	}
	
	private void setUpTopMenu() {
		HBox topMenu = new HBox(10);
		topMenu.getChildren().addAll(gameButton, settingsButton, statsButton);
		all.setTop(topMenu);
	}

	private void setEventHandlers() {
		login.setOnLogin(() -> {
			all.setCenter(start);
			settings = accounts.getLoggedInAccount().getPrefferedSettings();
			Theme.setTheme(settings.getPrefferedTheme());
			game = new GameView(Game.makeGame(settings));
			stats = new StatsScreen(CENTER_WIDTH, CENTER_HEIGHT, accounts);
			stats.getStylesheets().add("styles.css");
			game.setOnGameEnd(() -> {
				endGameScreen.updateGameSummary(game.getGuesses(), game.getEndTime());
				gameButton.setOnAction(e -> all.setCenter(start));
				accounts.getLoggedInAccount().addGameData(game.getGuesses(), game.getEndTime(), 
				game.getDifficulty(), game.getNumCards());
				stats = new StatsScreen(CENTER_WIDTH, CENTER_HEIGHT, accounts);

				all.setCenter(endGameScreen);
			});
			settingsView = new SettingsPane(CENTER_WIDTH, CENTER_HEIGHT, settings);
			settingsView.setOnChange(() -> {
				game.newGame(Game.makeGame(settings));
				System.out.println(accounts.getLoggedInAccount().getPrefferedSettings().getDifficulty());
				gameButton.setOnAction(e -> all.setCenter(start));
				});
			setUpTopMenu();
		});
		
		settingsButton.setOnAction(e -> all.setCenter(settingsView));
		statsButton.setOnAction(e -> all.setCenter(stats));
		gameButton.setOnAction(e -> all.setCenter(start));

		start.setOnClickPlay(() -> {
			all.setCenter(game);
			game.newGame(Game.makeGame(settings));
			gameButton.setOnAction(e -> all.setCenter(game));
			accounts.getLoggedInAccount().setPrefferedSettings(settings);
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}