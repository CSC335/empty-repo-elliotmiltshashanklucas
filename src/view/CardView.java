package view;

import interfaces.Observer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import model.game.Card;
import model.game.Theme;

/**
 * CardView is an ImageView representing a card in a game, capable of displaying
 * different images based on the card's state and animating flips between these
 * states. It observes changes in the card and theme models to update its
 * appearance.
 */
public class CardView extends ImageView implements Observer {
	private Card card;
	private Theme theme = Theme.getTheme();
	private boolean midAnimation = false;
	public boolean isMidAnimation() {
		return midAnimation;
	}

	/**
	 * Constructs a new CardView with a specified card. Sets up the image view
	 * properties, registers as an observer to the card and theme, and initializes
	 * the card image based on its current state.
	 * 
	 * @param c the card to be displayed and observed
	 */
	public CardView(Card c) {
		card = c;
		if(c != null)
			c.addListener(this);
		this.setFitHeight(140);
		this.setFitWidth(120);
		updateImage(isFlipped());
		Theme.addObserver(this);
	}

	/**
	 * Creates a flip animation for the card view.
	 * 
	 * @param angle the angle to end the rotation at
	 * @return a configured RotateTransition object
	 */
	private RotateTransition createFlipAnimation(int angle) {
		RotateTransition rotator = new RotateTransition(Duration.millis(250));
		rotator.setNode(this);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(angle);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);
		return rotator;
	}

	/**
	 * Updates the image displayed by the ImageView based on whether the card is
	 * flipped.
	 * 
	 * @param isFlipped true if the card is flipped (showing its face), false if
	 *                  showing its back
	 */
	protected void updateImage(boolean isFlipped) {
		if (isFlipped)
			this.setImage(new Image(card.getImage()));
		else
			this.setImage(theme.getCardBack());
	}
	// hack to allow for subclassing wtihout providing a card
	protected boolean isFlipped() {
		return card.isFlipped();
	}
	/**
	 * Called when an observed object changes. This method handles flipping
	 * animations and updates the card's image according to the new state.
	 */
	public void update() {
		var flipped = isFlipped();
		theme = Theme.getTheme();
		midAnimation = true;
		var rotator = createFlipAnimation(90);
		rotator.setOnFinished(e -> {
			updateImage(flipped);
			this.setRotate(90);
			var otherFlip = createFlipAnimation(0);
			otherFlip.setOnFinished(p -> midAnimation = false);
			otherFlip.play();
			
		});
		if (!isFlipped())
			rotator.setDelay(Duration.millis(1000));
		rotator.play();

	}

}
