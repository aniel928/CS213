package controller;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Photo;
import model.Tag;
import model.UserState;

/**
 * Logic for photo screen.  View photo/caption/tags and move to other photos via a manual slideshow.
 * 
 * @author alh220
 * @author jmuccino
 *
 */
public class PhotoController implements Initializable {
	/**
	 * Current photo being viewed.
	 */
	private Photo currentPhoto;
	/**
	 * List for TableView of tags.
	 */
	private ObservableList<Tag> obsTagList = FXCollections.observableArrayList();
	/**
	 * ImageView to display photo
	 */
	@FXML private ImageView photoDisplay;
	/**
	 * Table to store and view tags for a photo
	 */
	@FXML private TableView<Tag> tagTable;
	/**
	 * Tag column for table of tags
	 */
	@FXML private TableColumn<Tag, String> tagCol;
	/**
	 * Value column for table of tags.
	 */
	@FXML private TableColumn<Tag, String> valueCol;
	/**
	 * Time of photo.
	 */
	@FXML private Text timestamp;
	/** 
	 * Caption of photo.
	 */
	@FXML private Text caption;
	/**
	 * Button to navigate to previous photo.
	 */
	@FXML private Button leftArrow;
	/**
	 * Button to navigate to next photo.
	 */
	@FXML private Button rightArrow;
	/**
	 * Photo to navigate back to album.
	 */
	@FXML private Button backButton;
	
	/**
	 * Return to login screen
	 * 
	 * @param event passed in via button click.
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Main.changeScene("/view/login.fxml");
	}
	
	/**
	 * Return to album view screen.
	 * 
	 * @param event passed in via button click.
	 * @throws IOException
	 */
	public void album(ActionEvent event) throws IOException{
		Main.changeScene("/view/albumdetails.fxml");
	}
	
	/**
	 * Find index of current photo within the album and find the photo with an index lower than it. If at index 0, take the photo at the last index.
	 */
	public void leftPhoto() {
		int index = UserState.getCurrentAlbum().getPhotos().indexOf(currentPhoto);
		if(index == 0) {
			index = UserState.getCurrentAlbum().getPhotos().size() - 1;
		}
		else {
			index--;
		}
		
		currentPhoto = UserState.getCurrentAlbum().getPhotos().get(index);
		
		setPhoto();
	}
	/**
	 * Find index of current photo within the album and find the photo with an index higher than it.  If at highest index, take the photo at index 0.
	 */
	public void rightPhoto() {
		int index = UserState.getCurrentAlbum().getPhotos().indexOf(currentPhoto);
		if(index == UserState.getCurrentAlbum().getPhotos().size() - 1) {
			index = 0;
		}
		else {
			index++;
		}
		currentPhoto = UserState.getCurrentAlbum().getPhotos().get(index);
		setPhoto();
	}
	
	/**
	 * Set the image.  Either on first load or after clicking the slideshow buttons that find the next/previous photo.
	 */
	private void setPhoto() {
		photoDisplay.setImage(currentPhoto.getImage().getImage());
		caption.setText(currentPhoto.getCaption());
		timestamp.setText(DateFormat.getDateTimeInstance().format(currentPhoto.getTimestamp()));
		
		//set list
		obsTagList.clear();
		obsTagList.addAll(currentPhoto.getAllTags());
	}
	
	/**
	 * Initial method called on first load of screen.  Finds current photo and determines where call is coming from.  
	 * If coming from search results (as opposed to album), sets arrows to invisible and rewrites default back button logic.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//get user state and set album name
		currentPhoto = UserState.getCurrentPhoto();
		if(UserState.getCurrentAlbum() == null) {
			leftArrow.setVisible(false);
			rightArrow.setVisible(false);
			backButton.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	               try {
	            	   Main.changeScene("/view/searchresults.fxml");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            }
	        });
		}
		setPhoto();
		
		//set table view columns
		tagCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("tag"));
		valueCol.setCellValueFactory(new PropertyValueFactory<Tag, String>("value"));
		
		tagTable.setItems(obsTagList);
		tagTable.getSelectionModel().select(0);
	}
}
