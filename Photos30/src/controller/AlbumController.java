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
import model.User;
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
	@FXML private Text photoExists;
	@FXML private TextField captionField;
	@FXML private Button saveButton;

	
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
		System.out.println(file);

		captionLabel.setVisible(true);
		captionField.setVisible(true);
		saveButton.setVisible(true);

	}

	public void newPhoto() {
		photoExists.setVisible(false);
		if(captionField.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter a caption.");
			alert.showAndWait();
		}
		else {
			//check for duplicate photo
			for(Photo photo : currentAlbum.getPhotos()) {
				if(photo.photoURLProperty().get().equals(file.toURI().toString())) {
					photoExists.setVisible(true);
					return;
				}
			}
			Photo photo = new Photo(file, captionField.getText());
			captionLabel.setVisible(false);
			captionField.setText("");
			captionField.setVisible(false);
			saveButton.setVisible(false);
			currentAlbum.addPhoto(photo);
			obsPhotoList.add(photo);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentAlbum = UserState.getCurrentAlbum();
		UserState.setCurrentAlbum(null);
		albumTitle.setText(currentAlbum.getAlbumName());
		
		photoCol.setCellValueFactory(new PropertyValueFactory<Photo, ImageView>("image"));
		captionCol.setCellValueFactory(new PropertyValueFactory<Photo, String>("caption"));
		
		obsPhotoList = FXCollections.observableArrayList(currentAlbum.getPhotos());
		table.setItems(obsPhotoList);
	}
}
