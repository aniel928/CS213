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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Album;
import model.Photo;
import model.UserState;

public class AlbumController implements Initializable {
	private Album currentAlbum;
	private ObservableList<Photo> obsPhotoList;
	@FXML private TableView<Photo> table;
	@FXML private TableColumn<Photo, String> captionCol;
	@FXML private TableColumn<Photo, ImageView> photoCol;
	@FXML private Text albumTitle;
	@FXML private Text captionLabel;
	@FXML private Text photoError;
	@FXML private TextField captionField;
	@FXML private TextField editCaptionField;
	@FXML private Button saveButton;
	@FXML private Button saveEditButton;
	@FXML private Button moveButton;
	@FXML private Button copyButton;
	@FXML private ComboBox<Album> photoAlbums;

	public void hideAll() {
		captionLabel.setVisible(false);
		captionField.setVisible(false);
		editCaptionField.setVisible(false);
		saveButton.setVisible(false);
		saveEditButton.setVisible(false);
		captionField.setText("");
		editCaptionField.setText("");
		photoError.setText("");
		photoError.setVisible(false);
		moveButton.setVisible(false);
		copyButton.setVisible(false);
		photoAlbums.setVisible(false);
	}
	
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void albums(ActionEvent event) throws IOException{
		Main.changeScene("/view/userhome.fxml");
	}
	
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
			Photo photo = new Photo(file, captionField.getText());
			currentAlbum.addPhoto(photo);
			obsPhotoList.add(photo);
			table.getSelectionModel().select(photo);
			hideAll();
		}
	}
	
	public void newPhoto() {
		
	}
	@FXML
	public void deletePhoto() {
		hideAll();
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			//TODO: are you sure you want to delete error.
			Photo photo = table.getSelectionModel().getSelectedItem();
			currentAlbum.removePhoto(photo);
			obsPhotoList.remove(photo);
		}
	}
	
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
	
	@FXML
	private void updateCaption() {
		Photo photo = table.getSelectionModel().getSelectedItem();
		photo.setCaption(editCaptionField.getText());
		hideAll();
		table.refresh();
	}
	
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
	
	public void movePhoto() {
		Photo photo = table.getSelectionModel().getSelectedItem();
		Album album = photoAlbums.getSelectionModel().getSelectedItem();
		
		album.addPhoto(photo);
		currentAlbum.removePhoto(photo);
		obsPhotoList.remove(photo);
		
		hideAll();
	}
	
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
	
	public void copyPhoto() {
		//make a copy first
		Photo photo = table.getSelectionModel().getSelectedItem();
		
		Album album = photoAlbums.getSelectionModel().getSelectedItem();
		
		album.addPhoto(photo);
		
		hideAll();
	}
	
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
	
	public void showSlideShow() throws IOException {
		Main.newStage("/view/slideshow.fxml");
	}
	
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
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, String>("caption"));
		
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
