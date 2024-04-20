package view;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Game;
import model.Theme;

/**
 * GameView is a BorderPane that displays a game board and handles game
 * interactions, including theme changes and game restarts. It observes changes
 * in the Theme model.
 */
public class GameView extends BorderPane implements Observer {
	private Game game;
	private Theme theme;
	private GridPane board;
    private long startTime;
    private Label timerLabel;
    private AnimationTimer timer; 
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
		Theme.addObserver(this);
		newGame(g);
		update();
	}

	/**
	 * Starts a new game by resetting the game model and updating the GUI layout and
	 * event handlers.
	 */
	public void newGame(Game newGame) {
		game = newGame;
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
		board.setAlignment(Pos.CENTER);
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 4; j++)
				board.add(new CardView(game.getCard(i, j)), j, i);
		board.setHgap(10);
		board.setVgap(10);
		this.setCenter(board);
		timerLabel = new Label("00:00");
		timerLabel.setTextFill(Color.BLACK); 
		timerLabel.setFont(Font.font("Arial", 24));
		board.add(timerLabel, 4, 4);
		board.setMinHeight(500);
		startTimer();
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
	
	private void startTimer() {
        startTime = System.nanoTime();
    
	}

	private void updateTimer() {
            long now = System.nanoTime();
                long elapsedTimeNs = now - startTime;
                long elapsedSeconds = elapsedTimeNs / 1_000_000_000;

                long minutes = elapsedSeconds / 60;
                long seconds = elapsedSeconds % 60;

                String timeString = String.format("%02d:%02d", minutes, seconds);

                timerLabel.setText(timeString);
	}

	/**
	 * Updates the view to reflect the current theme settings when a theme change
	 * occurs.
	 */
	@Override
	public void update() {
		theme = Theme.getTheme();
		Image image = theme.getBackground();
		this.setStyle("-fx-background-image: url('" + image.getUrl() + "');" + "-fx-background-size: cover;");
		updateTimer();
	}

}
