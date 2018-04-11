package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;

public class UserState {
	public static Timer timer = null;
	private static User currentUser;
	private static List<User> userList = new ArrayList<>();
	private static Album currentAlbum;
	private static Photo currentPhoto;
	private static Set<Photo> searchResults;
	
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
	
	public static Photo getCurrentPhoto(){
		return currentPhoto;
	}
	
	public static void setCurrentPhoto(Photo photo) {
		currentPhoto = photo;
	}
	
	public static void saveFile() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream("data" + File.separator + "users.dat"));
		oos.writeObject(userList);
		oos.close();
	}	
	
	public static void setSearchResults(Set<Photo> photos) {
		searchResults = photos;
	}
	
	public static Set<Photo> getSearchResults(){
		return searchResults;
	}
	
}
