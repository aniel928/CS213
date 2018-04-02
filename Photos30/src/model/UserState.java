package model;

import java.util.ArrayList;
import java.util.List;

public class UserState {
	private static User currentUser;
	private static List<User> userList = new ArrayList<>();
	private static Album currentAlbum;
	
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
	
	public static Album getCurrentAlbum() {
		return currentAlbum;
	}
	
	public static void setCurrentAlbum(Album album) {
		currentAlbum = album;
	}
}
