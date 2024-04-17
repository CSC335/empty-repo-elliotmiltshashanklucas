package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Game;
import model.Theme;

/**
 * GameView is a BorderPane that displays a game board and handles game
 * interactions, including theme changes and game restarts. It observes changes
 * in the Theme model.
 */
public class GameView extends BorderPane implements Observer {
	private Game game;
	private Theme theme = Theme.getTheme();
	private GridPane board;
	private Action onGameEnd = () -> {
	}; // Placeholder for end-game action

	/**
	 * Constructs a new GameView with a specified game model. Initializes the game
	 * and registers as an observer to the Theme.
	 * 
	 * @param g the game model to associate with this view
	 */
	public GameView(Game g) {
		super();
		game = g;
		Theme.addObserver(this);
		newGame();
	}

	/**
	 * Starts a new game by resetting the game model and updating the GUI layout and
	 * event handlers.
	 */
	public void newGame() {
		game.newGame();
		layoutGUI();
		addEventHandlers();
	}

	/**
	 * Sets the action to be performed when the game ends.
	 * 
	 * @param onAction the action to execute on game completion
	 */
	public void setOnGameEnd(Action onAction) {
		onGameEnd = onAction;
	}

	private void layoutGUI() {
		board = new GridPane();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++)
				board.add(new CardView(game.getCard(i, j)), j, i);
		board.setHgap(5);
		board.setVgap(5);
		this.setCenter(board);
		board.setMinHeight(500);
	}

	private void addEventHandlers() {
		for (Node card : board.getChildren()) {
			card.setOnMouseClicked(e -> {
				game.guessCard(GridPane.getRowIndex((Node) e.getSource()),
						GridPane.getColumnIndex((Node) e.getSource()));
				if (game.isGameOver()) {
					onGameEnd.onAction();
				}
			});
		}
	}

	/**
	 * Updates the view to reflect the current theme settings when a theme change
	 * occurs.
	 */
	@Override
	public void update() {
		theme = Theme.getTheme();
	}

}
