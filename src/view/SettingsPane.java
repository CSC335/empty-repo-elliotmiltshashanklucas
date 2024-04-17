package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import model.Theme;

public class SettingsPane extends BorderPane implements Observer {
	private ObservableList<String> themes = FXCollections.observableArrayList();
	private ListView<String> themeView = new ListView<>();
	private Button selectTheme = new Button("Select Theme");
	public SettingsPane() {
		super();
		Theme.addObserver(this);
		themes.addAll(Theme.getThemes());
		themeView.setItems(themes);
		setCenter(themeView);
		setBottom(selectTheme);
		selectTheme.setOnAction(e -> Theme.setTheme(themeView.getSelectionModel().getSelectedItem()));
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		themes.clear();
		themes.addAll(Theme.getThemes());
		
	}
	
}
