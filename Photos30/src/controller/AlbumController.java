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
	private File file;
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
		file = fileChooser.showOpenDialog(Main.getStage());
		if(file != null) {
			System.out.println(file);
			String filename = file.toURI().toString();
			String shortFileName = filename.substring(filename.lastIndexOf('/')+1);
			
			hideAll();
			photoError.setText(shortFileName);
			photoError.setVisible(true);
			captionLabel.setVisible(true);
			captionField.setVisible(true);
			captionField.requestFocus();
			saveButton.setVisible(true);
		}
		
	}

	public void newPhoto() {
		if(captionField.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter a caption.");
			alert.showAndWait();
		}
		else {
			//check for duplicate photo
			for(Photo photo : currentAlbum.getPhotos()) {
				if(photo.photoURLProperty().get().equals(file.toURI().toString())) {
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
	
	@FXML
	public void deletePhoto() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
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
			captionLabel.setVisible(true);
			editCaptionField.setVisible(true);
			saveEditButton.setVisible(true);
			editCaptionField.setText(table.getSelectionModel().getSelectedItem().getCaption());
			editCaptionField.requestFocus();
		}
	}
	
	@FXML
	private void updateCaption() {
		Photo photo = table.getSelectionModel().getSelectedItem();
		photo.setCaption(editCaptionField.getText());
		hideAll();
	}
	
	public void moveFields() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			hideAll();
			moveButton.setVisible(true);
			photoAlbums.setVisible(true);
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
			hideAll();
			copyButton.setVisible(true);
			photoAlbums.setVisible(true);
		}
	}
	
	public void copyPhoto() {
		//make a copy first
		Photo photo = new Photo(table.getSelectionModel().getSelectedItem());
		
		Album album = photoAlbums.getSelectionModel().getSelectedItem();
		
		album.addPhoto(photo);
		
		hideAll();
	}
	
	public void showTags() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
	}
	
	public void showSlideShow() {
		if(table.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Implement logic here.");
			alert.showAndWait();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get user state and set album name
		currentAlbum = UserState.getCurrentAlbum();
		UserState.setCurrentAlbum(null);
		albumTitle.setText(currentAlbum.getAlbumName());
		
		//set table view columns
		photoCol.setCellValueFactory(new PropertyValueFactory<Photo, ImageView>("image"));
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, String>("caption"));
		
		//set list
		obsPhotoList = FXCollections.observableArrayList(currentAlbum.getPhotos());
		table.setItems(obsPhotoList);
		
		//set Combo Box
		ObservableList<Album> albums = FXCollections.observableArrayList(UserState.getCurrentUser().getAlbums());
		albums.remove(currentAlbum);
		photoAlbums.setItems(albums);
	}
}
