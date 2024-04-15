package view;
import java.util.ArrayList;

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
		
		start = new StartScreen(650,560);
		login = new LoginScreen(650,560);
		// TODO use web images for testing to not rely on uploading images to git
		start = new StartScreen(start.getWidth(), start.getHeight());
		stats = new StatsScreen(start.getWidth(), start.getHeight());
		game = new GameView(Game.makeGame(Game.difficulty.MEDIUM));
		login = new LoginScreen(login.getWidth(), login.getHeight());
		game = new GameView(Game.makeGame(Game.difficulty.MEDIUM));

		Scene startScene = new Scene(start, 650, 560);
		Scene gameScene = new Scene(game, 650, 560);
		Scene loginScene = new Scene(login, 500, 175);
		Scene statsScene = new Scene(stats, 650,560);
		statsScene.getStylesheets().add("styles.css");
		primaryStage.setScene(startScene);
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