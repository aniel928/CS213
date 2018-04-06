package controller;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Album;
import model.Photo;
import model.Tag;
import model.UserState;

public class SearchController implements Initializable {
	@FXML RadioButton all;
	@FXML RadioButton any;
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	@FXML TextField tag1, tag2, tag3, tag4, tag5;
	@FXML TextField value1, value2, value3, value4, value5;
	
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void home(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml");
	}
	
	@FXML
	public void search() {
		int numOfTags = getNumberOfTags();
		System.out.println("Number of tags: " + numOfTags);
		//if there are tags filled out and "all" or "any" are not selected, throw error.
		if(numOfTags != 0 && !(all.isSelected() || any.isSelected())) {
			Alert alert = new Alert(AlertType.ERROR, "Please select matching type.");
			alert.showAndWait();
			return;
		}
		int status = checkTags(numOfTags);
		if(status != 0) {
			Alert alert = null;
			if(status == -1) {
				alert = new Alert(AlertType.ERROR, "Please fill out corresponding tag and value fields.");
			}
			else if (status == -2) {
				alert = new Alert(AlertType.ERROR, "Start with top row of tags, and work your way down.");
			}

			alert.showAndWait();
			return;
		}
		LocalDateTime start = null, end = null;
		String[][] tags = new String[numOfTags][2];
		if(numOfTags > 0) {
			System.out.println("one tag");
			tags[0][0] = tag1.getText();
			tags[0][1] = value1.getText();
		}
		if(numOfTags > 1) {
			System.out.println("definitely not two");
			tags[1][0] = tag2.getText();
			tags[1][1] = value2.getText();
		}
		if(numOfTags > 2) {
			tags[2][0] = tag3.getText();
			tags[2][1] = value3.getText();
		}
		if(numOfTags > 3) {
			tags[3][0] = tag4.getText();
			tags[3][1] = value4.getText();
		}
		if(numOfTags > 4) {
			tags[4][0] = tag5.getText();
			tags[4][1] = value5.getText();
		}
		
		if(startDate.getValue() != null) {
			start = startDate.getValue().atStartOfDay();
		}
		if(endDate.getValue() != null) {
			end = endDate.getValue().atStartOfDay();
		}
		
		
		if((start == null && end != null) || (start != null && end == null)) {
			Alert alert = new Alert(AlertType.ERROR, "Please either fill out both start and end date, or neither.");
			alert.showAndWait();
			return;
		}
		
		List<Photo> results;
		
		if(any.isSelected() && numOfTags > 0) {
			results = searchAny(start, end, tags);
		}
		else if(numOfTags == 0 && start == null && end == null) {
			System.out.println("Return all photos!!");
			results = allPhotos();
		}
		else if(numOfTags == 0) {
			System.out.println("Search Dates only");
			results = searchDatesOnly(start, end);
		}
		
		else if(start == null && end == null) {
			System.out.println("Search Tags Only");
			results = searchTagsOnly(tags);
		}
		
		else {
			System.out.println("SEARCH IT ALL!!!!");
			results = searchBoth(start, end, tags);
		}
		
		
		if(results != null && results.size() != 0) {
			for(Photo result : results) {
				System.out.println(result.getPhotoURL());
			}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION, "No photos match this criteria.  Try expanding your search paraemters.");
			alert.showAndWait();
		}
	}
	
	private List<Photo> searchAny(LocalDateTime start, LocalDateTime end, String[][] tags) {
		List<Photo> photos = new ArrayList<>();
		Set<Photo> photoSet = new HashSet<>();
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			for(Photo photo : album.getPhotos()) {
				if(start != null & end != null) {
					if(photo.getTime().compareTo(start) < 0 || photo.getTime().compareTo(end) > 0) {
						continue;
					}
				}
				for(Tag tag : photo.getAllTags()) {
					for(int i=0; i< tags.length; i++) {
						if(tag.getTag().equals(tags[i][0]) && tag.getValue().equals(tags[i][0])) {
							photoSet.add(photo);
							continue;
						}
					}
				}
			}
		}
		photos.addAll(photoSet);
		return photos;
	}

	private List<Photo> searchBoth(LocalDateTime start, LocalDateTime end, String[][] tags) {
		List<Photo> photos = new ArrayList<>();
		
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			for(Photo photo : album.getPhotos()) {
				if(photo.getTime().compareTo(start) >= 0 && photo.getTime().compareTo(end) <= 0) {
					int tagCounter = 0;
					for(int i = 0; i < tags.length; i++) {
						for(Tag tag : photo.getAllTags()) {
							if(tag.getTag().equals(tags[i][0]) && tag.getValue().equals(tags[i][1])) {
								tagCounter++;
							}
						}
					}
					if(tagCounter == tags.length) {
						photos.add(photo);
					}
				}
			}
		}
		return photos;
	}

	private List<Photo> searchTagsOnly(String[][] tags) {
		List<Photo> photos = new ArrayList<>();
		
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			for(Photo photo : album.getPhotos()) {
				int tagCounter = 0;
				for(int i = 0; i < tags.length; i++) {
					for(Tag tag : photo.getAllTags()) {
						if(tag.getTag().equals(tags[i][0]) && tag.getValue().equals(tags[i][1])) {
							tagCounter++;
						}
					}
				}
				if(tagCounter == tags.length) {
					photos.add(photo);
				}
			}
		}
		return photos;
	}

	private List<Photo> searchDatesOnly(LocalDateTime start, LocalDateTime end) {
		List<Photo> photos = new ArrayList<>();
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			for(Photo photo : album.getPhotos()) {
				System.out.println(start.toString());
				System.out.println(end.toString());
				System.out.println(photo.getTime().toString());
				if(photo.getTime().compareTo(start) >= 0 && photo.getTime().compareTo(end) <= 0) {
					photos.add(photo);
				}
			}
		}
		return photos;
	}

	private List<Photo> allPhotos() {
		List<Photo> photos = new ArrayList<>();
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			photos.addAll(album.getPhotos());
		}
		return photos;
	}

	private int getNumberOfTags() {
		if(tag1.getText().isEmpty()) {
			return 0;
		} else if(tag2.getText().isEmpty()){
			return 1;
		} else if(tag3.getText().isEmpty()){
			return 2;
		} else if(tag4.getText().isEmpty()) {
			return 3;
		} else if(tag5.getText().isEmpty()) {
			return 4;
		} else {
			return 5;
		}
	}
	
	private int checkTags(int numOfTags) {
		if(numOfTags == 0 && !(tag1.getText().isEmpty() && tag2.getText().isEmpty() && tag3.getText().isEmpty() && tag4.getText().isEmpty() && tag5.getText().isEmpty())) {
			return -2;
		}
		if(numOfTags == 1 && !(tag2.getText().isEmpty() && tag3.getText().isEmpty() && tag4.getText().isEmpty() && tag5.getText().isEmpty())) {
			return -2;
		}
		if(numOfTags == 2 && !(tag3.getText().isEmpty() && tag4.getText().isEmpty() && tag5.getText().isEmpty())) {
			return -2;
		}
		if(numOfTags == 3 && !(tag4.getText().isEmpty() && tag5.getText().isEmpty())) {
			return -2;
		}
		if(numOfTags == 4 && !(tag5.getText().isEmpty())) {
			return -2;
		}
		
		
		if((!tag1.getText().isEmpty() && value1.getText().isEmpty()) || (tag1.getText().isEmpty() && !value1.getText().isEmpty())) {
			return -1;
		}
		if((!tag2.getText().isEmpty() && value2.getText().isEmpty()) || (tag2.getText().isEmpty() && !value2.getText().isEmpty())) {
			return -1;
		}
		if((!tag3.getText().isEmpty() && value3.getText().isEmpty()) || (tag3.getText().isEmpty() && !value3.getText().isEmpty())) {
			return -1;
		}
		if((!tag4.getText().isEmpty() && value4.getText().isEmpty()) || (tag4.getText().isEmpty() && !value4.getText().isEmpty())) {
			return -1;
		}
		if((!tag5.getText().isEmpty() && value5.getText().isEmpty()) || (tag5.getText().isEmpty() && !value5.getText().isEmpty())) {
			return -1;
		}
		
		return 0;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//add Tags later then replace text fields with combo box.
		
	}
}
