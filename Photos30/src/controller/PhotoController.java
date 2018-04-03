package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Photo;
import model.Tag;
import model.UserState;

public class PhotoController implements Initializable {
	private Photo currentPhoto;
	private ObservableList<Tag> obsTagList;
	@FXML private ImageView photoDisplay;
	@FXML private TableView<Tag> tagTable;
	@FXML private TableColumn<Tag, String> tagCol;
	@FXML private TableColumn<Tag, String> valueCol;
	@FXML private Text timestamp;
	@FXML private Text caption;
	
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void album(ActionEvent event) throws IOException{
		Main.changeScene("/view/albumdetails.fxml");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get user state and set album name
		currentPhoto = UserState.getCurrentPhoto();
		
		photoDisplay.setImage(currentPhoto.getImage().getImage());
		caption.setText(currentPhoto.getCaption());
		timestamp.setText(DateFormat.getDateInstance().format(currentPhoto.getTimestamp()));
		
		//set table view columns
		tagCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
		valueCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("value"));
		
		//set list
		obsTagList = FXCollections.observableArrayList(currentPhoto.getAllTags());
		tagTable.setItems(obsTagList);
	}
}
