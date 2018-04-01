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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Album;
import model.User;
import model.UserState;

public class UserHomeController implements Initializable{
	
	private ObservableList<Album> obsAlbumList;
	private User currentUser;
	@FXML private Text welcomeMessage;
	@FXML private Text albumLabel;
	@FXML private TextField albumField;
	@FXML private Button albumButton;
	@FXML private TableView<Album> albumTableView;
	@FXML private TableColumn<Album, String> albumNameCol;
	@FXML private TableColumn<Album, Integer> numPhotosCol;
	@FXML private TableColumn<Album, String> firstDateCol;
	@FXML private TableColumn<Album, String> lastDateCol;
//	@FXML private TableColumn numPhotosCol;
//	@FXML private TableColumn firstDateCol;
//	@FXML private TableColumn lastDateCol;
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */

	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void createAlbum() {
		String albumName = albumField.getText();
		Album al = new Album(albumName);
		currentUser.addAlbum(al);
		obsAlbumList.clear();
		obsAlbumList.addAll(currentUser.getAlbums());
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		albumField.setText("");
		albumButton.setVisible(false);
		for(Album album : currentUser.getAlbums()) {
			System.out.println(album.getAlbumName());
		}
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
		boolean curr = albumLabel.isVisible();
		albumLabel.setVisible(!curr);
		albumField.setVisible(!curr);
		albumField.requestFocus();
		albumButton.setVisible(!curr);
	}
	
	@FXML
	public void showRenameFields() {
		boolean curr = albumLabel.isVisible();
		albumLabel.setVisible(!curr);
		albumField.setVisible(!curr);
		albumField.setText("album name");
		albumField.requestFocus();
		albumButton.setVisible(!curr);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser = UserState.getCurrentUser();
		welcomeMessage.setText("Welcome, "+ currentUser.getUserName() + "!");
		
		albumNameCol.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
		numPhotosCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("numPhotos"));
		firstDateCol.setCellValueFactory(new PropertyValueFactory<Album, String>("firstDate"));
		lastDateCol.setCellValueFactory(new PropertyValueFactory<Album, String>("lastDate"));
		
		obsAlbumList = FXCollections.observableArrayList(currentUser.getAlbums());
		albumTableView.setItems(obsAlbumList);
	}
	
	
}