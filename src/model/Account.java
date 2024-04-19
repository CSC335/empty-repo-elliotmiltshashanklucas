package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Account implements Serializable{
	private static final long serialVersionUID = 5305138822007823784L;
	private static Map<String, Account> accountInfo = new HashMap<>();
    private String userName;
    private String password;
    private Settings prefferedSettings;
    
    public Settings getPrefferedSettings() {
		return prefferedSettings;
	}

	public Account(String userName, String password, Settings settings) {
        this.userName = userName;
        this.password = password;
        prefferedSettings = settings;
    }
    
    public void setPrefferedSettings(Settings s) {
    	prefferedSettings = s;
    }

    public String getUserName() {
        return userName;
    }
    public boolean correctPassword(String inputPassword) {
    	return (this.password.equals(inputPassword));
    }
}



