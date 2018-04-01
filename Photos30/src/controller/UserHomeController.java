package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.User;

public class UserHomeController implements Initializable{
	
	private List<User> userList = new ArrayList<>();
	@FXML private Text albumLabel;
	@FXML private TextField albumField;
	@FXML private Button albumButton;
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */

	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void createAlbum() {
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		albumField.setText("");
		albumButton.setVisible(false);
	}
	
	public void renameAlbum() {
		
	}
	
	public void deleteAlbum() {
		
	}
	
	public void openAlbum() {
		
	}
	
	public void search() {
		
	}
	
	@FXML
	public void showAddFields(){
		albumLabel.setVisible(true);
		albumField.setVisible(true);
		albumField.requestFocus();
		albumButton.setVisible(true);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//figure out which user this is.
	}
	
	
}