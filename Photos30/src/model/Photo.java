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

public class Photo implements Serializable {
	//auto-generated serialization ID:
	private static final long serialVersionUID = 329832242810260719L;
	//last modified date
	private long timestamp;
	//path to image
	private String photoURL;
	//caption (initially left blank)
	private String caption;
	//list of all tags
	private List<Tag> tags = new ArrayList<>();	
/*
 * CONSTRUCTOR(S)
 */
	public Photo(File file) {
		setPhotoURL(file.toURI().toString());
		setTimestamp(file.lastModified());
	}
	
/*
 * GETTERS
 */
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public LocalDateTime getTime(){
		return Instant.ofEpochMilli(this.timestamp).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
	}
	
	public String getPhotoURL() {
		return this.photoURL;
	}
	
	public String getCaption() {
		return this.caption;
	}

	public List<Tag> getAllTags(){
		return tags;
	}
	
	//create ImageView from image path for TableView in album
	public ImageView getThumbnail() {
		return new ImageView(new Image(getPhotoURL(), 150, 150, true, true));
	}
	//create ImageView from image path for photo display
	public ImageView getImage() {
		return new ImageView(new Image(getPhotoURL()));
	}
	
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
	
	public void setTimestamp(long value) {
		timestamp = value;
	}

	public void setPhotoURL(String value) {
		this.photoURL = value;
	}
	
	public void setCaption(String value) {
		this.caption = value;
	}

	public void setAllTags(List<Tag> newTags) {
		tags = newTags;
	}
	
}
