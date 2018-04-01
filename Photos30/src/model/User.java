package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userName;
	private List<Album> albumList = new ArrayList<>();
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
	
	public void addAlbum(Album name) {
		albumList.add(name);
	}
	
	public List<Album> getAlbums(){
		return this.albumList;
	}
	
	public void setAlbums(List<Album> albums) {
		this.albumList = albums;
	}
}
