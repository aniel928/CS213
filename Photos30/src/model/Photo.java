package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Photo {
	
	//tags for this photo
	List<Tag> tags = new ArrayList<>();	
	
	//regular constructor
	public Photo(File file, String caption) {
		setPhotoURL(file.toURI().toString());
		setCaption(caption);
		setTimestamp(file.lastModified());
		setThumbnail(new ImageView(new Image(photoURLProperty().get(), 60, 60, true, true)));
		setImage(new ImageView(new Image(photoURLProperty().get(), 300, 300, true, true)));
	}
	
	//make a copy constructor.
	public Photo(Photo originalPhoto) {
		setPhotoURL(originalPhoto.getPhotoURL());
		setCaption(originalPhoto.getCaption());
		setTimestamp(originalPhoto.getTimestamp());
		setThumbnail(originalPhoto.getThumbnail());
		setImage(originalPhoto.getImage());
		setAllTags(originalPhoto.getAllTags());
	}
	
	//timestamp (not property)
	private long timestamp;
	public void setTimestamp(long value) {
		timestamp = value;
	}
	public long getTimestamp() {
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
	
	//thumbnail property
	private ObjectProperty<ImageView> thumbnail;
	public void setThumbnail(ImageView value) {
		thumbnailProperty().set(value);
	}
	public ImageView getThumbnail() {
		return thumbnailProperty().get();
	}
	public ObjectProperty<ImageView> thumbnailProperty() {
		if(thumbnail == null) {
			thumbnail = new SimpleObjectProperty<ImageView>(this, "thumbnail");
		}
		return thumbnail;
	}
	
	//image property
	private ObjectProperty<ImageView> image;
	public void setImage(ImageView value) {
		imageProperty().set(value);
	}
	public ImageView getImage() {
		return imageProperty().get();
	}
	public ObjectProperty<ImageView> imageProperty() {
		if(image == null) {
			image = new SimpleObjectProperty<ImageView>(this, "image");
		}
		return image;
	}
	
	
	//Caption property and methods
	private StringProperty caption;
	public void setCaption(String value) {
		captionProperty().set(value);
	}
	public String getCaption() {
		return captionProperty().get();
	}
	public StringProperty captionProperty() {
		if(caption == null) {
			caption = new SimpleStringProperty(this, "caption");
		}
		return caption;
	}
	
	public List<Tag> getAllTags(){
		return tags;
	}
	
	public void setAllTags(List<Tag> newTags) {
		tags = newTags;
	}
}
