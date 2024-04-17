package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Account implements Serializable{
    private static Map<String, Account> accountInfo = new HashMap<>();
    private String userName;
    private String password; 
    
    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static boolean createAccount(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            System.out.println("Username cannot be empty");
            return false;
        }
        if (password == null || password.isEmpty()) {
            System.out.println("Password cannot be empty");
            return false;
        }
        if (accountInfo.containsKey(userName)) {
            System.out.println("Username is already taken");
            return false;
        }
        accountInfo.put(userName, new Account(userName, password));
        System.out.println("Account created");
        return true;
    }


	public void logout() {
		// TODO Auto-generated method stub

	}


    public String getUserName() {
        return userName;
    }
    public boolean correctPassword(String inputPassword) {
    	return (this.password.equals(inputPassword));
    }

}



