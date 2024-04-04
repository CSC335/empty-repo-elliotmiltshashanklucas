/*
 * This is the card class, which holds information for the GUI
 */

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
		if(o.getClass() != this.getClass()) {
			return false;
		} else {
			return ((Card)o).getImage() == img;
		}
	}
}