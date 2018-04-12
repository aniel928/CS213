package model;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Model of the photo.  Has a list of tags associated, a URL, timestamp, and caption.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class Photo implements Serializable {
	/**
	 * auto-generated serialization ID:
	 */
	private static final long serialVersionUID = 329832242810260719L;
	/**
	 * last modified date
	 */
	private long timestamp;
	/**
	 * path to image
	 */
	private String photoURL;
	/**
	 * caption (initially left blank)
	 */
	private String caption;
	/**
	 * list of all tags associated with this photo
	 */
	private List<Tag> tags = new ArrayList<>();	
/*
 * CONSTRUCTOR(S)
 */
	/**
	 * Creates new Photo object, sets Photo URL and Timestamp properties.
	 * @param file File object from a file import screen.
	 */
	public Photo(File file) {
		setPhotoURL(file.toURI().toString());
		setTimestamp(file.lastModified());
	}
	
/*
 * GETTERS
 */
	
	/**
	 * Retrieves the last modified date of photo.
	 * 
	 * @return long timestamp representing the last modified date.
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Retrieves a date/time associated with the timestamp.  Used in tableview and photo display area.
	 * @return LocalDateTime of the timestamp.
	 */
	public LocalDateTime getTime(){
		return Instant.ofEpochMilli(this.timestamp).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
	}
	
	/**
	 * Retrieves the location of the photo that was uploaded.
	 * 
	 * @return String representing URL of photo object.
	 */
	public String getPhotoURL() {
		return this.photoURL;
	}
	
	/**
	 * Retrieves the caption of the photo.
	 * 
	 * @return String representing photo caption.
	 */
	public String getCaption() {
		return this.caption;
	}

	/**
	 * Retrieves a list of all of the tags associated with this photo.
	 * 
	 * @return List object of associated tags.
	 */
	public List<Tag> getAllTags(){
		return tags;
	}
	
	/**
	 * create thumbnail ImageView from image path for TableView in album
	 * 
	 * @return thumbnail ImageView object with Image embedded.
	 */
	public ImageView getThumbnail() {
		return new ImageView(new Image(getPhotoURL(), 150, 150, true, true));
	}
	
	/**
	 * create ImageView from image path for photo display
	 * 
	 * @return ImageView object with Image embedded.
	 */
	public ImageView getImage() {
		return new ImageView(new Image(getPhotoURL()));
	}
	
	/**
	 * create TextFlow from caption text for use in TableView and photo display.  This allows the text to break onto a second
	 * line when longer than the container.
	 * 
	 * @return TextFLow object holding Text for caption.
	 */
	public TextFlow getCaptionFlow() {
		TextFlow tf = new TextFlow();
		Text t = new Text(caption);
		tf.getChildren().add(t);
		tf.setPrefHeight(10);
		return tf;
	}
	
/*
 * SETTERS
 */
	/**
	 * Set the photo timestamp (used in constructor).
	 * 
	 * @param value long representing timestamp of photo
	 */
	public void setTimestamp(long value) {
		timestamp = value;
	}

	/**
	 * Set the PhotoURL (used in constructor)
	 * 
	 * @param value String representing the URL of the photo.
	 */
	public void setPhotoURL(String value) {
		this.photoURL = value;
	}
	
	/**
	 * Set the caption of the photo.  Used in album screen to add/edit caption.
	 * 
	 * @param value String representing the caption to be associated with the photo.
	 */
	public void setCaption(String value) {
		this.caption = value;
	}

	/**
	 * Overwrite old list of tags with new list of tags.
	 * 
	 * @param newTags List object of Tags to be associated with this photo.
	 */
	public void setAllTags(List<Tag> newTags) {
		tags = newTags;
	}
	
}
