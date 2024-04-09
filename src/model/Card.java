/*
 * This is the card class, which holds information for the GUI
 */
package model;

import javafx.scene.image.Image;
import view.CardView;

public class Card {
	private String img;
	private boolean flipped;
	Theme t = Theme.getTheme();
	private CardView observor;
	
	public Card(String i, Image background) {
		img = i;
		flipped = false;
	}
	
	public String getImage() {
		if(isFlipped())
			return img;
		System.out.println(t.getCardBack().getUrl());
		System.out.println(t.getName());
		return t.getCardBack().getUrl();
	}
	
	public boolean isFlipped() {
		return flipped;
	}
	
	public void flipCard() {
		flipped = !flipped;
		notifyListener();
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) {
	        return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	        return false;
	    }
	    Card card = (Card) o;
	    if (img == null) {
	        return card.img == null;
	    } else {
	        return img.equals(card.img);
	    }
	}

	public void addListener(CardView cardView) {
		// TODO Auto-generated method stub
		this.observor = cardView;
	}
	private void notifyListener() {
		if(observor != null)
			observor.update();
	}


}
	
