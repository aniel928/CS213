package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Album {
	List<Photo> photos = new ArrayList<>();
	
	public Album(String name) {
		setAlbumName(name);
		setNumPhotos(0);
		setFirstDate(null);
		setLastDate(null);
	}

	// album name
	private StringProperty albumName;
	public void setAlbumName(String value) {
		albumNameProperty().set(value);
	}
	public String getAlbumName() {
		return albumNameProperty().get();
	}
	public StringProperty albumNameProperty() {
		if(albumName == null) {
			albumName = new SimpleStringProperty(this, "albumName");
		}
		return albumName;
	}
	
	// numPhotos
	private IntegerProperty numPhotos;
	public void setNumPhotos(Integer value) {
		numPhotosProperty().set(value);
	}
	public Integer getNumPhotos() {
		return numPhotosProperty().get();
	}
	public IntegerProperty numPhotosProperty() {
		if(numPhotos == null) {
			numPhotos = new SimpleIntegerProperty(this, "numPhotos");
		}
		return numPhotos;
	}

	// first date
	private StringProperty firstDate;
	public void setFirstDate(String value) {
		firstDateProperty().set(value);
	}
	public String getFirstDate() {
		return firstDateProperty().get();
	}
	public StringProperty firstDateProperty() {
		if(firstDate == null) {
			firstDate = new SimpleStringProperty(this, "firstDate");
		}
		return firstDate;
	}
	
	// last date
	private StringProperty lastDate;
	public void setLastDate(String value) {
		lastDateProperty().set(value);
	}
	public String getlastDate() {
		return lastDateProperty().get();
	}
	public StringProperty lastDateProperty() {
		if(lastDate == null) {
			lastDate = new SimpleStringProperty(this, "lastDate");
			}
			return lastDate;
		}
	
}
