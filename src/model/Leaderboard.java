package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Leaderboard {
    private final SimpleStringProperty playerName;
    private final SimpleIntegerProperty score;

    public Leaderboard(String playerName, int score) {
        this.playerName = new SimpleStringProperty(playerName);
        this.score = new SimpleIntegerProperty(score);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public Integer getScore() {
        return score.get();
    }

    public void setScore(Integer score) {
        this.score.set(score);
    }

    public SimpleStringProperty playerNameProperty() {
        return playerName;
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}