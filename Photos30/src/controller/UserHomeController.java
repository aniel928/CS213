package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	@FXML private Text albumExistsError;
	@FXML private TextField albumField;
	@FXML private Button albumButton;
	@FXML private TextField renameField;
	@FXML private Button renameButton;
	@FXML private TableView<Album> albumTableView;
	@FXML private TableColumn<Album, String> albumNameCol;
	@FXML private TableColumn<Album, Integer> numPhotosCol;
	@FXML private TableColumn<Album, String> firstDateCol;
	@FXML private TableColumn<Album, String> lastDateCol;
	
	/**
	 * Change scene to either admin screen or user home screen.
	 * @param event
	 * @throws IOException
	 */

	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	public void createAlbum() {
		albumExistsError.setVisible(false);
		String albumName = albumField.getText();
		for(Album a : currentUser.getAlbums()) {
			if(a.getAlbumName().toLowerCase().equals(albumName.toLowerCase())) {
				albumExistsError.setVisible(true);
				return;
			}
		}
		Album al = new Album(albumName);
		currentUser.addAlbum(al);
		obsAlbumList.add(al);
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		albumButton.setVisible(false);
		for(Album album : currentUser.getAlbums()) {
			System.out.println(album.getAlbumName());
		}
	}
	
	public void renameAlbum() {
		albumExistsError.setVisible(false);
		String oldName = albumTableView.getSelectionModel().getSelectedItem().getAlbumName();
		String albumName = renameField.getText();
		List<Album> albums = currentUser.getAlbums();
		//check for dupes
		for(Album a : albums) {
			if(a.getAlbumName().toLowerCase().equals(albumName.toLowerCase())) {
				albumExistsError.setVisible(true);
				return;
			}
		}
		albumLabel.setVisible(false);
		renameField.setVisible(false);
		renameButton.setVisible(false);

		//change
		for(Album a : albums) {
			if(a.getAlbumName().equals(oldName)) {
				a.setAlbumName(albumName);
				break;
			}
		}		
	}
	
	public void deleteAlbum() {
		if(albumTableView.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING, "You are about to delete "+albumTableView.getSelectionModel().getSelectedItem().getAlbumName() + ".  This action cannot be undone.  Would you like to continue?", ButtonType.YES, ButtonType.CANCEL);
			alert.showAndWait();
			
			if (alert.getResult() == ButtonType.YES) {
				Album album = albumTableView.getSelectionModel().getSelectedItem();
				currentUser.getAlbums().remove(album);
				obsAlbumList.remove(album);
			}
		}
	}
	
	public void openAlbum() {
		
	}
	
	public void search() {
		
	}
	
	
	
	
	@FXML
	public void showAddFields(){
		boolean curr = albumLabel.isVisible();
		albumLabel.setVisible(!curr);
		albumField.setText("");
		albumField.setVisible(!curr);
		albumField.requestFocus();
		albumButton.setVisible(!curr);
		renameButton.setVisible(false);
		renameField.setVisible(false);
		
	}
	
	@FXML
	public void showRenameFields() {
		if(albumTableView.getSelectionModel().getSelectedIndex() != -1) {
			boolean curr = albumLabel.isVisible();
			albumLabel.setVisible(!curr);
			renameField.setVisible(!curr);
			renameField.setText(albumTableView.getSelectionModel().getSelectedItem().getAlbumName());
			renameField.requestFocus();
			renameButton.setVisible(!curr);
			albumButton.setVisible(false);
			albumField.setVisible(false);
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}
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