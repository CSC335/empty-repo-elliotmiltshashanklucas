package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private Button statsButton = new Button("Statistics");
	private HBox topMenu;
	private Button logOutButton;
	
	private final double CENTER_WIDTH = 900;
	private final double CENTER_HEIGHT = 600;

	public void start(Stage primaryStage) throws Exception {
		accounts = new AccountManager();
		all = new BorderPane();
		all.setMinHeight(CENTER_HEIGHT + 50);
		all.setMinWidth(CENTER_WIDTH + 50);
		start = new StartScreen(CENTER_WIDTH, CENTER_HEIGHT);
		login = new LoginScreen(accounts, primaryStage);
		all.setCenter(login);
		Scene scene = new Scene(all);
		scene.getStylesheets().add("logincss.css");
		login.getStyleClass().add("login-pane");
		primaryStage.setScene(scene);
		primaryStage.show();
		setEventHandlers();
	}
	
	private void setUpTopMenu() {
		logOutButton = new Button("Logout");
		logOutButton.setOnAction(e -> {
			if(accounts.userIsLoggedIn()) {
				accounts.loggedOut();
				all.setCenter(login);
				System.out.print("Logged out successfully");
				login.setPrompt("Logged out successfully");
			}
		});
		HBox topMenu = new HBox(10);
		topMenu.getChildren().addAll(settingsButton, statsButton, logOutButton);
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
				
			System.out.println(game.getEndTime() + "\n");
			accounts.getLoggedInAccount().addGameData(game.getGuesses(), game.getEndTime(), 
					game.getDifficulty(), game.getNumCards());
			stats = new StatsScreen(CENTER_WIDTH, CENTER_HEIGHT, accounts);
			Platform.runLater(()->{
				
			all.setCenter(start);
			});
			});
			settingsView = new SettingsPane(CENTER_WIDTH, CENTER_HEIGHT, settings);
			settingsView.setOnChange(() -> {
				game.newGame(Game.makeGame(settings));
				System.out.println(accounts.getLoggedInAccount().getPrefferedSettings().getDifficulty());
				
				});
			setUpTopMenu();
		});
		
		settingsButton.setOnAction(e -> {
			Node currentCenter = all.getCenter();
			all.setTop(previousPane);
			if (!(currentCenter.equals(settingsView))) {
				previousPane.setOnAction(event -> {
					setUpTopMenu();
					all.setCenter(currentCenter);
				});
			}
			all.setCenter(settingsView);
		});
		
		statsButton.setOnAction(e -> {
			Node currentCenter = all.getCenter();
			all.setTop(previousPane);
			if (!(currentCenter.equals(stats))) {
				previousPane.setOnAction(event -> {
					setUpTopMenu();
					all.setCenter(currentCenter);
				});
			}
			all.setCenter(stats);
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