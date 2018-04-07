package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable {
	//auto-generated serialization ID:
	private static final long serialVersionUID = -5765672915061151624L;
	//name of album
	private String albumName;
	//earliest timestamp
	private List<Photo> photos = new ArrayList<>();
	
	
/*
 * CONSTRUCTOR(S)	
 */
	public Album(String name) {
		setAlbumName(name);
	}
	
	public Album(String name, List<Photo> photos) {
		setAlbumName(name);
		this.photos = photos;
	}
	
/*
 * GETTERS
 */
	
	public String getAlbumName() {
		return this.albumName;
	}
	
	public Integer getNumPhotos() {
		return photos.size();
	}
	
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
	
	public List<Photo> getPhotos(){
		return this.photos;
	}
	
	
/*
 * SETTERS
 */
	public void setAlbumName(String value) {
		this.albumName = value;
	}
	
	
/*
 * HELPER METHODS
 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	public void removePhoto(Photo photo) {
		photos.remove(photo);
	}
	
	@Override
	public String toString() {
		return this.getAlbumName();
	}
	
}
