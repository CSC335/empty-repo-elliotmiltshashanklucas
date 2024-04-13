package view;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

/**
 * A custom login screen layout for managing user authentication and account creation.
 * @author Lucas Liang
 */
public class LoginScreen extends BorderPane {

	private Label accountNameLabel;
	private Label passwordLabel;

	private TextField accountName;
	private TextField password;

	private Button login;
	private Label status;
	private Button logout;

	private HBox usernameDetails;
	private HBox passwordDetails;
	private VBox loginPanel;
	private VBox loginStatus;
	
	private Account account;

	private Button makeAccount;

	private String curPW = "";

	private Alert alert;

	public LoginScreen(double d, double e) {
		this.setWidth(d);
		this.setHeight(e);
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
		this.setCenter(loginPanel);

	}

    /**
     * Adds event handlers for login, password typing, and logout actions.
     *
     * @param account The Account object associated with the login screen.
     * @return none
     */
	public void addEventHandlers(Account account) {
		login.setOnAction(e -> {
			if (account.activeAccount() != null) {
				alert.setHeaderText("Please Logout First");
				alert.showAndWait();
			} else if (!account.login(accountName.getText(), curPW)) {
				alert.setHeaderText("Invalid Login - Please Try Again");
				alert.showAndWait();
			} 
			else {
				accountName.setText("");
				password.setText("");
				curPW = "";
			}
		});

		password.setOnKeyTyped(e -> {
			String in = password.getText();
			if (in.length() > curPW.length()) {
				curPW += in.substring(curPW.length());
				password.replaceText(in.length() - 1, in.length(), "\u2022");
			} else {
				curPW = curPW.substring(0, in.length());
			}
		});

		password.setOnAction(e -> {
			if (account.activeAccount() != null) {
				alert.setHeaderText("Please Logout First");
				alert.showAndWait();
			} else if (!account.login(accountName.getText(), curPW)) {
				alert.setHeaderText("Invalid Login - Please Try Again");
				alert.showAndWait();
			} else {
				accountName.setText("");
				password.setText("");
				curPW = "";
			}
		});

		logout.setOnAction(e -> {
			account.logout();
			status.setText("Login first");
			accountName.setText("");
			password.setText("");
			curPW = "";
		});
		
}
}
