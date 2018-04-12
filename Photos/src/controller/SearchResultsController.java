package controller;



import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Album;
import model.Photo;
import model.UserState;
/**
 * Logic for search results screen. Opening photo / creating album from results.
 * 
 * @author alh220
 *@author jmuccino
 */
public class SearchResultsController implements Initializable {
	
	/**
	 * List to hold all photos and generate table view. 
	 */
	private ObservableList<Photo> obsPhotoList;
	/**
	 * Table to hold all photos
	 */
	@FXML private TableView<Photo> table;
	/**
	 * Column to hold photo caption
	 */
	@FXML private TableColumn<Photo, TextFlow> captionCol;
	/**
	 * Column to hold actual photo (ImageView)
	 */
	@FXML private TableColumn<Photo, ImageView> photoCol;
	/**
	 * Label for adding photos to an album.
	 */
	@FXML private Text albumLabel;
	/**
	 * Field to type in album name when adding photos to a new album.
	 */
	@FXML private TextField albumField;
	/**
	 * Save button that initiates creation of new album.
	 */
	@FXML private Button saveButton;

	/**
	 * Return to login screen
	 * 
	 * @param event passed in via button press.
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml", "Log In");
	}
	
	/**
	 * Return to search criteria screen. 
	 * 
	 * @param event passed in via button press.
	 * @throws IOException
	 */
	public void albums(ActionEvent event) throws IOException{
		Main.changeScene("/view/search.fxml", "Search Criteria");
	}

	/**
	 * Return ot user home screen.
	 * 
	 * @param event passed in via button press.
	 * @throws IOException
	 */
	public void home(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml", "User Home");
	}
	
	/**
	 * sets new album fields to be visible.
	 */
	@FXML
	public void createAlbum(){
		boolean curr = albumLabel.isVisible();
		albumLabel.setVisible(!curr);
		albumField.setVisible(!curr);
		saveButton.setVisible(!curr);
	}
	
	/**
	 * Takes new album name, checks to make sure you don't already have an album with this name, then creates 
	 * album and saved to suer profile.
	 */
	@FXML 
	private void saveAlbum() {
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		saveButton.setVisible(false);
		String albumName = albumField.getText();
		//Check for empty album name
		if(albumName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter an album name");
			alert.showAndWait();
			return;
		}
		//Check for duplicate album name.
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			if(album.getAlbumName().equals(albumName)) {
				Alert alert = new Alert(AlertType.ERROR, "You already have an album with this name.  Please choose a unique name");
				alert.showAndWait();
				return;
			}
		}
		//If not empty and not dupe, save new album.
		Album album = new Album(albumName, UserState.getSearchResults());
		UserState.getCurrentUser().addAlbum(album);
		Alert alert = new Alert(AlertType.INFORMATION, "Album saved!");
		alert.showAndWait();
		
	}
	
		
	/**
	 * If a photo is selcted, open photo with photo screen. 
	 * @throws IOException
	 */
	public void openPhoto() throws IOException {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			UserState.setCurrentPhoto(table.getSelectionModel().getSelectedItem());
			Main.changeScene("/view/photo.fxml", "Photo Display");
		}
	}
	
	/**
	 * Code called upon first load of screen.
	 * Sets up tableview with list of photos returned from serach.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//set table view columns
		photoCol.setCellValueFactory(new PropertyValueFactory<Photo, ImageView>("thumbnail"));
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, TextFlow>("captionFlow"));
		
		//set list
		obsPhotoList = FXCollections.observableArrayList(UserState.getSearchResults());
		table.setItems(obsPhotoList);
		
	}
}
