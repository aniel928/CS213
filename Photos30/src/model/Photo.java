package model;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Photo {
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
	
	
	
	//tags for this photo
	Map<String, List<String>> tags = new HashMap<>();
	
	//regular constructor
	public Photo(File file, String caption) {
		setPhotoURL(file.toURI().toString());
		setCaption(caption);
		setTimestamp(file.lastModified());
		setImage(new ImageView(new Image(photoURLProperty().get(), 60, 60, true, true)));
	}
	
	//make a copy constructor.
	public Photo(Photo originalPhoto) {
		setPhotoURL(originalPhoto.getPhotoURL());
		setCaption(originalPhoto.getCaption());
		setTimestamp(originalPhoto.getTimestamp());
		setImage(originalPhoto.getImage());
	}
	
	//timestamp 
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
	
	

}
