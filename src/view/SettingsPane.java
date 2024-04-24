package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private HBox settingsBar = new HBox();
	private ObservableList<String> themes = FXCollections.observableArrayList();
	private ComboBox<String> themeView = new ComboBox<>();
	private ObservableList<Game.Difficulty> gamemode = FXCollections.observableArrayList();
	private ComboBox<Game.Difficulty> gamemodeView = new ComboBox<>();
	private ObservableList<Tuple> boardSizes = FXCollections.observableArrayList();
	private ComboBox<Tuple> boardSizesView = new ComboBox<>();
	private Button save = new Button("Save Changes");
	private Action onChange = () -> {};
	public void setOnChange(Action onChange) {
		this.onChange = onChange;
	}
	private class Tuple {
		public int rows;
		public int cols;
		public Tuple(int i, int j) {
			// TODO Auto-generated constructor stub
			rows = i;
			cols = j;
		}
		public String toString() {
			return rows + ", " + cols;
		}
		
	}
	
	
	/**
	 * Constructs a new SettingsPane. Initializes the theme selection ListView and
	 * select button, and registers this pane as an observer of the Theme model.
	 */
	public SettingsPane(double height, double width, Settings s) {
		super();
		settings = s;
		Theme.addObserver(this);
		setHeight(height);
		setWidth(width);
		layoutGUI();

		save.setOnAction(e-> {
			settings.setPrefferedTheme(themeView.getSelectionModel().getSelectedItem());
			settings.setDifficulty(gamemodeView.getSelectionModel().getSelectedItem());
			Tuple dimensions = boardSizesView.getSelectionModel().getSelectedItem();
			settings.setDimension(dimensions.rows, dimensions.cols);
			onChange.onAction();
		});
		
	}

	private void layoutGUI() {
		themes.addAll(Theme.getThemes());
		themeView.setItems(themes);
		themeView.setValue(settings.getPrefferedTheme());
		gamemode.addAll(Game.Difficulty.values());
		gamemodeView.setItems(gamemode);
		gamemodeView.setValue(settings.getDifficulty());
		//TODO I don't like the list of board sizes being hard coded into the view
		// (hard to find if trying to use elsewhere)
		boardSizes.addAll(new Tuple(2, 3), new Tuple(3, 4));
		boardSizesView.setItems(boardSizes);
		boardSizesView.setValue(new Tuple(settings.getRows(), settings.getColumns()));
		settingsBar.getChildren().addAll(themeView, gamemodeView, boardSizesView);
		setCenter(settingsBar);
		setBottom(save);
		update();
		
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
		themeView.setValue(settings.getPrefferedTheme());
		Image image = Theme.getTheme().getBackground();
		this.setStyle("-fx-background-image: url('" + image.getUrl() + "');" + "-fx-background-size: cover;");

	}

}
