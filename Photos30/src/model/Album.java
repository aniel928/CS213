package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Model of the album.  Has a list of songs associated and a name, along with helper methods to get information specific
 * to the album from the photos within.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class Album implements Serializable {
	/**
	 * auto-generated serialization ID:
	 */
	private static final long serialVersionUID = -5765672915061151624L;
	/**
	 * Name of album
	 */
	private String albumName;
	/**
	 * List of associated photos in this album
	 */
	private List<Photo> photos = new ArrayList<>();
	
	
/*
 * CONSTRUCTOR(S)	
 */
	/**
	 * Main constructor called when a user wants to add a new album.  Sets album name and creates the album object.  
	 * @param name String representing the name of the album.
	 */
	public Album(String name) {
		setAlbumName(name);
	}
	
	/**
	 * Constructor used when album is created during search.  Takes in an album name and a list of photos to create 
	 * the album object.
	 * 
	 * @param name String representing the name of the album.
	 * @param photos List of photos to add to the album.
	 */
	public Album(String name, Set<Photo> photos) {
		setAlbumName(name);
		this.photos.addAll(photos);
	}
	
/*
 * GETTERS
 */
	/**
	 * Retrieves the name of the album.
	 * @return String representing the album name. 
	 */
	public String getAlbumName() {
		return this.albumName;
	}
	
	/**
	 * Retrieve the number of photos in the album by getting the size of the list of photos.
	 * @return Integer representing number of photos in album.
	 */
	public Integer getNumPhotos() {
		return photos.size();
	}
	
	/**
	 * Retrieves the earliest date in the date range of photos in this album.
	 * 
	 * @return String representation of earliest date.
	 */
	public String getFirstDate() {
		long min = 0;
		for(Photo photo : photos) {
			if(min == 0 || photo.getTimestamp() < min) {
				min = photo.getTimestamp();
			}
		}
		if(min == 0) {
			return null;
		}
		return DateFormat.getDateTimeInstance().format(min);
	}
	
	/**
	 * Retrieves the latest date in the date range of photos in this album.
	 * 
	 * @return String representation of latest date.
	 */
	public String getLastDate() {
		long max = 0;
		for(Photo photo: photos) {
			if(photo.getTimestamp() > max) {
				max = photo.getTimestamp();
			}
		}
		if(max == 0) {
			return null;
		}
		return DateFormat.getDateTimeInstance().format(max);
	}
	
	/**
	 * Gets all photos in this album.
	 * @return List of Photo objects associated with this album.
	 */
	public List<Photo> getPhotos(){
		return this.photos;
	}
	
	
/*
 * SETTERS
 */
	/**
	 * sets the album name.
	 * @param value String representing album name.
	 */
	public void setAlbumName(String value) {
		this.albumName = value;
	}
	
	
/*
 * HELPER METHODS
 */
	/**
	 * Adds a single photo to the list of photos in this album.
	 * 
	 * @param photo Photo object to add to album.
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * Removes a single photo from the list of photos in this album.
	 * 
	 * @param photo Photo object to remove from the album.
	 */
	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}
	
	/**
	 * Override of toString() method from Object class for comboBox.
	 */
	@Override
	public String toString() {
		return this.getAlbumName();
	}
	
}
