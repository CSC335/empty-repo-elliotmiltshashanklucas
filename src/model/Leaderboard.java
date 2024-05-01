package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Class representing a leaderboard entry with player name and score properties.
 */
public class Leaderboard {
    private final SimpleStringProperty playerName;  // Property for player's name to support data binding
    private final SimpleIntegerProperty score;      // Property for player's score to support data binding

    /**
     * Constructs a new leaderboard entry with specified player name and score.
     * 
     * @param playerName the name of the player
     * @param score the score of the player
     */
    public Leaderboard(String playerName, int score) {
        this.playerName = new SimpleStringProperty(playerName);
        this.score = new SimpleIntegerProperty(score);
    }

    /**
     * Gets the player's name.
     * 
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName.get();
    }

    /**
     * Sets the player's name.
     * 
     * @param playerName the new name to set
     */
    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    /**
     * Gets the player's score.
     * 
     * @return the score of the player
     */
    public Integer getScore() {
        return score.get();
    }

    /**
     * Sets the player's score.
     * 
     * @param score the new score to set
     */
    public void setScore(Integer score) {
        this.score.set(score);
    }

    /**
     * Provides a property wrapper for the player's name. This is used for data binding in JavaFX.
     * 
     * @return the property for the player's name
     */
    public SimpleStringProperty playerNameProperty() {
        return playerName;
    }

    /**
     * Provides a property wrapper for the player's score. This is used for data binding in JavaFX.
     * 
     * @return the property for the player's score
     */
    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
