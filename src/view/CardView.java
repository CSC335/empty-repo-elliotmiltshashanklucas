package view;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.Card;
import model.Theme;

public class CardView extends ImageView implements Observer{
	private Card card;
	private Theme theme = Theme.getTheme();
	public CardView(Card c) {
		card = c;
		c.addListener(this);
		this.setFitHeight(100);
		this.setFitWidth(100);
		updateImage(card.isFlipped());
		Theme.addObserver(this);
	}
	private RotateTransition createFlipAnimation(int angle) {
		RotateTransition rotator = new RotateTransition(Duration.millis(500));
		rotator.setNode(this);
	    rotator.setAxis(Rotate.Y_AXIS);
	    rotator.setFromAngle(0);
	    rotator.setToAngle(angle);
	    rotator.setInterpolator(Interpolator.LINEAR);
	    rotator.setCycleCount(1);
	    return rotator;
	}
	private void updateImage(boolean isFlipped) {
		if(isFlipped)
			this.setImage(new Image(card.getImage()));
		else 
			this.setImage(theme.getCardBack());
	}
	public void update() {
		var flipped = card.isFlipped();
		theme = Theme.getTheme();
		var rotator = createFlipAnimation(90);
		rotator.setOnFinished(e -> {
			updateImage(flipped);
			this.setRotate(90);
			var otherFlip = createFlipAnimation(0);
			otherFlip.play();
		});
		if(!card.isFlipped())
			rotator.setDelay(Duration.millis(1000));
		rotator.play();

	}
	
}
