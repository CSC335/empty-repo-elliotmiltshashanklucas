package model.account;

import java.util.HashMap;

public class AccountManager {

    private Account loggedInAccount;  // Holds the currently logged-in account
    private HashMap<String, Account> accounts = new HashMap<>();  // Maps usernames to their respective Account objects

    /**
     * Attempts to log in a user with the provided username and password.
     * 
     * @param name the username of the account
     * @param password the password of the account
     * @return true if login is successful, false otherwise
     */
    public boolean login(String name, String password) {
        Account account = accounts.get(name);
        if (account != null && account.correctPassword(password)) {
            loggedInAccount = account;
            System.out.print("Login successful");
            return true;
        }
        System.out.println("Login failed");
        return false;
    }

    /**
     * Checks if any user is currently logged in.
     * 
     * @return true if a user is logged in, false otherwise
     */
    public boolean userIsLoggedIn() {
        return loggedInAccount != null;
    }

    /**
     * Logs out the current user by setting the loggedInAccount to null.
     */
    public void loggedOut() {
        loggedInAccount = null;
    }

    /**
     * Retrieves the currently logged-in account.
     * 
     * @return the logged-in Account object, or null if no user is logged in
     */
    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    /**
     * Creates a new account with the specified username and password.
     * 
     * @param userName the username for the new account
     * @param password the password for the new account
     * @return true if the account was created successfully, false if username is taken or inputs are invalid
     */
    public boolean createAccount(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            System.out.println("Username cannot be empty");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty");
            return false;
        }
        if (accounts.containsKey(userName)) {
            System.out.println("Username is already taken");
            return false;
        }
        accounts.put(userName, new Account(userName, password, new Settings()));
        System.out.println("Account created");
        return true;
    }

    /**
     * Gets a map of all accounts managed by this AccountManager.
     * 
     * @return a map of username strings to Account objects
     */
    public HashMap<String, Account> getAccounts() {
        return accounts;
    }
}
