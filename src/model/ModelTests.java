package model;

import org.junit.Test;

import static org.junit.Assert.*;

import model.game.*;
import model.account.*;

/**
 * JUnit tests for the model classes
 */
public class ModelTests {
	
	/**
	 * Tests the card class
	 */
	@Test
	public void testCard() {
		String img1 = "earth";
		String img2 = "mars";
		Card c1 = new Card(img1);
		Card c2 = new Card(img1);
		Card c3 = new Card(img2);
		Card c4 = new Card(img2);

		assertEquals(c1.getImage(),img1);
		assertFalse(c1.isFlipped());
		assertEquals(c1,c2);
		assertEquals(c3,c4);
		assertNotEquals(c1,c4);
		c1.flipCard();
		assertTrue(c1.isFlipped());
		assertEquals(c1,c2);
		c1.flipCard();
		assertFalse(c1.isFlipped());
	}
	
	/**
	 * Tests the Settings class
	 */
	@Test
	public void testSettings() {
		Settings s = new Settings(Game.Difficulty.EASY, 40, 4, "Cats");
		assertEquals(s.getDifficulty(), Game.Difficulty.EASY);
		assertEquals(s.getRows(), 40);
		assertEquals(s.getColumns(), 4);
		assertEquals(s.getPrefferedTheme(), "Cats");
		
		Settings s2 = new Settings();
		assertEquals(s2.getDifficulty(), Game.Difficulty.MEDIUM);
		assertEquals(s2.getColumns(), Game.DEFAULT_COLS);
		assertEquals(s2.getRows(), Game.DEFAULT_ROWS);
		assertEquals(s2.getPrefferedTheme(), Theme.getTheme().getName());
		
		s2.setDifficulty(Game.Difficulty.HARD);
		assertEquals(s2.getDifficulty(), Game.Difficulty.HARD);
		s2.setDimension(40, 2);
		assertEquals(s2.getRows(), s2.getRows());
		assertEquals(s2.getColumns(), 2);
		s2.setPrefferedTheme("Space");
		assertEquals(s2.getPrefferedTheme(), "Space");
	}
	
	/**
	 * Tests the Game class
	 */
	@Test
	public void testGame() {
		Settings s = new Settings(Game.Difficulty.MEDIUM, 3, 4, "Space");
		Game g = Game.makeGame(s);
		
		g.newGame();
		assertFalse(g.isGameOver());
		assertEquals(g.totalGuesses(), 0);
		assertEquals(g.getDifficulty(), s.getDifficulty());
		assertEquals(g.getNumSetsFound(), 0);
		assertEquals(g.numCards(), s.getColumns() * s.getRows());

		assertEquals(g.guessCard(0, 0), Game.state.NOT_ENOUGH_CARDS);
		assertEquals(g.guessCard(0, 0), Game.state.ALREADY_FACE_UP);
		assertEquals(g.totalGuesses(), 1);
		if(g.getCard(0, 0).equals(g.getCard(1,1))) {
			assertEquals(g.guessCard(1,1), Game.state.MATCH);
		} else {
			assertEquals(g.guessCard(1,1), Game.state.NOT_A_MATCH);
		}
	
		g.newTestGame();
		assertFalse(g.isGameOver());
		assertEquals(g.totalGuesses(), 0);
		assertEquals(g.getNumSetsFound(), 0);
		for(int r = 0; r < s.getRows()-1; r++) {
			for(int c = 0; c < s.getColumns(); c++) {
				g.guessCard(r, c);
			}
		}
		assertFalse(g.isGameOver());
		assertEquals(g.guessCard(s.getRows()-1, 0), Game.state.NOT_ENOUGH_CARDS);
		assertEquals(g.guessCard(s.getRows()-1, 2), Game.state.NOT_A_MATCH);
		assertEquals(g.guessCard(s.getRows()-1, 0), Game.state.NOT_ENOUGH_CARDS);
		assertEquals(g.guessCard(s.getRows()-1, 1), Game.state.MATCH);
		assertEquals(g.guessCard(s.getRows()-1, 2), Game.state.NOT_ENOUGH_CARDS);
		assertEquals(g.guessCard(s.getRows()-1, 2), Game.state.ALREADY_FACE_UP);
		assertEquals(g.guessCard(s.getRows()-1, 3), Game.state.END_OF_GAME);
		
		assertTrue(g.isGameOver());
		assertEquals(g.getNumSetsFound(), s.getColumns() * s.getRows() / 2);
		assertEquals(g.totalGuesses(), s.getColumns() * s.getRows() + 2);	
	}
	
	/**
	 * Tests the Account class
	 */
	@Test
	public void testAccount() {
		Settings s = new Settings();
		Account alice = new Account("alice", "bestpasswordever1234321", s);
		Account bob = new Account("bob", "123", s);

		assertEquals(alice.getPrefferedSettings().getDifficulty(), new Settings().getDifficulty());
		assertEquals(alice.getPrefferedSettings().getPrefferedTheme(), new Settings().getPrefferedTheme());
		alice.addGameData(10, 100, Game.Difficulty.EASY, 8);
		alice.addGameData(12, 20, Game.Difficulty.EASY, 8);
		assertTrue(alice.getStats(Game.Difficulty.EASY).getAverageGuesses() == 11);
		assertTrue(alice.getStats(Game.Difficulty.EASY).getBestGuesses() == 10);
		assertTrue(alice.getStats(Game.Difficulty.EASY).getAverageTime() == 60);
		assertTrue(alice.getStats(Game.Difficulty.EASY).getBestTime() == 20);
		assertTrue(bob.getUserName() == "bob");
	}
	
	/**
	 * Tests the AccountManager class
	 */
	@Test
	public void testAccountManager() {
		AccountManager am = new AccountManager();
		assertTrue(am.createAccount("bob", "123"));
		assertTrue(am.createAccount("alice", "bestpasswordever1234321"));
		assertFalse(am.createAccount("bob", "123123123123123123"));
		assertTrue(am.getAccounts().size() == 2);

		assertFalse(am.userIsLoggedIn());
		assertFalse(am.login("bob", "1123"));
		assertTrue(am.login("bob", "123"));
		assertTrue(am.userIsLoggedIn());
		assertTrue(am.getLoggedInAccount().getUserName() == "bob");
		am.loggedOut();
		assertFalse(am.userIsLoggedIn());
		assertTrue(am.login("alice", "bestpasswordever1234321"));
	}

}
