package model;

public class User {
	private String userName;
	
	/**
	 * Create new user.
	 * @param userName String representing username.
	 */
	public User(String userName) {
		this.userName = userName; 
	}

	public String getUserName() {
		return this.userName;
	}
}
