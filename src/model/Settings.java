package model;

import java.io.Serializable;

public class Settings implements Serializable {
	private static final long serialVersionUID = 1L;
	private Game.Difficulty difficulty = Game.Difficulty.MEDIUM;
	private int rows = Game.DEFAULT_ROWS;
	private int columns = Game.DEFAULT_COLS;
	@SuppressWarnings("unused")
	private String prefferedTheme = Theme.getTheme().getName(); 
	
	public Settings() {}
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
