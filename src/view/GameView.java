package view;

import interfaces.Action;
import interfaces.Observer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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
import model.game.Game;
import model.game.Theme;

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
		
		onGameEnd = () -> {
			// Wait for all of the animations to finish
			for(Node e : board.getChildren()) {
				CardView c = (CardView) e;
				//TODO I sort of hate everything about these changes
				if(c.isMidAnimation()) {
					Platform.runLater(() -> onGameEnd.onAction());
					return;
				}
			}
			onAction.onAction();
			
			};
	}

	private void layoutGUI() {
		board = new GridPane();
		board.setAlignment(Pos.CENTER);
		System.out.println(game.getCols());
		System.out.println(game.getRows());
		for (int row = 0; row < game.getRows(); row++)
			for (int col = 0; col < game.getCols(); col++)
				board.add(new CardView(game.getCard(row, col)), col, row);
		board.setHgap(10);
		board.setVgap(10);
		this.setCenter(board);
		timerLabel = new Label("00:00");
		timerLabel.setTextFill(Color.WHITE); 
		timerLabel.setFont(Font.font("Arial", 24));
		setTop(timerLabel);
		board.setMinHeight(500);
		startTimer();
		Thread timerThread = new Thread(()->{
		while (true) {
			Platform.runLater(()->updateTimer());
		try {
			Thread.sleep(200);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}});
		timerThread.start();
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

	
	public Double getEndTime() {
		Long now = System.nanoTime();
		Long elapsedTimeNs = now - startTime;
        Double elapsedSeconds = elapsedTimeNs / 1_000_000_000.0;

        return elapsedSeconds;
	}
	
	public Integer getGuesses() {
		return game.totalGuesses();
	}
	
	public Integer getNumCards() {
		return game.numCards();
	}
	
	public Game.Difficulty getDifficulty(){
		return game.getDifficulty();
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
	}

}
