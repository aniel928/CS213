package controller;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
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

/**
 * Logic for search criteria screen. Take information filled out in criteria and perform search.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class SearchController implements Initializable {
	/**
	 * Radio button to determine that all tags entered should match.  Logical AND>
	 */
	@FXML RadioButton all;
	/**
	 * Radio button to determine that any tags entered could match.  Logical OR.
	 */
	@FXML RadioButton any;
	/**
	 * Start date of search results (optional field).
	 */
	@FXML DatePicker startDate;
	/**
	 * End date of search results (optional field).
	 */
	@FXML DatePicker endDate;
	/**
	 * 5 tag fields that pair with a value to search on (all are optional).
	 */
	@FXML TextField tag1, tag2, tag3, tag4, tag5;
	/**
	 * 5 value fields that pair with a tag to search on (all are optional).
	 */
	@FXML TextField value1, value2, value3, value4, value5;
	
	/**
	 * Return to login screen.
	 * 
	 * @param event passed in via button click.
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml", "Log In");
	}
	
	/**
	 * Return to user home screen.
	 * 
	 * @param event passed in via button click.
	 * @throws IOException
	 */
	public void home(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml", "User Home");
	}
	
	/**
	 * Called upon button click.  Determines which fields are filled in and which method to call depending
	 * on which fields are filled in.
	 * @throws IOException
	 */
	@FXML
	public void search() throws IOException {
		int numOfTags = getNumberOfTags();
		
		//check to see how many tags are filled in
		int status = checkTags(numOfTags);
		
		//iff bad stuff happened throw an error and return. 
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
		
		//set up array of all tags
		
		String[][] tags = null;
		if(numOfTags > 0) {
			tags = new String[numOfTags][2];
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
		
		//set dates to search on (move back to midnight for easy searching)
		LocalDateTime start = null, end = null;
		if(startDate.getValue() != null) {
			start = startDate.getValue().atStartOfDay();
		}
		if(endDate.getValue() != null) {
			end = endDate.getValue().atStartOfDay();
		}
		
		//if only one is filled out throw error.
		if((start == null && end != null) || (start != null && end == null)) {
			Alert alert = new Alert(AlertType.ERROR, "Please either fill out both start and end date, or neither.");
			alert.showAndWait();
			return;
		}
		
		//create a set (to eliminate potential duplicates across albums)
		Set<Photo> results;
		
		//add anything that matches to set.
		if(any.isSelected() && numOfTags > 0) {
			results = searchAny(start, end, tags);
		}
		//add all photos to set.
		else if(numOfTags == 0 && start == null && end == null) {
			System.out.println("Return all photos!!");
			results = allPhotos();
		}
		//add all photos that match dates
		else if(numOfTags == 0) {
			System.out.println("Search Dates only");
			results = searchDatesOnly(start, end);
		}
		//add all photos that match ALL tags
		else if(start == null && end == null) {
			System.out.println("Search Tags Only");
			results = searchTagsOnly(tags);
		}
		//add all photos that match ALL tags and ALL dates.
		else {
			System.out.println("SEARCH IT ALL!!!!");
			results = searchBoth(start, end, tags);
		}
		
		//If there are results, display them
		if(results != null && results.size() != 0) {
			
			UserState.setSearchResults(results);
			Main.changeScene("/view/searchresults.fxml", "Search Results");
			
		}
		//Otherwise throw error
		else {
			Alert alert = new Alert(AlertType.INFORMATION, "No photos match this criteria.  Try expanding your search paraemters.");
			alert.showAndWait();
		}
	}
	

	/**
	 * if photo matches dates and matches ANY tag, add to set.
	 * 
	 * @param start beginning date (set back to midnight)
	 * @param end ending date (set back to midnight)
	 * @param tags array of all tag values (up to 5 total)
	 * @return set of all photos that match these results
	 */
	private Set<Photo> searchAny(LocalDateTime start, LocalDateTime end, String[][] tags) {
		Set<Photo> photos = new HashSet<>();
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			//Must match the date range.  If it doesn't skip this photo
			for(Photo photo : album.getPhotos()) {
				if(start != null & end != null) {
					if(photo.getTime().compareTo(start) < 0 || photo.getTime().compareTo(end) > 0) {
						continue;
					}
				}
				//As long as it's in the date range, it can match any of the tags (at least one)  Once it matches, add and exit.
				for(Tag tag : photo.getAllTags()) {
					for(int i=0; i< tags.length; i++) {
						if(tag.getTag().toLowerCase().equals(tags[i][0]) && tag.getValue().toLowerCase().equals(tags[i][1])) {
							photos.add(photo);
							continue;
						}
					}
				}
			}
		}
		
		return photos;
	}

	/**
	 * If photo matches dates and ALL tags, add to set.
	 * @param start beginning date (set back to midnight)
	 * @param end ending date (set back to midnight)
	 * @param tags array of all tag values (up to 5 total)
	 * @return set of all photos that match these results
	 */
	private Set<Photo> searchBoth(LocalDateTime start, LocalDateTime end, String[][] tags) {
		Set<Photo> photos = new HashSet<>();
		
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

	/**
	 * Search only tags, not dates.  Must match ALL tags.
	 * 
	 * @param tags array of all tag values (up to 5 total)
	 * @return set of all photos that match these results
	 */
	private Set<Photo> searchTagsOnly(String[][] tags) {
		Set<Photo> photos = new HashSet<>();
		
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

	/**
	 * Search only based on dates, not based on tags.
	 * 
	 * @param start beginning date (set back to midnight)
	 * @param end ending date (set back to midnight)
	 * @return set of all photos that match these results
	 */
	private Set<Photo> searchDatesOnly(LocalDateTime start, LocalDateTime end) {
		Set<Photo> photos = new HashSet<>();
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

	/**
	 * Returns all photos in all albums belonging to the user.
	 * @return set of all photos that match these results.
	 */
	private Set<Photo> allPhotos() {
		Set<Photo> photos = new HashSet<>();
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			photos.addAll(album.getPhotos());
		}
		return photos;
	}

	/** 
	 * Helper method to figure out how many tag pairs are filled out.   
	 * 
	 * @return number of tag pairs filled out (a number between 0 and 5). 
	 */
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
	
	/**
	 * Check to make sure that all fields are passed in correctly
	 * 
	 * @param numOfTags how many tags are filled out properly (starting at top, one row at a time).
	 * @return 0 on success 
	 * 		  -1 if tag/value pairs are not both filled out, 
	 * 		  -2 if rows are not filled in from top to bottom (e.g. row 4 has a tag/value pair, but row 1, 2, or 3 doesn't) 
	 */
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
	
	/**
	 * Method called at first load of screen.  Nothing in here yet.  Keeping it in for future use.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//add Tags later then replace text fields with combo box.
		
	}
}
