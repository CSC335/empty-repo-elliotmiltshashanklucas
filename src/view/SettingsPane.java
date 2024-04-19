package view;

import java.util.ArrayList;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Game;
import model.Settings;
import model.Theme;

/**
 * SettingsPane is a BorderPane that allows users to select and apply themes
 * from a list. It updates its display when the list of themes changes.
 */
public class SettingsPane extends BorderPane implements Observer {
	private Settings settings;
	private VBox themeElement = new VBox();
	private ObservableList<String> themes = FXCollections.observableArrayList();
	private ListView<String> themeView = new ListView<>();
	private Button selectTheme = new Button("Select Theme");
	private VBox selectGameModeStackPane = new VBox();
	private ObservableList<Game.Difficulty> gamemode = FXCollections.observableArrayList();
	private ListView<Game.Difficulty> gamemodeView = new ListView<>();
	private Button selectGamemode = new Button("Select Game Mode");
	/**
	 * Constructs a new SettingsPane. Initializes the theme selection ListView and
	 * select button, and registers this pane as an observer of the Theme model.
	 */
	public SettingsPane(double height, double width, Settings s) {
		super();
		Theme.addObserver(this);
		setHeight(height);
		setWidth(width);
		layoutGUI();
		selectTheme.setOnAction(e -> Theme.setTheme(themeView.getSelectionModel().getSelectedItem()));
		
	}

	private void layoutGUI() {
		themes.addAll(Theme.getThemes());
		themeView.setItems(themes);
		themeView.setMaxHeight(getHeight()/2);
		themeElement.getChildren().addAll(themeView, selectTheme);
		gamemode.addAll(Game.Difficulty.values());
		gamemodeView.setItems(gamemode);
		gamemodeView.setMaxHeight(getHeight()/2);
		selectGameModeStackPane.getChildren().addAll(gamemodeView, selectGamemode);
		setCenter(themeElement);
		setRight(selectGameModeStackPane);
	}

	/**
	 * Updates the list of themes in the ListView when notified by the observed
	 * object.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		themes.clear();
		themes.addAll(Theme.getThemes());

	}

}
