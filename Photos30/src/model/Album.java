package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Album {
	String albumName;
	List<Photo> photos = new ArrayList<>();
	
	public Album(String name) {
		this.albumName = name;
	}
	
	protected String getName() {
		return this.albumName;
	}
	
	protected int numOfPics() {
		return photos.size();
	} 
	
	protected String[] dateRange() {
		if(photos.size() == 0) {
			return null;
		}
		Calendar minDate = null;
		Calendar maxDate = null;
		for(Photo photo : photos) {
			//check date against current min
			//check date against current max
		}
		//convert min and max to strings
		//return array ["minDate", "maxDate"]
		return null;
	}
	
}
