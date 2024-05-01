package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Optional;

import interfaces.Action;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.account.Account;
import model.account.AccountManager;


public class LoginScreen extends BorderPane {
    // Declare UI components and some utility fields
	private TextField usernameField = new TextField();
	private PasswordField passwordField = new PasswordField();
	private Button loginButton = new Button("Login");
	private Label prompt = new Label("Login first");
	private Button logoutButton = new Button("Logout");
	private Label accountNameLabel = new Label("Account Name");
	private Label passwordLabel = new Label("Password");
	private Button createNewAccount = new Button("Create new Account");
	private HBox usernameDetails;
	private HBox passwordDetails;
	private HBox createLogin;
	private VBox loginPanel;
	private AccountManager accounts;
	private Action onLogin = () -> {};
	private String STATE_FILE = "state.ser";

	// Constructor
	public LoginScreen(AccountManager a, Stage stage) {
		accounts = a;
		layoutGUI(650, 560);
		setEventHandlers();
		promptLoadState();
		setUpCloseRequestHandler(stage);
	}
	// Set up the user interface
	private void layoutGUI(double d, double e) {
		this.setWidth(d);
		this.setHeight(e);
		usernameField.setPromptText("Enter your username");
		passwordField.setPromptText("Enter your password");
		
		// Organizing username and password fields along with labels
		usernameDetails = new HBox();
		usernameDetails.getChildren().addAll(accountNameLabel, usernameField);
		usernameDetails.setSpacing(10);
		passwordDetails = new HBox();
		passwordDetails.getChildren().addAll(passwordLabel, passwordField);
		passwordDetails.setSpacing(10);
		createLogin = new HBox();
		createLogin.getChildren().addAll(createNewAccount, loginButton);
		createLogin.setSpacing(10);
		loginPanel = new VBox();
		loginPanel.getChildren().addAll(prompt, usernameDetails, passwordDetails, createLogin);
		
		// Styling and alignment
		usernameDetails.setPadding(new Insets(0, 0, 0, 300));
		passwordDetails.setPadding(new Insets(0, 0, 0, 327));
		createLogin.setPadding(new Insets(0, 0, 0, 395));
		loginPanel.setSpacing(10);
		loginPanel.setAlignment(Pos.CENTER);
		this.setCenter(loginPanel);
	    this.setStyle("-fx-background-color: #f0f4f7;"); 
	    
	 // Adding CSS classes to buttons
	    loginButton.getStyleClass().add("login-button");
	    createNewAccount.getStyleClass().add("create-account-button");

	}

	/**
	 * Adds event handlers for login, password typing, and logout actions.
	 *
	 * @param account The Account object associated with the login screen.
	 * @return none
	 */


	private void promptLoadState() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText("Load Saved State");
		alert.setContentText("Click OK read from a .ser file");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			readState();
		}
	}

	private void setUpCloseRequestHandler(Stage stage) {
		stage.setOnCloseRequest(event -> {
			event.consume();

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setHeaderText("Save Changes");
			alert.setContentText("Click OK to save current state before exiting");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isPresent() && result.get() == ButtonType.OK) {
				writeState();
			}
			Platform.exit();
			System.exit(0);
		});
	}

	private void setEventHandlers() {
		loginButton.setOnAction(e -> {
			String user = getUsername();
			String pass = getPassword();
			if (user.isEmpty() || pass.isEmpty()) {
				setPrompt("Username and password cannot be empty.");
				return;
			}
			if (accounts.login(user, pass)) {
				setPrompt("Logged in successfully.");
				onLogin.onAction();
			} else {
				setPrompt("Invalid Credentials");
			}
		});
		
		passwordField.setOnAction(e -> {
			String user = getUsername();
			String pass = getPassword();
			if (user.isEmpty() || pass.isEmpty()) {
				setPrompt("Username and password cannot be empty.");
				return;
			}
			if (accounts.login(user, pass)) {
				setPrompt("Logged in successfully.");
				onLogin.onAction();
			} else {
				setPrompt("Invalid Credentials");
			}
		});



		createNewAccount.setOnAction(e -> {
			String user = getUsername();
			String pass = getPassword();
			if (user.isEmpty() || pass.isEmpty()) {
				setPrompt("Username and password cannot be empty.");
				return;
			}
			if (accounts.createAccount(user, pass)) {
				setPrompt("Account created, please login.");
			} else {
				setPrompt("Account name already taken");
			}
		});
	}



	private void readState() {
		File file = new File(STATE_FILE);
		if (!file.exists()) {
			System.out.println("No saved state file found. Starting with a new state.");
			return;
		}

		try (FileInputStream rawBytes = new FileInputStream(file);
				ObjectInputStream inFile = new ObjectInputStream(rawBytes)) {

			HashMap<String, Account> fileAccounts = (HashMap<String, Account>) inFile.readObject();
			accounts.getAccounts().putAll(fileAccounts);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void writeState() {
		HashMap<String, Account> acc = accounts.getAccounts();
		try {
			FileOutputStream bytesToDisk = new FileOutputStream(STATE_FILE);
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			outFile.writeObject(acc);
			outFile.close();
		} catch (IOException ioe) {
			System.out.println("Writing objects failed");
		}
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public Button getLogoutButton() {
		return logoutButton;
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public void setPrompt(String s) {
		prompt.setText(s);
	}

	public boolean isLoggedIn() {
		return accounts.userIsLoggedIn();
	}

	public Account getCurrentUser() {
		return accounts.getLoggedInAccount();
	}
	
	public void setOnLogin(Action loginAction) {
		this.onLogin = loginAction;
	}


}

