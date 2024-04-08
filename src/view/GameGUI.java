package view;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameGUI extends Application{
	
	private Canvas canvas;
	private BorderPane all;
	private StartScreen start;
	public void start(Stage primaryStage) throws Exception {
		start = new StartScreen(650,560);
		Scene scene = new Scene(start, 650, 560);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}