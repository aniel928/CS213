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

/**
 * Logic for user home screen.  Allows adding/editing/opening of albums and navigates to search functionality.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class UserHomeController implements Initializable{
	
	/**
	 * List of all albums belonging to a user.
	 */
	private ObservableList<Album> obsAlbumList;
	/**
	 * Current user whose albums are being viewed.
	 */
	private User currentUser;
	/**
	 * Welcomes the current User by name.
	 */
	@FXML private Text welcomeMessage;
	/**
	 * label for adding/editing an album
	 */
	@FXML private Text albumLabel;
	/**
	 * Error message to indicate issue in adding/renaming album.
	 */
	@FXML private Text albumExistsError;
	/**
	 * Field to add a new album
	 */
	@FXML private TextField albumField;
	/**
	 * Button that executes adding the new album.
	 */
	@FXML private Button albumButton;
	/** 
	 * Field to rename an album.
	 */
	@FXML private TextField renameField;
	/**
	 * Button that executes renaming the album.
	 */
	@FXML private Button renameButton;
	/**
	 * Table to hold all albums for this user.
	 */
	@FXML private TableView<Album> albumTableView;
	/**
	 * Column to hold album names.
	 */
	@FXML private TableColumn<Album, String> albumNameCol;
	/**
	 * Column to hold photo count per album.
	 */
	@FXML private TableColumn<Album, Integer> numPhotosCol;
	/** 
	 * Column to hold low end of date range.
	 */
	@FXML private TableColumn<Album, String> firstDateCol;
	/** 
	 * Column to hold high end of date range.
	 */
	@FXML private TableColumn<Album, String> lastDateCol;
	
	/**
	 * Return back to login screen.
	 * @param event passed in via button click
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml", "Log In");
	}
	
	/**
	 * Checks to make sure album doesn't already exists then adds new album.
	 */
	public void createAlbum() {
		albumExistsError.setVisible(false);
		String albumName = albumField.getText();
		for(Album a : currentUser.getAlbums()) {
			if(a.getAlbumName().toLowerCase().equals(albumName.toLowerCase())) {
				albumExistsError.setVisible(true);
				return;
			}
		}
		
		
		Album album = new Album(albumName);
		currentUser.addAlbum(album);
		obsAlbumList.add(album);
		albumLabel.setVisible(false);
		albumField.setVisible(false);
		albumButton.setVisible(false);

		albumTableView.getSelectionModel().select(album);
	}
	/**
	 * Checks to make sure name doesn't already exist for a different album, then changes name of album.
	 */
	public void renameAlbum() {
		albumExistsError.setVisible(false);
		String oldName = albumTableView.getSelectionModel().getSelectedItem().getAlbumName();
		String albumName = renameField.getText();
		List<Album> albums = currentUser.getAlbums();
		//check for dupes
		for(Album a : albums) {
			if(a.getAlbumName().toLowerCase().equals(albumName.toLowerCase()) && albumTableView.getSelectionModel().getSelectedItem() != a) {
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
		albumTableView.refresh();
	}
	
	/** 
	 * Removes album from user list of albums.
	 */
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
	
	/** 
	 * If album is selected, opens new screen to show album details
	 * @throws IOException
	 */
	public void openAlbum() throws IOException {
		if(albumTableView.getSelectionModel().getSelectedIndex() == -1) {
			Alert alert = new Alert(AlertType.ERROR, "Please select an item.");
			alert.showAndWait();
		}else {
			UserState.setCurrentAlbum(albumTableView.getSelectionModel().getSelectedItem());
			Main.changeScene("/view/albumdetails.fxml", "Album Details");
		}
		
	}
	

	/** 
	 * Opens search criteria screen.
	 * 
	 * @throws IOException
	 */
	public void search() throws IOException {
		Main.changeScene("/view/search.fxml", "Search Criteria");
	}
	
	/** 
	 * Shows fields/buttons to add a new album
	 */
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
	/**
	 * Shows fields/buttons to rename an album.
	 */
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
	
	/**
	 * Initial code that runs on screen start.  Finds current user, gets albums and adds them to table.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		currentUser = UserState.getCurrentUser();
		
		if(currentUser.getUserName().equals("stock")) {
			welcomeMessage.setText("Stock Photo Albums");
		}
		else{
			welcomeMessage.setText(currentUser.getUserName() + "'s albums");
		}
		
		albumNameCol.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
		numPhotosCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("numPhotos"));
		firstDateCol.setCellValueFactory(new PropertyValueFactory<Album, String>("firstDate"));
		lastDateCol.setCellValueFactory(new PropertyValueFactory<Album, String>("lastDate"));
		
		obsAlbumList = FXCollections.observableArrayList(currentUser.getAlbums());
		albumTableView.setItems(obsAlbumList);
		if(UserState.getCurrentAlbum() != null) {
			albumTableView.getSelectionModel().select(UserState.getCurrentAlbum());
			UserState.setCurrentAlbum(null);
		} else {
			albumTableView.getSelectionModel().select(0);
		}
	}
	
	
}