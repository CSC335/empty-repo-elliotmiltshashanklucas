package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Account;
import model.AccountManager;
import model.Game;
import model.Game.Difficulty;

public class accountStatsTest extends Application {
	AccountManager am;
	
	private StatsScreen stats;
	
	private BorderPane all;
	
	public void addData() {
	am.createAccount("a", "1");
	am.createAccount("b", "2");
	am.login("a", "1");
	Account a = am.getLoggedInAccount();
	a.makeHistory();
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	a.addGameData(24, 23, Game.Difficulty.MEDIUM, 12);
	am.loggedOut();
	am.login("b", "2");
	Account b = am.getLoggedInAccount();
	b.makeHistory();
	b.addGameData(20, 23, Game.Difficulty.MEDIUM, 12);
	b.addGameData(12, 23, Game.Difficulty.MEDIUM, 12);
	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		all = new BorderPane();
		am = new AccountManager();
		addData();
		stats = new StatsScreen(900, 600, am);
		all.setCenter(stats);
		Scene scene = new Scene(all);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
}
