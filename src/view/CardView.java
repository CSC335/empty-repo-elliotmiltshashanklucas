package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Card;
import model.Theme;

public class CardView extends ImageView {
	private Card card;
	public CardView(Card c, Theme t) {
		card = c;
		c.addListener(this);
		this.setFitHeight(100);
		this.setFitWidth(100);
		update();
			
	}
	public void update() {
		// TODO Auto-generated method stub
		this.setImage(new Image(card.getImage()));
	}
	
}
