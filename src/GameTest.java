import static org.junit.Assert.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

		private class GameTestable extends Game{
			public GameTestable(int r, int c, int ss, int m, Theme t) {
				super(r, c, ss, m, t);
			}
		}
		private class TestTheme extends Theme{
			@Override 
			public List<String> getImageStrings(int numSets){
				List<String> images = new ArrayList<>();
				for (int i = 0; i < numSets; i++) {
					images.add("Image" + i);
				}
				return images;
			}
		}
		private TestTheme testTheme;
		List<Card> testCards = new ArrayList<>();

		@Before
		public void setUp() {
			testTheme = new TestTheme();
		}
		
		@Test
		public void testGameSetup() {
			Game game = new GameTestable(4, 3, 2, 2, testTheme);
			assertNotNull(game);
			assertEquals(0, game.getNumSetsFound());
	}

		@Test
		public void testGuessCard() {
			Game game = new GameTestable(4, 3, 2, 2, testTheme);
			List<Card> testCards = new ArrayList<>();
			testCards.add(new Card("Image0"));
			testCards.add(new Card("Image1"));
			testCards.add(new Card("Image0"));
			testCards.add(new Card("Image1"));
			game.setCards(testCards);
			assertEquals(Game.state.NOT_ENOUGH_CARDS, game.guessCard(0, 0));
		}
		//Using it as a refernce to test
		/*
		public void setMatchingCards(boolean matching) {
			List<Card> testCards = new ArrayList<>();
			if(matching){
					testCards.add(new Card("match1"));
					testCards.add(new Card("match1"));
			}else {
					testCards.add(new Card("match2"));
					testCards.add(new Card("match3"));
			}
		}
		*/
		@Test
		public void testGuessCard1() {
		    Game game = new GameTestable(4, 3, 2, 2, testTheme);
		    game.setCards(Arrays.asList(new Card("match1"), new Card("match1"), 
		                                 new Card("match2"), new Card("match3"))); 
		    // First match
		    game.guessCard(0, 0); 
		    assertTrue(game.flipCard(0));
		    assertEquals(Game.state.MATCH, game.guessCard(0, 1));
		    assertTrue(game.flipCard(1));

		    // Attempt to guess two new, different cards
		    game.guessCard(0, 2); // This should be guessing "match2"
		    assertTrue(game.flipCard(2));
		    assertEquals(Game.state.NOT_A_MATCH, game.guessCard(0, 3)); // This should be guessing "match3"
		}

		private void assertTrue(Card flipCard) {
			// TODO Auto-generated method stub
			
		}


}