package model;

import java.util.HashMap;
import java.util.List;

public class AccountManager {

	private Account loggedInAccount;
	private HashMap<String, Account> accounts = new HashMap();

	public boolean login(String name, String password) {
		Account account = accounts.get(name);
			if(account != null && account.correctPassword(password)) {
				loggedInAccount = account;
				System.out.print("Login successful");
				return true;
			}
			System.out.println("Login failed");
			return false;
		}
	
	
	public boolean userIsLoggedIn() {
		return loggedInAccount != null;
	}
	
	public void loggedOut() {
		loggedInAccount = null;
	}
	public Account getLoggedInAccount() {
		return loggedInAccount;
	}

    public boolean createAccount(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            System.out.println("Username cannot be empty");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty");
            return false;
        }
        if(accounts.containsKey(userName)) {
            System.out.println("Username is already taken");
            return false;
        }
        accounts.put(userName, new Account(userName, password));
        System.out.println("Account created");
        return true;
    }


	public HashMap<String, Account> getAccounts() {
		// TODO Auto-generated method stub
		return accounts;
	}
}
