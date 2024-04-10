import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginScreen extends BorderPane{
	
	private Label accountNameLabel;
	private Label passwordLabel;

	private TextField accountName;
	private TextField password;

	private Button login;
	private Label status;
	private Button logout;

	private HBox songs;
	private HBox usernameDetails;
	private HBox passwordDetails;
	private VBox loginPanel;
	private VBox loginStatus;
	
	private Button makeAccount;

	private String curPW = "";

	private Alert alert;
	LoginScreen(){
		alert = new Alert(AlertType.INFORMATION);

		accountNameLabel = new Label("Account Name");
		// make the labels even length
		passwordLabel = new Label("Password         ");

		accountName = new TextField();
		password = new TextField();

		usernameDetails = new HBox();
		login = new Button("Login");
		usernameDetails.getChildren().addAll(accountNameLabel, accountName, login);
		usernameDetails.setSpacing(10);
		passwordDetails = new HBox();
		logout = new Button("Logout");
		passwordDetails.getChildren().addAll(passwordLabel, password, logout);
		passwordDetails.setSpacing(10);

		loginPanel = new VBox();
		status = new Label("Login first");
		makeAccount = new Button("Create new Account");
		loginPanel.getChildren().addAll(status, usernameDetails, passwordDetails, makeAccount);
		usernameDetails.setPadding(new Insets(0,0,0,80));
		passwordDetails.setPadding(new Insets(0,0,0,80));
		loginPanel.setSpacing(10); 
		loginPanel.setAlignment(Pos.CENTER);
	}

}
