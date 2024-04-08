/*
 * This is the card class, which holds information for the GUI
 */
package model;

public class Card {
	private String img;
	private boolean flipped;
	
	public Card(String i) {
		img = i;
		flipped = false;
	}
	
	public String getImage() {
		return img;
	}
	
	public boolean isFlipped() {
		return flipped;
	}
	
	public void flipCard() {
		flipped = !flipped;
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
}
	
