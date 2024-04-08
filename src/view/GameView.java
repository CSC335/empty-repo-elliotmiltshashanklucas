package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Game;
public class GameView extends BorderPane {
	private Game game;
	private GridPane boardCanvas;
	public GameView(Game g) {
		super();
		game = g;
	}
}
