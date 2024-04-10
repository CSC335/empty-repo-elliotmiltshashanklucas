package view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class StartScreen extends StackPane{
	
	private BackgroundImage backgroundImage;
	private Button playGame;
	
	public StartScreen (double d, double e) {
		this.setWidth(d);
		this.setHeight(e);
		playGame = new Button("Play Game");
        backgroundImage = new BackgroundImage(
                new Image("file:images/backgroundImg.png"),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        this.setBackground(new Background(backgroundImage));
		this.getChildren().addAll(playGame);
	}
	public void setOnClickPlay(Action e) {
		playGame.setOnAction(action -> e.onAction());
	}
	public void setEventHandlers() {
	}
	
}