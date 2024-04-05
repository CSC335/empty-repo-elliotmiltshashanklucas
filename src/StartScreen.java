import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class StartScreen extends StackPane{
	
	private Image backgroundImage;
	private Button playGame;
	private Canvas canvas;
	
	public StartScreen () {
		canvas = new Canvas();
		playGame = new Button("Play Game");
		this.getChildren().addAll(playGame, canvas);
		canvas.widthProperty().bind(this.widthProperty());
		canvas.heightProperty().bind(this.heightProperty());
		backgroundImage = new Image("file:images/backgroundImg.png");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawImage(gc);
	}
	
	public void setEventHandlers() {
		
	}
	
	private void drawImage(GraphicsContext g) {
		int mid1 = this.widthProperty().intValue();
		int mid2 = this.heightProperty().intValue();
		
		g.drawImage(backgroundImage, 0,mid1,0,mid2);
		
	}
}