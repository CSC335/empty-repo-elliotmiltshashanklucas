package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import model.Theme;
/**
 * SettingsPane is a BorderPane that allows users to select and apply themes from a list.
 * It updates its display when the list of themes changes.
 */
public class SettingsPane extends BorderPane implements Observer {
	private ObservableList<String> themes = FXCollections.observableArrayList();
	private ListView<String> themeView = new ListView<>();
	private Button selectTheme = new Button("Select Theme");
    /**
     * Constructs a new SettingsPane.
     * Initializes the theme selection ListView and select button, and registers this pane as an observer of the Theme model.
     */
	public SettingsPane() {
		super();
		Theme.addObserver(this);
		themes.addAll(Theme.getThemes());
		themeView.setItems(themes);
		setCenter(themeView);
		setBottom(selectTheme);
		selectTheme.setOnAction(e -> Theme.setTheme(themeView.getSelectionModel().getSelectedItem()));
	}
    /**
     * Updates the list of themes in the ListView when notified by the observed object.
     */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		themes.clear();
		themes.addAll(Theme.getThemes());
		
	}
	
}
