package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class StartScreen extends StackPane{
	
	private BackgroundImage backgroundImage;
	private Button playGame;
	
	public StartScreen (int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		playGame = new Button("Play Game");
		Image image = new Image("file:images/backgroundImg.png");
		this.setStyle("-fx-background-image: url('" + image.getUrl() + "');" +
		                "-fx-background-size: cover;");
		this.getChildren().addAll(playGame);
	}
	
	public void setEventHandlers() {
		playGame.setOnAction((e)->{
			// implement observable, 
			// setViewTo(loginPanel);
		
		});
	}
	
}