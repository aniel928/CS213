package controller;

import java.io.File;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import model.Album;
import model.Photo;
import model.UserState;

/**
 * Logic for album view screen.  Add, delete, edit songs.  Add tags, view manual or automatic slideshow, add/edit tags, etc.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class AlbumController implements Initializable {
	/**
	 * reference to the album that is currently being viewed.
	 */
	private Album currentAlbum;
	/**
	 * List of photos that is inside of this album
	 */
	private ObservableList<Photo> obsPhotoList;
	/**
	 * Table of all photos / captions that are in this album
	 */
	@FXML private TableView<Photo> table;
	/** 
	 * Column of captions for photos in this album
	 */
	@FXML private TableColumn<Photo, TextFlow> captionCol;
	/** 
	 * Column of photos for photos in this album. Returned as an imageview.
	 */
	@FXML private TableColumn<Photo, ImageView> photoCol;
	/**
	 * Title of album.
	 */
	@FXML private Text albumTitle;
	/**
	 * Label for adding/editing caption.
	 */
	@FXML private Text captionLabel;
	/**
	 * Error message text area to notify of potential erros.
	 */
	@FXML private Text photoError;
	/**
	 * Field to type caption into for adding/editing caption.
	 */
	@FXML private TextField editCaptionField;
	/**
	 * Button pressed when saving a caption.
	 */
	@FXML private Button saveEditButton;
	/**
	 * Button pressed to trigger moving a photo to a different album.
	 */
	@FXML private Button moveButton;
	/**
	 * Button pressed to trigger copying a photo to a different album.
	 */
	@FXML private Button copyButton;
	/**
	 * Drop down menu to pick album to move/copy photo to.
	 */
	@FXML private ComboBox<Album> photoAlbums;

	/**
	 * Helper method to hide all fields that pop up on button press.  Restores screen to look like it does on initial load.
	 */
	public void hideAll() {
		captionLabel.setVisible(false);
		editCaptionField.setVisible(false);
		saveEditButton.setVisible(false);
		editCaptionField.setText("");
		photoError.setText("");
		photoError.setVisible(false);
		moveButton.setVisible(false);
		copyButton.setVisible(false);
		photoAlbums.setVisible(false);
	}
	
	/**
	 * Return to login screen.
	 * @param event passed in on user click of button.
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	/**
	 * Return to user home screen where all albums are listed.
	 * @param event passed in on user click of button.
	 * @throws IOException
	 */
	public void albums(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml");
	}
	
	/**
	 * Triggers window to open to select file.  After ensuring photo doens't already exist in this album, saves file path into Photo model and adds to current TableView.  
	 * @throws IOException
	 */
	@FXML
	private void openFile() throws IOException {		
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(Main.getStage());
		if(file != null) {
	
			//check for duplicate photo;
			for(Photo photo : currentAlbum.getPhotos()) {
				if(photo.getPhotoURL().equals(file.toURI().toString())) {
				//if(photo.photoURLProperty().get().equals(file.toURI().toString())) {
					photoError.setText("This photo is already in this album!");
					photoError.setVisible(true);
					return;
				}
			}
			Photo photo = new Photo(file);
			currentAlbum.addPhoto(photo);
			obsPhotoList.add(photo);
			table.getSelectionModel().select(photo);
			hideAll();
		}
	}
	/**
	 * Removes photo from current album and deletes from list.
	 */
	@FXML
	public void deletePhoto() {
		hideAll();
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this photo?  This action cannot be undone.", ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				Photo photo = table.getSelectionModel().getSelectedItem();
				currentAlbum.removePhoto(photo);
				obsPhotoList.remove(photo);
			}
		}
	}
	
	/**
	 * Shows label, field and button to allow adding/editing of caption.
	 */
	public void editCaption() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			if(captionLabel.isVisible()) {
				hideAll();
			}else {
				hideAll();
				captionLabel.setVisible(true);
				editCaptionField.setVisible(true);
				saveEditButton.setVisible(true);
				editCaptionField.setText(table.getSelectionModel().getSelectedItem().getCaption());
				editCaptionField.requestFocus();
			}
		}
	}
	
	/**
	 * Saves a new caption to the photo.
	 */
	@FXML
	private void updateCaption() {
		Photo photo = table.getSelectionModel().getSelectedItem();
		photo.setCaption(editCaptionField.getText());
		hideAll();
		table.refresh();
	}
	
	/** 
	 * Shows label, combo box and button to move a photo to a new screen.
	 */
	public void moveFields() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			if(moveButton.isVisible()){
				hideAll();
			}
			else{
				hideAll();
				moveButton.setVisible(true);
				photoAlbums.setVisible(true);
			}
		}
	}
	
	/** 
	 * Copy photo into album chosen from drop-down and remove from the current album.
	 */
	public void movePhoto() {
		Photo photo = table.getSelectionModel().getSelectedItem();
		Album album = photoAlbums.getSelectionModel().getSelectedItem();
		
		album.addPhoto(photo);
		currentAlbum.removePhoto(photo);
		obsPhotoList.remove(photo);
		
		hideAll();
	}
	
	/** 
	 * Shows label, combo box and button to copy a photo to a new screen.
	 */
	public void copyFields() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			if(copyButton.isVisible()) {
				hideAll();
			}else {
				hideAll();
				copyButton.setVisible(true);
				photoAlbums.setVisible(true);
			}
		}
	}
	
	/**
	 *  Copy photo into album chosen from drop-down.  (Keep in current album.) 
	 */
	public void copyPhoto() {
		//make a copy first
		Photo photo = table.getSelectionModel().getSelectedItem();
		
		Album album = photoAlbums.getSelectionModel().getSelectedItem();
		
		album.addPhoto(photo);
		
		hideAll();
	}
	
	/**
	 * If item is selected, open a second window to show all tags associated with selected photo.
	 * @throws IOException
	 */
	public void showTags() throws IOException {
		hideAll();
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			UserState.setCurrentPhoto(table.getSelectionModel().getSelectedItem());
			Main.newStage("/view/tags.fxml");
		}
	}

	/**
	 * Automatic slideshow (pictures change every 3 seconds)
	 * @throws IOException
	 */
	public void showSlideShow() throws IOException {
		Main.newStage("/view/slideshow.fxml");
	}
	
	/**
	 * If photo is selected, open window to display photo
	 * @throws IOException
	 */
	public void openPhoto() throws IOException {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			UserState.setCurrentPhoto(table.getSelectionModel().getSelectedItem());
			Main.changeScene("/view/photo.fxml");
		}
	}
	
	/**
	 * Code called on initial load of screen.  Sets current album, selects a photo (first on default or previously selected if 
	 * returning to this screen from photo screen), and sets TableView elements.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get user state and set album name
		currentAlbum = UserState.getCurrentAlbum();
		if(UserState.getCurrentPhoto() != null) {
			table.getSelectionModel().select(UserState.getCurrentPhoto());
			UserState.setCurrentPhoto(null);
		}
		
		albumTitle.setText(currentAlbum.getAlbumName());
		
		//set table view columns
		photoCol.setCellValueFactory(new PropertyValueFactory<Photo, ImageView>("thumbnail"));
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, TextFlow>("captionFlow"));
		
		//set list
		obsPhotoList = FXCollections.observableArrayList(currentAlbum.getPhotos());
		table.setItems(obsPhotoList);
		
		//set Combo Box
		ObservableList<Album> albums = FXCollections.observableArrayList(UserState.getCurrentUser().getAlbums());
		albums.remove(currentAlbum);
		photoAlbums.setItems(albums);
		
		table.getSelectionModel().select(0);
		
	}
}
