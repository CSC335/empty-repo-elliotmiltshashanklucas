package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Game;
import model.Theme;
public class GameView extends BorderPane {
	private Game game;
	private Theme theme = Theme.getTheme();
	private GridPane board;
	public GameView(Game g) {
		super();
		game = g;
		layoutGUI();
		addEventHandlers();
	}
	private void layoutGUI() {
		board = new GridPane();
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 4; j++)
				board.add(new CardView(game.getCard(i, j), theme), j, i);
		board.setHgap(5);
		board.setVgap(5);
		this.setCenter(board);
	}
	private void addEventHandlers() {
		for(Node card : board.getChildren()) {
			card.setOnMouseClicked(e -> game.guessCard(
				GridPane.getRowIndex((Node) e.getSource()),
				GridPane.getColumnIndex((Node) e.getSource())
			));
		}
	}
	
}
