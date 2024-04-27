package model.account;

import java.io.Serializable;

import model.game.Game;
import model.game.Theme;
import model.game.Game.Difficulty;

public class Settings implements Serializable {
	private static final long serialVersionUID = 1L;
	private Game.Difficulty difficulty = Game.Difficulty.MEDIUM;
	private int rows = Game.DEFAULT_ROWS;
	private int columns = Game.DEFAULT_COLS;
	private String prefferedTheme = Theme.getTheme().getName(); 
	
	public String getPrefferedTheme() {
		return prefferedTheme;
	}

	public Settings(Difficulty difficulty, int rows, int columns, String prefferedTheme) {
		super();
		this.difficulty = difficulty;
		this.rows = rows;
		this.columns = columns;
		this.prefferedTheme = prefferedTheme;
	}
	
	public Settings() {
		super();
	}
	
	public void setDimension(int rows, int cols) {
		this.rows = rows;
		columns = cols;
	}
	
	public Game.Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Game.Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}
	public void setPrefferedTheme(String theme) {
		prefferedTheme = theme;
		Theme.setTheme(theme);
	}
}
