package model;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Photo {
 //cal.set(Calendar.MILLISECOND,0);
	
	//tags for this photo
	Map<String, List<String>> tags = new HashMap<>();
	
	public Photo(File file, String caption) {
		setPhotoURL(file.toString());
		setCaption(caption);
		setTimestamp(file.lastModified());
	}
	
	//timestamp 
	private long timestamp;
	public void setTimestamp(long value) {
		timestamp = value;
	}
	public Object getTimestampL() {
		return timestamp;
	}
	
	//PhotoURL Property and Methods
	private StringProperty photoURL;
	public void setPhotoURL(String value) {
		photoURLProperty().set(value);
	}
	public String getPhotoURL() {
		return photoURLProperty().get();
	}
	public StringProperty photoURLProperty() {
		if(photoURL == null) {
			photoURL = new SimpleStringProperty(this, "photoURL");
		}
		return photoURL;
	}
	
	//Caption property and methods
	private StringProperty caption;
	public void setCaption(String value) {
		captionProperty().set(value);
	}
	public String getCaptionL() {
		return captionProperty().get();
	}
	public StringProperty captionProperty() {
		if(caption == null) {
			caption = new SimpleStringProperty(this, "caption");
		}
		return caption;
	}
	
	

}
