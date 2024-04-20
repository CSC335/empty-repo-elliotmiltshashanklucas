package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account implements Serializable{
	private static final long serialVersionUID = 5305138822007823784L;
    private String userName;
    private String password;
    private Settings prefferedSettings;
    private Map<Game.Difficulty, List<GameStats>> gameHistory;
    
    public Settings getPrefferedSettings() {
		return prefferedSettings;
	}

	public Account(String userName, String password, Settings settings) {
        this.userName = userName;
        this.password = password;
        prefferedSettings = settings;
    }
    
    public void setPrefferedSettings(Settings s) {
    	prefferedSettings = s;
    }

    public String getUserName() {
        return userName;
    }
    public boolean correctPassword(String inputPassword) {
    	return (this.password.equals(inputPassword));
    }
    
    public Map<Game.Difficulty, List<GameStats>> getHistory(){
    	return this.gameHistory;
    }
    
    /*
     * For testing only
     */
    
    public void makeHistory() {
    	gameHistory = new HashMap<>();
    }
    public void addGameData(int numGuesses, double numSeconds, 
    		Game.Difficulty difficulty, int numberOfCards ) 
    {
    	if(!gameHistory.containsKey(difficulty)) {
    		gameHistory.put(difficulty, new ArrayList<GameStats>());
    	}
    	GameStats g = new GameStats(numGuesses, numSeconds, difficulty, numberOfCards);
    	gameHistory.get(difficulty).add(g);
    }
    
}



