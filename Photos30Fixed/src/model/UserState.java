package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
/**
 * Class to hold state.  Keeps track of "session" data across all screens and hold common variables.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class UserState {
	/**
	 * Timer used for automatic slideshow
	 */
	public static Timer timer = null;
	/**
	 * User object to hold currently loggin in user.
	 */
	private static User currentUser;
	/**
	 * List of all users.
	 */
	private static List<User> userList = new ArrayList<>();
	/**
	 * Album object to hold album currently being viewed.
	 */
	private static Album currentAlbum;
	/**
	 * Photo object to hold photo currently being viewed.
	 */
	private static Photo currentPhoto;
	/**
	 * List of all photos in search results to display
	 */
	private static Set<Photo> searchResults;
	
	
	/**
	 * Retrieves the currently logged in user.
	 * 
	 * @return User object of currently logged in user.
	 */
	public static User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Sets the currently logged in user.
	 * 
	 * @param user User object of currently logged in user.
	 */
	public static void setCurrentUser(User user) {
		currentUser = user;
	}
	
	/**
	 * Gets a list of all the users that exist.
	 * 
	 * @return List object of all Users
	 */
	public static List<User> getAllUsers(){
		return userList;
	}
	
	/**
	 * Overwrites the list of all users that exists.  Particularly on first load from serialized data.
	 * 
	 * @param users List object of all users.
	 */
	public static void setAllUsers(List<User> users) {
		userList = users;
	}
	
	/**
	 * Retrieves the album selected to be viewed.
	 * 
	 * @return Album object representing current Album.
	 */
	public static Album getCurrentAlbum() {
		return currentAlbum;
	}
	
	/**
	 * Sets the album selected as current album.
	 *  
	 * @param album Album object representing the current album.
	 */
	public static void setCurrentAlbum(Album album) {
		currentAlbum = album;
	}
	
	/**
	 * Retrieves the photo selected to be viewed.
	 * 
	 * @return Photo object representing current Photo.
	 */
	public static Photo getCurrentPhoto(){
		return currentPhoto;
	}
	
	/**
	 * Sets the photo selected as current photo
	 * 
	 * @param photo Photo object representing current photo
	 */
	public static void setCurrentPhoto(Photo photo) {
		currentPhoto = photo;
	}
	/**
	 * Function to save all current data into a serialized format.
	 * @throws IOException exception thrown if loading class fails
	 */
	public static void saveFile() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
		new FileOutputStream("data" + File.separator + "users.dat"));
		oos.writeObject(userList);
		oos.close();
	}	
	
	/** 
	 * Function to set the list of photos to display in search results.
	 * 
	 * @param photos Set object of Photos retrieved during search.
	 */
	public static void setSearchResults(Set<Photo> photos) {
		searchResults = photos;
	}
	
	/**
	 * Function to retrieve the list of photos to display in search results.
	 * 
	 * @return Set object of Photos retrieved during search.
	 */
	public static Set<Photo> getSearchResults(){
		return searchResults;
	}
	
}
