import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game{
	
	private List<Card> cards = new ArrayList<>();
	private int score = 0;
	
	public void setupGame(int pairCount){
		List<String> cardIdentifiers = getCardIdentifiers(pairCount);
		for (String id : cardIdentifiers) {
			cards.add(new Card(id));
			cards.add(new Card(id));
		}
		Collections.shuffle(cards);
	}
	private List<String> getCardIdentifiers(int pairCount){
		List<String> identifiers = new ArrayList<>();
		for (int i = 1; i <= pairCount; i++) {
			identifiers.add(String.valueOf(i));
		}
		return identifiers;
	}
	
	public Card flipCard(int cardIndex) {
		if(cardIndex >= 0 && cardIndex < cards.size()) {
			Card selectedCard = cards.get(cardIndex);
			selectedCard.flipCard();
			return selectedCard;
		}else {
			System.out.println("Invalid Card Size");
			return null;
		}
	}
	
	public boolean checkifMatch(Card cardMatch) {
		for (Card card : cards) {
			if(card.isFlipped() && !card.equals(cardMatch) && card.getImage().equals(cardMatch.getImage())) {
				score++;
				return true;
			}
		}
		return false;
	}
	
	public boolean isGameOver() {
		for (Card card: cards) {
			if(!card.isFlipped()) {
				return false;
			}
		}
		return true;
	}
	public int updateScore() {
		return score;
	}
}