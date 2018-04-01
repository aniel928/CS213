package model;

import java.util.ArrayList;
import java.util.List;

public class UserState {
	private static User currentUser;
	private static List<User> userList = new ArrayList<>();
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	
	public static List<User> getAllUsers(){
		return userList;
	}
	
	public static void setAllUsers(List<User> users) {
		userList = users;
	}
}
