package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model of the user.  Has a username and list of albums belonging to user.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class User implements Serializable {
	
	/**
	 * auto-generated serialization ID:
	 */
	private static final long serialVersionUID = 2524019894349643234L;
	
	/**
	 * Username (unique to user)
	 */
	private String userName;
	
	private List<Album> albumList = new ArrayList<>();
	
	/**
	 * Creates new user.
	 * @param userName String representing username.
	 */
	public User(String userName) {
		this.userName = userName; 
	}

	/**
	 * Retrieves username of user.
	 * 
	 * @return String representing username.
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Adds a single album to list of user albums.
	 * 
	 * @param album Album to be added to List of Albums for user.
	 */
	public void addAlbum(Album album) {
		albumList.add(album);
	}
	
	/**
	 * Returns entire list of albums belong to user.
	 * 
	 * @return List object of Albums belonging to user.
	 */
	public List<Album> getAlbums(){
		return this.albumList;
	}
	
	/**
	 * Overrides entire list of albums that belong to user with a new list of albums.
	 * 
	 * @param albums List of Albums to store as user's albums.
	 */
	public void setAlbums(List<Album> albums) {
		this.albumList = albums;
	}
}
