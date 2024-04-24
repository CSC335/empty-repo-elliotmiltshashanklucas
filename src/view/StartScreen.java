package view;

import interfaces.Action;
import interfaces.Observer;
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
import model.game.Theme;

/**
 * A customized start screen layout containing a background image and a play
 * button.
 * 
 * @author Lucas Liang
 */
public class StartScreen extends StackPane implements Observer{

	private BackgroundImage backgroundImage;
	private Button playGame;

	public StartScreen(double d, double e) {
		this.setWidth(d);
		this.setHeight(e);
		playGame = new Button("Play Game");
		this.getChildren().addAll(playGame);
		this.update();
		Theme.addObserver(this);
	}

	public void setOnClickPlay(Action e) {
		playGame.setOnAction(action -> e.onAction());
	}

	public void setEventHandlers() {
	}

	@Override
	public void update() {
		Image image = Theme.getTheme().getBackground();
		this.setStyle("-fx-background-image: url('" + image.getUrl() + "');" + "-fx-background-size: cover;");
	}

}