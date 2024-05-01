package model.account;

import java.io.Serializable;

import model.game.Game;
import model.game.Game.Difficulty;
import model.game.Theme;

public class Settings implements Serializable {
	private static final long serialVersionUID = 1L;
	private Game.Difficulty difficulty = Game.Difficulty.MEDIUM;
	private int rows = Game.DEFAULT_ROWS;
	private int columns = Game.DEFAULT_COLS;
	private String prefferedTheme = Theme.getTheme().getName();

	public Settings() {
		super();
	}

	public Settings(Difficulty difficulty, int rows, int columns, String prefferedTheme) {
		super();
		this.difficulty = difficulty;
		this.rows = rows;
		this.columns = columns;
		this.prefferedTheme = prefferedTheme;
	}

	public int getColumns() {
		return columns;
	}

	public Game.Difficulty getDifficulty() {
		return difficulty;
	}

	public String getPrefferedTheme() {
		return prefferedTheme;
	}

	public int getRows() {
		return rows;
	}

	public void setDifficulty(Game.Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setDimension(int rows, int cols) {
		this.rows = rows;
		columns = cols;
	}

	public void setPrefferedTheme(String theme) {
		prefferedTheme = theme;
		Theme.setTheme(theme);
	}
}
