package view;
import java.util.ArrayList;
import model.AccountManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Game;
import model.Theme;

public class GameGUI extends Application{
	
	
	private Canvas canvas;
	private BorderPane all;
	private StartScreen start;
	private LoginScreen login;
	private StatsScreen stats;
	private GameView game;
	public void start(Stage primaryStage) throws Exception {
		AccountManager accountManager = new AccountManager();
		start = new StartScreen(650,560);
		login = new LoginScreen(accountManager, primaryStage);
		Theme t = Theme.getTheme();
		t.setCardBack("dogs10.png");
		// TODO use web images for testing to not rely on uploading images to git
		
		ArrayList<String> a = new ArrayList<>();
		for(int i = 0; i < 6; i++)
			a.add("cat" + i + ".png");
		t.setImages(a);
		start = new StartScreen(start.getWidth(), start.getHeight());
		stats = new StatsScreen(start.getWidth(), start.getHeight());
		game = new GameView(Game.makeGame(Game.difficulty.MEDIUM));
		game = new GameView(Game.makeGame(Game.difficulty.MEDIUM));

		Scene startScene = new Scene(start, 650, 560);
		Scene gameScene = new Scene(game, 650, 560);
		Scene loginScene = new Scene(login, 500, 175);
		Scene statsScene = new Scene(stats, 650,560);
		statsScene.getStylesheets().add("styles.css");
		primaryStage.setScene(loginScene);
		primaryStage.show();
		game.setOnGameEnd(() ->  primaryStage.setScene(startScene));
		start.setOnClickPlay(() -> {
			primaryStage.setScene(gameScene);
			game.newGame();
			});
	}
	public static void main(String[] args) {
		launch(args);
	}
}