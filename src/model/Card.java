package model;

import view.CardView;

/**
 * This is the card class, which holds information for the GUI
 */
public class Card {
	private String img;
	private boolean flipped;
	Theme t = Theme.getTheme();
	private CardView observor;	
	/**
     * Constructs a new Card object with the specified image.
     *
     * @param i - the image of the card
     */
	public Card(String i) {
		img = i;
		flipped = false;
	}
	
	/**
     * Gets the image of the card.
     *
     * @return img - the card's image
     */
	public String getImage() {
		return img;
	}
	
	/**
     * Checks if the card is flipped.
     *
     * @return true if the card is flipped, false otherwise
     */
	public boolean isFlipped() {
		return flipped;
	}
	
	/**
     * Flips the card.
     * Notifies the cardview observer after flipping the card.
     */

	public void flipCard() {
		flipped = !flipped;
		notifyListener();
	}
	
	/**
     * Compares this Card object to the specified object. The result is true if and only if
     * the argument is not null and is a Card object with the same image as this object.
     *
     * @param o - the object to compare this Card against
     * @return true if the given object represents a Card equivalent to this card, false otherwise
     */
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

	/**
     * Adds a CardView observer to this card.
     *
     * @param cardView - the observer to add
     */
	public void addListener(CardView cardView) {
		// TODO Auto-generated method stub
		this.observor = cardView;
	}

	
	/**
     * Notifies the observer that the card has been flipped.
     */
	private void notifyListener() {
		if (observor != null)
			observor.update();
	}

}
