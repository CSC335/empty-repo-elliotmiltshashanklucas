package view;

import interfaces.Observer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.game.Game;
import model.game.Theme;

public class EndGameScreen extends BorderPane implements Observer {
	private HBox info = new HBox();
	private Label timeLabel = new Label("");
	private Label gameInfo = new Label("");
	public EndGameScreen(Double w, Double h) {
		setHeight(h);
		setWidth(w);
		info.getChildren().addAll(timeLabel, gameInfo);
		info.setSpacing(25.0);
		Font font = new Font("monospace", 50);
		timeLabel.setTextFill(Color.WHITE); 
		timeLabel.setFont(font);
		gameInfo.setFont(font);
		gameInfo.setTextFill(Color.WHITE);
		setCenter(info);
		Theme.addObserver(this);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		Image background = Theme.getTheme().getBackground();
		this.setStyle("-fx-background-image: url('" + background.getUrl() + "');" + "-fx-background-size: cover;");
	}
	public void updateGameSummary(Integer guesses, Double endTime) {
	    int minutes = endTime.intValue();
	    int seconds = (int) ((endTime - endTime.intValue())*60);

        String timeString = String.format("%02d:%02d", minutes, seconds);
        timeLabel.setText("Total Time: " + timeString);
        gameInfo.setText("Num Clicks: " + guesses);
	}
	
}
