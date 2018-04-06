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
import model.Album;
import model.Photo;
import model.UserState;

public class SearchResultsController implements Initializable {
	
	private ObservableList<Photo> obsPhotoList;
	@FXML private TableView<Photo> table;
	@FXML private TableColumn<Photo, String> captionCol;
	@FXML private TableColumn<Photo, ImageView> photoCol;
	@FXML private Text albumLabel;
	@FXML private TextField albumField;
	@FXML private Button saveButton;

	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void albums(ActionEvent event) throws IOException{
		Main.changeScene("/view/search.fxml");
	}
	
	@FXML
	public void createAlbum(){
		boolean curr = albumLabel.isVisible();
		albumLabel.setVisible(!curr);
		albumField.setVisible(!curr);
		saveButton.setVisible(!curr);
	}
	
	@FXML 
	private void saveAlbum() {
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		saveButton.setVisible(false);
		String albumName = albumField.getText();
		if(albumName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter an album name");
			alert.showAndWait();
			return;
		}
		for(Album album : UserState.getCurrentUser().getAlbums()) {
			if(album.getAlbumName().equals(albumName)) {
				Alert alert = new Alert(AlertType.ERROR, "You already have an album with this name.  Please choose a unique name");
				alert.showAndWait();
				return;
			}
		}
		Album album = new Album(albumName, UserState.getSearchResults());
		UserState.getCurrentUser().addAlbum(album);
		Alert alert = new Alert(AlertType.INFORMATION, "Album saved!");
		alert.showAndWait();
		
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
		
		//set table view columns
		photoCol.setCellValueFactory(new PropertyValueFactory<Photo, ImageView>("thumbnail"));
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, String>("caption"));
		
		//set list
		obsPhotoList = FXCollections.observableArrayList(UserState.getSearchResults());
		table.setItems(obsPhotoList);
		
	}
}
